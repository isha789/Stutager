package cat.ishawaheed.stutager

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AllTasksActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_tasks)

        findViewById<ImageView>(R.id.btnMenu).setOnClickListener {
            finish()
        }

        findViewById<FloatingActionButton>(R.id.btnAddActivity).setOnClickListener {
            startActivity(Intent(this, TaskEditActivity::class.java))
        }
    }
}
