package cat.ishawaheed.stutager

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ActivitiesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activities_summary)

        // Menu button -> Shows PopupMenu using menu_stutager.xml
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

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewTasks)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val sampleTasks = listOf(
            TaskItem("Prototip DSAM", "Mòdul: DSAM", "📅 30 oct 2026", "En curs", false, BadgeType.BLUE),
            TaskItem("Examen Kotlin", "Mòdul: Programació", "📅 4 nov 2026", "Pendent", true, BadgeType.YELLOW),
            TaskItem("Java fitxers", "Mòdul: Programació", "📅 6 nov 2026", "Pendent", false, BadgeType.YELLOW)
        )

        recyclerView.adapter = TaskAdapter(sampleTasks) { _ ->
            startActivity(Intent(this, TaskDetailActivity::class.java))
        }

        findViewById<FloatingActionButton>(R.id.btnAddActivity).setOnClickListener {
            val intent = Intent(this, TaskEditActivity::class.java)
            intent.putExtra("is_edit", false)
            startActivity(intent)
        }

        findViewById<FloatingActionButton>(R.id.btnAllTasks).setOnClickListener {
            startActivity(Intent(this, AllTasksActivity::class.java))
        }
    }
}
