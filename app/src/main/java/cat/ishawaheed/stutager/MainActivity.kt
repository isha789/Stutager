package cat.ishawaheed.stutager

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * MainActivity
 *
 * Active activities summary view displaying task cards in a RecyclerView.
 * Handles top navigation popup menu, FAB creation actions, and passes selected task ID to TaskDetailActivity.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Apply edge-to-edge system window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Top navigation back button
        findViewById<ImageView>(R.id.btnBack).setOnClickListener {
            finish()
        }

        // Inflate top navigation dropdown menu (res/menu/menu_stutager.xml) on menu icon click
        findViewById<ImageView>(R.id.btnMenu).setOnClickListener { view ->
            val popup = PopupMenu(this, view)
            popup.menuInflater.inflate(R.menu.menu_stutager, popup.menu)
            popup.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.menu_activities -> true
                    R.id.menu_all_tasks -> {
                        startActivity(Intent(this, AllTasksActivity::class.java))
                        true
                    }
                    R.id.menu_preferences -> {
                        startActivity(Intent(this, PreferencesActivity::class.java))
                        true
                    }
                    R.id.menu_help -> {
                        startActivity(Intent(this, InitialHelpActivity::class.java))
                        true
                    }
                    else -> false
                }
            }
            popup.show()
        }

        // Initialize RecyclerView with vertical layout manager
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewTasks)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Add Activity FAB button -> opens TaskEditActivity
        findViewById<FloatingActionButton>(R.id.btnAddActivity).setOnClickListener {
            val intent = Intent(this, TaskEditActivity::class.java)
            intent.putExtra("is_edit", false)
            startActivity(intent)
        }

        // All Tasks FAB button -> opens AllTasksActivity
        findViewById<FloatingActionButton>(R.id.btnAllTasks).setOnClickListener {
            startActivity(Intent(this, AllTasksActivity::class.java))
        }
    }

    /**
     * Refresh task list when resuming activity and bind task click handlers passing EXTRA_TASK_ID
     */
    override fun onResume() {
        super.onResume()
        val tasks = TaskRepository(this).tasks(summary = true).map {
            it.copy(date = when (it.id) {
                "prototip" -> getString(R.string.date_prototip)
                "examen" -> getString(R.string.date_examen)
                else -> it.date
            })
        }

        // Pass selected task ID to TaskDetailActivity on item click
        findViewById<RecyclerView>(R.id.recyclerViewTasks).adapter = TaskAdapter(tasks) { task ->
            val intent = Intent(this, TaskDetailActivity::class.java)
            intent.putExtra(TaskRepository.EXTRA_TASK_ID, task.id)
            startActivity(intent)
        }
    }
}
