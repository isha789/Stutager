package cat.ishawaheed.stutager

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.isVisible
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
    private lateinit var taskAdapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        applySystemBarInsets()
        findViewById<LinearLayout>(R.id.activityTabs).fitTabLabels()

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
                        startActivity(Intent(this, InitialHelpActivity::class.java)
                            .putExtra(InitialHelpActivity.EXTRA_FROM_MENU, true))
                        true
                    }
                    R.id.menu_account -> {
                        startActivity(Intent(this, WelcomeActivity::class.java)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP))
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
        taskAdapter = TaskAdapter(emptyList()) { task ->
            startActivity(Intent(this, TaskDetailActivity::class.java)
                .putExtra(TaskRepository.EXTRA_TASK_ID, task.id))
        }
        recyclerView.adapter = taskAdapter

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

    override fun onResume() {
        super.onResume()
        val tasks = TaskRepository(this).tasks(summary = true)
        taskAdapter.updateTasks(tasks)
        findViewById<View>(R.id.recyclerViewTasks).isVisible = tasks.isNotEmpty()
        findViewById<View>(R.id.tvEmptyTasks).isVisible = tasks.isEmpty()
        findViewById<TextView>(R.id.tvTasksCount).text =
            resources.getQuantityString(R.plurals.task_count, tasks.size, tasks.size)
    }
}
