package cat.ishawaheed.stutager

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.appcompat.app.AlertDialog
import com.google.android.material.color.MaterialColors
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder

/**
 * TaskDetailActivity
 *
 * Activity that displays detailed information about a specific task.
 *
 * Loads task data from [TaskRepository] using the task ID passed in the Intent
 * and provides navigation to edit the task or display a deletion confirmation dialog.
 */

class TaskDetailActivity : AppCompatActivity() {
    private lateinit var task: TaskItem
    private var deleteDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)

        task = TaskRepository(this).find(intent.getStringExtra(TaskRepository.EXTRA_TASK_ID) ?: "prototip")
            ?: run { finish(); return }
        findViewById<TextView>(R.id.tvTaskTitle).text = task.title
        findViewById<TextView>(R.id.tvTaskStatus).text = task.status
        findViewById<TextView>(R.id.tvTaskDate).text = task.detailDate
        findViewById<TextView>(R.id.tvTaskModule).text = if (task.id == "prototip") getString(R.string.module_full_dsam) else task.module
        findViewById<TextView>(R.id.tvTaskNotes).text = task.notes
        findViewById<ImageView>(R.id.ivDetailTaskType).apply {
            setImageResource(if (task.isExam) R.drawable.ic_exam else R.drawable.ic_task)
            contentDescription = getString(if (task.isExam) R.string.type_examen else R.string.type_treball)
        }
        val priority = getString(when (task.priorityIndex) {
            2 -> R.string.priority_alta_short
            0 -> R.string.priority_baixa_short
            else -> R.string.priority_mitjana_short
        })
        findViewById<TextView>(R.id.tvTaskPriority).apply {
            text = getString(R.string.priority_detail, priority)
            setTextColor(themeColor(if (task.priorityIndex == 2) androidx.appcompat.R.attr.colorError
                else com.google.android.material.R.attr.colorOnSurfaceVariant))
        }
        val (badgeBackground, badgeText) = when (task.badgeType) {
            BadgeType.BLUE -> R.drawable.badge_blue to R.color.badge_blue_text
            BadgeType.GREEN -> R.drawable.badge_green to R.color.badge_green_text
            BadgeType.RED -> R.drawable.badge_red to R.color.badge_red_text
            BadgeType.YELLOW -> R.drawable.badge_yellow to R.color.badge_yellow_text
        }
        findViewById<TextView>(R.id.tvTaskStatus).apply {
            setBackgroundResource(badgeBackground)
            setTextColor(getColor(badgeText))
        }

        findViewById<ImageView>(R.id.btnBack).setOnClickListener {
            finish()
        }

        findViewById<MaterialButton>(R.id.btnEdit).setOnClickListener {
            val intent = Intent(this, TaskEditActivity::class.java)
            intent.putExtra("is_edit", true)
            intent.putExtra(TaskRepository.EXTRA_TASK_ID, task.id)
            startActivity(intent)
        }

        findViewById<MaterialButton>(R.id.btnDelete).setOnClickListener {
            showDeleteDialog()
        }
        if (savedInstanceState?.getBoolean("delete_dialog_open") == true) showDeleteDialog()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean("delete_dialog_open", deleteDialog?.isShowing == true)
        super.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        deleteDialog?.dismiss()
        super.onDestroy()
    }
    /**
     *
     * TaskDetailActivity - showDetaileDialog()
     *
     * Constructs and displays a custom modal confirmation dialog for deleting the current task.
     *
     */
    private fun showDeleteDialog() {
        if (deleteDialog?.isShowing == true) return
        val taskTitle = findViewById<TextView>(R.id.tvTaskTitle)?.text?.toString()
            ?: getString(R.string.task_title_prototip)

        val context = this
        val dialogContainer = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER_HORIZONTAL
            setPadding(dp(24), dp(24), dp(24), dp(24))
            background = ContextCompat.getDrawable(context, R.drawable.bg_card_rounded)

            // Red Trash Bin Icon
            val iconView = ImageView(context).apply {
                setImageResource(R.drawable.ic_delete)
                setColorFilter(themeColor(androidx.appcompat.R.attr.colorError))
                importantForAccessibility = View.IMPORTANT_FOR_ACCESSIBILITY_NO
                layoutParams = LinearLayout.LayoutParams(dp(48), dp(48)).apply {
                    bottomMargin = dp(12)
                }
            }
            addView(iconView)

            // Title
            val titleView = TextView(context).apply {
                text = getString(R.string.delete_dialog_title_q)
                setTextColor(themeColor(com.google.android.material.R.attr.colorOnSurface))
                textSize = 22f
                setTypeface(typeface, Typeface.BOLD)
                gravity = Gravity.CENTER
            }
            addView(titleView)

            // Message
            val messageView = TextView(context).apply {
                text = getString(R.string.delete_dialog_message_target, taskTitle)
                setTextColor(themeColor(com.google.android.material.R.attr.colorOnSurfaceVariant))
                textSize = 15f
                gravity = Gravity.CENTER
                setLineSpacing(dp(3).toFloat(), 1f)
                layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                ).apply {
                    topMargin = dp(8)
                    bottomMargin = dp(16)
                }
            }
            addView(messageView)

            // Horizontal buttons row
            val buttonsRow = LinearLayout(context).apply {
                orientation = LinearLayout.HORIZONTAL
                layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )

                val btnCancel = MaterialButton(
                    context,
                    null,
                    com.google.android.material.R.attr.materialButtonOutlinedStyle
                ).apply {
                    text = getString(R.string.cancel_btn)
                    setTextColor(themeColor(androidx.appcompat.R.attr.colorPrimary))
                    isAllCaps = false
                    textSize = 15f
                    setTypeface(typeface, Typeface.BOLD)
                    strokeColor = ColorStateList.valueOf(themeColor(com.google.android.material.R.attr.colorOutline))
                    strokeWidth = dp(1)
                    backgroundTintList = ColorStateList.valueOf(themeColor(com.google.android.material.R.attr.colorSurface))
                    cornerRadius = dp(12)
                    minHeight = dp(48)
                    layoutParams = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f).apply {
                        marginEnd = dp(6)
                    }
                }

                val btnConfirm = MaterialButton(context).apply {
                    text = getString(R.string.delete_btn)
                    setTextColor(themeColor(com.google.android.material.R.attr.colorOnError))
                    isAllCaps = false
                    textSize = 15f
                    setTypeface(typeface, Typeface.BOLD)
                    backgroundTintList = ColorStateList.valueOf(themeColor(androidx.appcompat.R.attr.colorError))
                    cornerRadius = dp(12)
                    minHeight = dp(48)
                    layoutParams = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f).apply {
                        marginStart = dp(6)
                    }
                }

                addView(btnCancel)
                addView(btnConfirm)
            }
            addView(buttonsRow)
        }

        val dialog = MaterialAlertDialogBuilder(this)
            .setView(dialogContainer)
            .create()
        deleteDialog = dialog
        ViewCompat.setAccessibilityPaneTitle(dialogContainer, getString(R.string.delete_dialog_title_q))
        dialog.setOnDismissListener { deleteDialog = null }

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val buttonsRow = dialogContainer.getChildAt(3) as LinearLayout
        val btnCancel = buttonsRow.getChildAt(0) as MaterialButton
        val btnConfirm = buttonsRow.getChildAt(1) as MaterialButton

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        btnConfirm.setOnClickListener {
            TaskRepository(this).delete(task.id)
            android.widget.Toast.makeText(this, "Activitat «${task.title}» eliminada", android.widget.Toast.LENGTH_SHORT).show()
            android.util.Log.d("TaskDetailActivity", "Eliminada la tasca amb ID: ${task.id}")
            dialog.dismiss()
            finish()
        }

        dialog.show()
    }

    private fun dp(value: Int): Int = (value * resources.displayMetrics.density + 0.5f).toInt()

    private fun themeColor(attribute: Int): Int = MaterialColors.getColor(this, attribute, javaClass.simpleName)
}
