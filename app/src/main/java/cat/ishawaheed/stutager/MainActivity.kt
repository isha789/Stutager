package cat.ishawaheed.stutager

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        findViewById<LinearLayout>(R.id.activityTabs).fitTabLabels()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Back button
        findViewById<ImageView>(R.id.btnBack).setOnClickListener {
            finish()
        }

        // Menu button -> All Tasks (Screen 9)
        /*findViewById<ImageView>(R.id.btnMenu).setOnClickListener {
            startActivity(Intent(this, AllTasksActivity::class.java))
        }*/
        // Accés provisional a Preferències
        findViewById<ImageView>(R.id.btnMenu).setOnClickListener {
            startActivity(Intent(this, PreferencesActivity::class.java))
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewTasks)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Add Activity -> Screen 6
        findViewById<FloatingActionButton>(R.id.btnAddActivity).setOnClickListener {
            val intent = Intent(this, TaskEditActivity::class.java)
            intent.putExtra("is_edit", false)
            startActivity(intent)
        }

        // All Tasks -> Screen 9
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
