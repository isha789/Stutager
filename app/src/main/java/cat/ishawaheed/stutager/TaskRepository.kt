package cat.ishawaheed.stutager

import android.content.Context
import androidx.core.content.edit

/** Shared visibility state for the sample activities, retained across app restarts. */
class TaskRepository(context: Context) {
    private val context = context.applicationContext
    private val preferences = this.context.getSharedPreferences("task_state", Context.MODE_PRIVATE)

    fun tasks(summary: Boolean = false): List<TaskItem> {
        val deleted = preferences.getStringSet("deleted_ids", emptySet()).orEmpty()
        return sampleTasks().filter {
            it.id !in deleted && (if (summary) it.id in SUMMARY_IDS else it.id != "java")
        }
    }

    fun find(id: String): TaskItem? = (tasks() + tasks(summary = true)).find { it.id == id }

    fun delete(id: String) {
        val deleted = preferences.getStringSet("deleted_ids", emptySet()).orEmpty() + id
        preferences.edit { putStringSet("deleted_ids", deleted) }
    }

    private fun sampleTasks(): List<TaskItem> = with(context) {
        listOf(
            TaskItem(getString(R.string.task_title_prototip), getString(R.string.module_dsam),
                getString(R.string.date_priority_alta), getString(R.string.status_in_progress),
                false, BadgeType.BLUE, "prototip", getString(R.string.full_date_prototip), getString(R.string.notes_content), priorityIndex = 2),
            TaskItem(getString(R.string.task_title_examen), getString(R.string.module_programacio),
                getString(R.string.date_priority_mitjana), getString(R.string.status_pending),
                true, BadgeType.YELLOW, "examen", getString(R.string.date_examen)),
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
