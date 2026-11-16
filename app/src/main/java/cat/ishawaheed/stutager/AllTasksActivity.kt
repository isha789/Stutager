package cat.ishawaheed.stutager

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

/** Mostra la llista general de tasques i els accessos als detalls, al formulari i als filtres. */
class AllTasksActivity : AppCompatActivity() {
    private lateinit var taskAdapter: TaskAdapter

    /**
     * Prepara el RecyclerView vertical i crea l'Adapter una sola vegada.
     * El clic d'una fila passa el seu task_id als detalls amb un Intent.
     * Afegir obre el formulari amb is_edit=false; Filtres obre la pantalla existent.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_all_tasks)
        findViewById<LinearLayout>(R.id.activityTabs).fitTabLabels()

        val rootView = findViewById<View>(R.id.main)
        ViewCompat.setOnApplyWindowInsetsListener(rootView) { v, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updatePadding(top = insets.top, bottom = insets.bottom, left = insets.left, right = insets.right)
            WindowInsetsCompat.CONSUMED
        }

        // Menu button -> Shows PopupMenu using menu_stutager.xml
        findViewById<ImageView>(R.id.btnMenu).setOnClickListener { view ->
            val popup = PopupMenu(this, view)
            popup.menuInflater.inflate(R.menu.menu_stutager, popup.menu)
            popup.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.menu_activities -> {
                        startActivity(Intent(this, MainActivity::class.java))
                        true
                    }
                    R.id.menu_all_tasks -> true
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

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewAllTasks)
        recyclerView.layoutManager = LinearLayoutManager(this)
        taskAdapter = TaskAdapter(emptyList()) { task ->
            startActivity(Intent(this, TaskDetailActivity::class.java).putExtra(TaskRepository.EXTRA_TASK_ID, task.id))
        }
        recyclerView.adapter = taskAdapter

        findViewById<FloatingActionButton>(R.id.btnAddActivity).setOnClickListener {
            val intent = Intent(this, TaskEditActivity::class.java)
            intent.putExtra("is_edit", false)
            startActivity(intent)
        }

        findViewById<View>(R.id.btnFilters)?.setOnClickListener {
            startActivity(Intent(this, FiltersActivity::class.java))
        }
    }

    /**
     * Consulta el repositori quan es torna a la pantalla i actualitza el mateix Adapter.
     * Així es reflecteixen les eliminacions. També actualitza el comptador i mostra
     * un missatge en lloc de la llista quan no queden tasques.
     */
    override fun onResume() {
        super.onResume()
        val tasks = TaskRepository(this).tasks()
        taskAdapter.updateTasks(tasks)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewAllTasks)
        val emptyMessage = findViewById<TextView>(R.id.tvEmptyTasks)
        if (tasks.isEmpty()) {
            recyclerView.visibility = View.GONE
            emptyMessage.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.VISIBLE
            emptyMessage.visibility = View.GONE
        }
        findViewById<TextView>(R.id.tvTasksCount).text = resources.getQuantityString(R.plurals.task_count, tasks.size, tasks.size)
    }
}
