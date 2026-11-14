package cat.ishawaheed.stutager

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ActivitiesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activities_summary)
        findViewById<LinearLayout>(R.id.activityTabs).fitTabLabels()
        applySystemBarInsets()

        // Menu button (three lines) opens All Tasks (Screen 9) instead of finishing
        findViewById<ImageView>(R.id.btnMenu).setOnClickListener {
            startActivity(Intent(this, AllTasksActivity::class.java))
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewTasks)
        recyclerView.layoutManager = LinearLayoutManager(this)

        findViewById<FloatingActionButton>(R.id.btnAddActivity).setOnClickListener {
            val intent = Intent(this, TaskEditActivity::class.java)
            intent.putExtra("is_edit", false)
            startActivity(intent)
        }

        findViewById<FloatingActionButton>(R.id.btnAllTasks).setOnClickListener {
            startActivity(Intent(this, AllTasksActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        val tasks = TaskRepository(this).tasks(summary = true).map {
            it.copy(date = when (it.id) {
                "prototip" -> getString(R.string.date_prototip)
                "examen" -> getString(R.string.date_examen)
                else -> it.date
            })
        }
        findViewById<RecyclerView>(R.id.recyclerViewTasks).adapter = TaskAdapter(tasks) { task ->
            startActivity(Intent(this, TaskDetailActivity::class.java).putExtra(TaskRepository.EXTRA_TASK_ID, task.id))
        }
    }
}
