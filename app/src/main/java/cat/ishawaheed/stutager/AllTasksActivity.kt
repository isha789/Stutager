package cat.ishawaheed.stutager

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AllTasksActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_all_tasks)

        val rootView = findViewById<View>(R.id.main)
        ViewCompat.setOnApplyWindowInsetsListener(rootView) { v, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updatePadding(top = insets.top, bottom = insets.bottom, left = insets.left, right = insets.right)
            WindowInsetsCompat.CONSUMED
        }

        findViewById<ImageView>(R.id.btnMenu).setOnClickListener {
            finish()
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewAllTasks)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val allTasks = listOf(
            TaskItem("Prototip DSAM", "Mòdul: DSAM", "📅 30 oct 2026   🚩 Alta", "En curs", false, BadgeType.BLUE),
            TaskItem("Examen Kotlin", "Mòdul: Programació", "📅 4 nov 2026   🚩 Mitjana", "Pendent", true, BadgeType.YELLOW),
            TaskItem("AppBanc", "Mòdul: Accés a Dades", "📅 6 nov 2026   🚩 Mitjana", "Pendent", false, BadgeType.YELLOW),
            TaskItem("Informe accessibilitat", "Mòdul: DSAM", "📅 8 nov 2026   🚩 Mitjana", "Pendent", false, BadgeType.YELLOW),
            TaskItem("Pràctica SQLite", "Mòdul: Accés a Dades", "📅 10 nov 2026   🚩 Baixa", "Pendent", false, BadgeType.YELLOW),
            TaskItem("Exercicis Kotlin", "Mòdul: Programació", "📅 20 oct 2026   🚩 Baixa", "Completada", false, BadgeType.GREEN)
        )

        recyclerView.adapter = TaskAdapter(allTasks) { _ ->
            startActivity(Intent(this, TaskDetailActivity::class.java))
        }

        findViewById<FloatingActionButton>(R.id.btnAddActivity).setOnClickListener {
            startActivity(Intent(this, TaskEditActivity::class.java))
        }
    }
}
