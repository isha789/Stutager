package cat.ishawaheed.stutager

import android.os.Bundle
import android.view.View
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton

class TaskEditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_task_edit)

        val taskEditContent = findViewById<View>(R.id.taskEditContent)
        if (taskEditContent != null) {
            val initialLeft = taskEditContent.paddingLeft
            val initialTop = taskEditContent.paddingTop
            val initialRight = taskEditContent.paddingRight
            val initialBottom = taskEditContent.paddingBottom

            ViewCompat.setOnApplyWindowInsetsListener(taskEditContent) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(
                    initialLeft + systemBars.left,
                    initialTop + systemBars.top,
                    initialRight + systemBars.right,
                    initialBottom + systemBars.bottom
                )
                insets
            }
        }

        // Valors inicials d'exemple (DSAM, Alta, En curs)
        findViewById<Spinner>(R.id.moduleSpinner)?.setSelection(0)
        findViewById<Spinner>(R.id.prioritySpinner)?.setSelection(2)
        findViewById<Spinner>(R.id.statusSpinner)?.setSelection(1)

        findViewById<View>(R.id.btnBack)?.setOnClickListener {
            finish()
        }

        findViewById<MaterialButton>(R.id.btnSave)?.setOnClickListener {
            finish()
        }

        findViewById<MaterialButton>(R.id.btnCancel)?.setOnClickListener {
            finish()
        }
    }
}
