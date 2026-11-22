package cat.ishawaheed.stutager

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup
/**
 * TaskEditActivity
 *
 * Activity responsible for managing the task creation and editing form.
 *
 * Automatically differentiates between creation mode ("Nova activitat") and edit mode
 * ("Edita l'activitat") using the "is_edit" boolean extra passed in the Intent.
 */
class TaskEditActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val isEdit = intent.getBooleanExtra("is_edit", false)
        val taskId = intent.getStringExtra(TaskRepository.EXTRA_TASK_ID)
        val existingTask = if (isEdit && taskId != null) TaskRepository(this).find(taskId) else null
        if (isEdit && existingTask == null) {
            Toast.makeText(this, R.string.form_task_unavailable, Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        setContentView(R.layout.activity_task_edit)
        applySystemBarInsets()

        val tvFormTitle = findViewById<TextView>(R.id.tvFormTitle)
        val tvFormSubtitle = findViewById<TextView>(R.id.tvFormSubtitle)
        val etTitle = findViewById<EditText>(R.id.etTitle)
        val etDate = findViewById<EditText>(R.id.etDate)
        val etNotes = findViewById<EditText>(R.id.etNotes)
        val moduleSpinner = findViewById<Spinner>(R.id.moduleSpinner)
        val prioritySpinner = findViewById<Spinner>(R.id.prioritySpinner)
        val statusSpinner = findViewById<Spinner>(R.id.statusSpinner)
        val typeToggleGroup = findViewById<MaterialButtonToggleGroup>(R.id.typeToggleGroup)
        typeToggleGroup.allowLabelWrapping()
        val btnSave = findViewById<MaterialButton>(R.id.btnSave)
        val btnCancel = findViewById<MaterialButton>(R.id.btnCancel)

        if (existingTask != null) {
            // Mode EDICIÓ - Carreguem la tasca seleccionada des de TaskRepository
            tvFormTitle?.text = getString(R.string.edit_activity_title)
            tvFormSubtitle?.text = getString(R.string.edit_activity_subtitle)
            etTitle.setText(existingTask.title)
            etDate.setText(existingTask.date)
            etNotes.setText(existingTask.notes)

            val moduleIndex = when (existingTask.module) {
                getString(R.string.module_programacio) -> 1
                getString(R.string.module_data) -> 2
                else -> 0
            }
            moduleSpinner?.setSelection(moduleIndex)
            prioritySpinner?.setSelection(existingTask.priorityIndex)

            val statusIndex = when (existingTask.status) {
                getString(R.string.status_pending) -> 0
                getString(R.string.badge_completed) -> 2
                else -> 1 // En curs
            }
            statusSpinner?.setSelection(statusIndex)

            if (existingTask.isExam) {
                typeToggleGroup?.check(R.id.typeExamsButton)
            } else {
                typeToggleGroup?.check(R.id.typeWorksButton)
            }
            btnSave?.text = getString(R.string.save_changes_btn)
        } else {
            // Mode CREACIÓ ("Nova activitat")
            tvFormTitle?.text = getString(R.string.new_activity_title)
            tvFormSubtitle?.text = getString(R.string.new_activity_subtitle)
            etTitle.setText("")
            etDate.setText("")
            etNotes.setText("")
            moduleSpinner?.setSelection(0)
            prioritySpinner?.setSelection(1)
            statusSpinner?.setSelection(0)
            typeToggleGroup?.check(R.id.typeWorksButton)
            btnSave?.text = getString(R.string.save_btn)
        }

        btnCancel?.text = getString(R.string.cancel_btn)

        findViewById<View>(R.id.btnBack)?.setOnClickListener {
            finish()
        }

        btnSave?.setOnClickListener {
            if (etTitle.text.isBlank()) {
                etTitle.requestFocus()
                etTitle.error = getString(R.string.form_title_required)
            } else {
                val message = if (isEdit) R.string.form_edit_demo else R.string.form_add_demo
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        btnCancel?.setOnClickListener {
            finish()
        }
    }
}
