package cat.ishawaheed.stutager

import android.content.Context
import androidx.core.content.edit

/**
 * Comparteix les tasques de prova entre les pantalles.
 * Desa els identificadors eliminats a SharedPreferences per conservar les
 * eliminacions quan es torna a obrir l'aplicació.
 */
class TaskRepository(context: Context) {
    private val context = context.applicationContext
    private val preferences = this.context.getSharedPreferences("task_state", Context.MODE_PRIVATE)

    /**
     * Retorna una nova llista sense les tasques eliminades.
     * Amb [summary] es mostren les tasques del resum. La llista general manté
     * el comportament existent.
     */
    fun tasks(summary: Boolean = false): List<TaskItem> {
        val deleted = preferences.getStringSet("deleted_ids", emptySet()).orEmpty()
        return sampleTasks().filter {
            it.id !in deleted && (!summary || it.id in SUMMARY_IDS)
        }
    }

    /** Busca [id] a la llista general i retorna null si no hi és. */
    fun find(id: String): TaskItem? = tasks().find { it.id == id }

    /** Desa [id] com a eliminat perquè les pantalles deixin de mostrar aquesta tasca. */
    fun delete(id: String) {
        val deleted = preferences.getStringSet("deleted_ids", emptySet()).orEmpty() + id
        preferences.edit { putStringSet("deleted_ids", deleted) }
    }

    /** Crea les tasques de prova amb identificadors fixos i textos dels recursos. */
    private fun sampleTasks(): List<TaskItem> = with(context) {
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

    companion object {
        const val EXTRA_TASK_ID = "task_id"
        private val SUMMARY_IDS = setOf("prototip", "examen", "java")
    }
}
