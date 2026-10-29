package cat.ishawaheed.stutager

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ActivitiesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activities_summary)

        // Menu button (three lines) opens All Tasks (Screen 9) instead of finishing
        findViewById<ImageView>(R.id.btnMenu).setOnClickListener {
            startActivity(Intent(this, AllTasksActivity::class.java))
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
            startActivity(Intent(this, TaskEditActivity::class.java))
        }

        findViewById<FloatingActionButton>(R.id.btnAllTasks).setOnClickListener {
            startActivity(Intent(this, AllTasksActivity::class.java))
        }
    }
}
