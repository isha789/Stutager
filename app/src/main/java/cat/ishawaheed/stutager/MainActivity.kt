package cat.ishawaheed.stutager

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
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

        // Add Activity -> Screen 6
        findViewById<FloatingActionButton>(R.id.btnAddActivity).setOnClickListener {
            startActivity(Intent(this, TaskEditActivity::class.java))
        }

        // All Tasks -> Screen 9
        findViewById<FloatingActionButton>(R.id.btnAllTasks).setOnClickListener {
            startActivity(Intent(this, AllTasksActivity::class.java))
        }
    }
}
