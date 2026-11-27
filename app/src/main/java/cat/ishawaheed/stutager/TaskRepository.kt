package cat.ishawaheed.stutager

import android.content.Context
import androidx.core.content.edit

/**
 * Comparteix una única llista mutable de tasques durant el procés.
 * Només els identificadors de les tasques d'exemple eliminades es desen a SharedPreferences.
 */
object TaskRepository {
    const val EXTRA_TASK_ID = "task_id"
    private const val USER_ID_PREFIX = "user-"
    private val SUMMARY_IDS = setOf("prototip", "examen", "java")
    private val items = mutableListOf<TaskItem>()
    private var initialized = false
    private var nextUserId = 1L

    private fun initialize(context: Context) {
        if (initialized) return
        val preferences = context.getSharedPreferences("task_state", Context.MODE_PRIVATE)
        val deleted = preferences.getStringSet("deleted_ids", emptySet()).orEmpty()
        items.addAll(sampleTasks(context).filter { it.id !in deleted })
        initialized = true
    }

    /** Retorna la llista general o un resum amb les tasques noves abans de les d'exemple. */
    fun tasks(context: Context, summary: Boolean = false): List<TaskItem> {
        initialize(context)
        if (!summary) return items.toList()
        return items.filter { it.id.startsWith(USER_ID_PREFIX) || it.id in SUMMARY_IDS }
            .take(SUMMARY_IDS.size)
    }

    fun find(context: Context, id: String): TaskItem? {
        initialize(context)
        return items.find { it.id == id }
    }

    /** Afegeix una tasca amb un ID reservat a les creacions d'aquest procés. */
    fun add(context: Context, task: TaskItem): TaskItem {
        initialize(context)
        val created = task.copy(id = "$USER_ID_PREFIX${nextUserId++}")
        items.add(0, created)
        return created
    }

    /** Substitueix la tasca a la mateixa posició conservant el seu ID. */
    fun update(context: Context, task: TaskItem): Boolean {
        initialize(context)
        val index = items.indexOfFirst { it.id == task.id }
        if (index == -1) return false
        items[index] = task
        return true
    }

    /** Elimina la tasca i recorda l'ID després del reinici si és d'exemple. */
    fun delete(context: Context, id: String): Boolean {
        initialize(context)
        val index = items.indexOfFirst { it.id == id }
        if (index == -1) return false
        items.removeAt(index)
        if (!id.startsWith(USER_ID_PREFIX)) {
            val preferences = context.getSharedPreferences("task_state", Context.MODE_PRIVATE)
            val deleted = preferences.getStringSet("deleted_ids", emptySet()).orEmpty() + id
            preferences.edit { putStringSet("deleted_ids", deleted) }
        }
        return true
    }

    /** Crea les tasques de prova amb identificadors fixos i textos dels recursos. */
    private fun sampleTasks(context: Context): List<TaskItem> = with(context) {
        listOf(
            TaskItem(getString(R.string.task_title_prototip), getString(R.string.module_dsam),
                getString(R.string.date_prototip), getString(R.string.status_in_progress),
                false, BadgeType.BLUE, "prototip", notes = getString(R.string.notes_content), priorityIndex = 2),
            TaskItem(getString(R.string.task_title_examen), getString(R.string.module_programacio),
                getString(R.string.date_examen), getString(R.string.status_pending),
                true, BadgeType.YELLOW, "examen"),
            TaskItem(getString(R.string.task_title_java), getString(R.string.module_programacio),
                getString(R.string.date_java), getString(R.string.status_pending),
                false, BadgeType.YELLOW, "java"),
            TaskItem(getString(R.string.task_title_appbanc), getString(R.string.module_data),
                getString(R.string.date_appbanc), getString(R.string.status_pending),
                false, BadgeType.YELLOW, "appbanc"),
            TaskItem(getString(R.string.filters_second_title), getString(R.string.module_dsam),
                getString(R.string.date_accessibility), getString(R.string.status_pending),
                false, BadgeType.YELLOW, "accessibility"),
            TaskItem(getString(R.string.task_title_sqlite), getString(R.string.module_data),
                getString(R.string.date_sqlite), getString(R.string.status_pending),
                false, BadgeType.YELLOW, "sqlite", priorityIndex = 0),
            TaskItem(getString(R.string.task_title_exercises), getString(R.string.module_programacio),
                getString(R.string.date_exercises), getString(R.string.badge_completed),
                false, BadgeType.GREEN, "exercises", priorityIndex = 0)
        )
    }

}
