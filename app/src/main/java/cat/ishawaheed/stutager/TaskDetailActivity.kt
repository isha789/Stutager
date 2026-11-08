package cat.ishawaheed.stutager

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class TaskDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)

        findViewById<ImageView>(R.id.btnBack).setOnClickListener {
            finish()
        }

        findViewById<MaterialButton>(R.id.btnEdit).setOnClickListener {
            val intent = Intent(this, TaskEditActivity::class.java)
            intent.putExtra("is_edit", true)
            startActivity(intent)
        }

        findViewById<MaterialButton>(R.id.btnDelete).setOnClickListener {
            showDeleteDialog()
        }
    }

    private fun showDeleteDialog() {
        val taskTitle = findViewById<TextView>(R.id.tvTaskTitle)?.text?.toString()
            ?: getString(R.string.task_title_prototip)

        val context = this
        val dialogContainer = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER_HORIZONTAL
            setPadding(64, 64, 64, 64)
            background = ContextCompat.getDrawable(context, R.drawable.bg_card_rounded)

            // Red Trash Bin Icon
            val iconView = ImageView(context).apply {
                setImageResource(R.drawable.ic_delete)
                setColorFilter(ContextCompat.getColor(context, R.color.icon_red))
                layoutParams = LinearLayout.LayoutParams(120, 120).apply {
                    bottomMargin = 32
                }
            }
            addView(iconView)

            // Title
            val titleView = TextView(context).apply {
                text = getString(R.string.delete_dialog_title_q)
                setTextColor(ContextCompat.getColor(context, R.color.dark_blue))
                textSize = 22f
                setTypeface(typeface, Typeface.BOLD)
                gravity = Gravity.CENTER
            }
            addView(titleView)

            // Message
            val messageView = TextView(context).apply {
                text = getString(R.string.delete_dialog_message_target, taskTitle)
                setTextColor(ContextCompat.getColor(context, R.color.dark_blue))
                textSize = 15f
                gravity = Gravity.CENTER
                setLineSpacing(8f, 1f)
                layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                ).apply {
                    topMargin = 24
                    bottomMargin = 48
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
                    setTextColor(ContextCompat.getColor(context, R.color.dark_blue))
                    isAllCaps = false
                    textSize = 15f
                    setTypeface(typeface, Typeface.BOLD)
                    strokeColor = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.dark_blue))
                    strokeWidth = 3
                    backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.white_background))
                    cornerRadius = 32
                    minHeight = 120
                    layoutParams = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f).apply {
                        marginEnd = 16
                    }
                }

                val btnConfirm = MaterialButton(context).apply {
                    text = getString(R.string.delete_btn)
                    setTextColor(ContextCompat.getColor(context, R.color.white))
                    isAllCaps = false
                    textSize = 15f
                    setTypeface(typeface, Typeface.BOLD)
                    backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.icon_red))
                    cornerRadius = 32
                    minHeight = 120
                    layoutParams = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f).apply {
                        marginStart = 16
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

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val buttonsRow = dialogContainer.getChildAt(3) as LinearLayout
        val btnCancel = buttonsRow.getChildAt(0) as MaterialButton
        val btnConfirm = buttonsRow.getChildAt(1) as MaterialButton

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        btnConfirm.setOnClickListener {
            dialog.dismiss()
            finish()
        }

        dialog.show()
    }
}
