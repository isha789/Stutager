package cat.ishawaheed.stutager

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup

class TaskEditActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_task_edit)

        val rootLayout = findViewById<View>(R.id.rootLayout)
        if (rootLayout != null) {
            ViewCompat.setOnApplyWindowInsetsListener(rootLayout) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(
                    systemBars.left,
                    systemBars.top,
                    systemBars.right,
                    systemBars.bottom,
                )
                insets
            }
        }

        val isEdit = intent.getBooleanExtra("is_edit", false)

        val tvFormTitle = findViewById<TextView>(R.id.tvFormTitle)
        val tvFormSubtitle = findViewById<TextView>(R.id.tvFormSubtitle)
        val etTitle = findViewById<EditText>(R.id.etTitle)
        val etDate = findViewById<EditText>(R.id.etDate)
        val etNotes = findViewById<EditText>(R.id.etNotes)
        val moduleSpinner = findViewById<Spinner>(R.id.moduleSpinner)
        val prioritySpinner = findViewById<Spinner>(R.id.prioritySpinner)
        val statusSpinner = findViewById<Spinner>(R.id.statusSpinner)
        val typeToggleGroup = findViewById<MaterialButtonToggleGroup>(R.id.typeToggleGroup)
        val btnSave = findViewById<MaterialButton>(R.id.btnSave)
        val btnCancel = findViewById<MaterialButton>(R.id.btnCancel)

        if (isEdit) {
            // Mode EDICIÓ
            tvFormTitle?.text = getString(R.string.edit_activity_title)
            tvFormSubtitle?.text = getString(R.string.edit_activity_subtitle)
            etTitle?.setText(R.string.task_title_prototip)
            etDate?.setText(R.string.hint_date)
            etNotes?.setText(R.string.hint_notes_content)
            moduleSpinner?.setSelection(0) // DSAM
            prioritySpinner?.setSelection(2) // Alta
            statusSpinner?.setSelection(1) // En curs
            typeToggleGroup?.check(R.id.typeWorksButton)
            btnSave?.text = getString(R.string.save_changes_btn)
        } else {
            // Mode CREACIÓ ("Nova activitat")
            tvFormTitle?.text = getString(R.string.new_activity_title)
            tvFormSubtitle?.text = getString(R.string.new_activity_subtitle)
            etTitle?.setText("")
            etDate?.setText("")
            etNotes?.setText("")
            moduleSpinner?.setSelection(0) // DSAM
            prioritySpinner?.setSelection(1) // Mitjana
            statusSpinner?.setSelection(0) // Pendent
            typeToggleGroup?.check(R.id.typeWorksButton)
            btnSave?.text = getString(R.string.save_btn)
        }

        btnCancel?.text = getString(R.string.cancel_btn)

        findViewById<View>(R.id.btnBack)?.setOnClickListener {
            finish()
        }

        btnSave?.setOnClickListener {
            finish()
        }

        btnCancel?.setOnClickListener {
            finish()
        }
    }
}
