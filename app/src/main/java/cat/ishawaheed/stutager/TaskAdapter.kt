package cat.ishawaheed.stutager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityActionCompat

/**
 * Connecta les tasques amb les files del RecyclerView.
 * [onItemClick] avisa l'Activity de quina tasca s'ha premut.
 */
class TaskAdapter(
    private var tasks: List<TaskItem>,
    private val onItemClick: (TaskItem) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    /** Guarda les referències als textos i a la icona d'una fila per reutilitzar-los. */
    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivTaskType: ImageView = itemView.findViewById(R.id.ivTaskType)
        val tvTaskTitle: TextView = itemView.findViewById(R.id.tvTaskTitle)
        val tvTaskModule: TextView = itemView.findViewById(R.id.tvTaskModule)
        val tvTaskDate: TextView = itemView.findViewById(R.id.tvTaskDate)
        val tvTaskPriority: TextView = itemView.findViewById(R.id.tvTaskPriority)
        val tvTaskBadge: TextView = itemView.findViewById(R.id.tvTaskBadge)
    }

    /** Crea una fila a partir d'item_task.xml i la guarda en un ViewHolder. */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    /**
     * Mostra les dades de la tasca situada a [position] i prepara el seu clic.
     * El clic normal i l'acció del lector de pantalla obren la mateixa tasca.
     */
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.tvTaskTitle.text = task.title
        holder.tvTaskModule.text = task.module
        holder.tvTaskDate.text = task.date
        holder.tvTaskPriority.text = holder.itemView.context.getString(
            R.string.priority_detail, holder.itemView.context.getString(task.priorityLabel)
        )
        holder.tvTaskBadge.text = task.status

        if (task.isExam) {
            holder.ivTaskType.setImageResource(R.drawable.ic_exam)
        } else {
            holder.ivTaskType.setImageResource(R.drawable.ic_task)
        }
        holder.ivTaskType.contentDescription = holder.itemView.context.getString(
            if (task.isExam) R.string.type_examen else R.string.type_treball
        )

        when (task.badgeType) {
            BadgeType.BLUE -> {
                holder.tvTaskBadge.setBackgroundResource(R.drawable.badge_blue)
                holder.tvTaskBadge.setTextColor(holder.itemView.context.getColor(R.color.badge_blue_text))
            }
            BadgeType.YELLOW -> {
                holder.tvTaskBadge.setBackgroundResource(R.drawable.badge_yellow)
                holder.tvTaskBadge.setTextColor(holder.itemView.context.getColor(R.color.badge_yellow_text))
            }
            BadgeType.GREEN -> {
                holder.tvTaskBadge.setBackgroundResource(R.drawable.badge_green)
                holder.tvTaskBadge.setTextColor(holder.itemView.context.getColor(R.color.badge_green_text))
            }
            BadgeType.RED -> {
                holder.tvTaskBadge.setBackgroundResource(R.drawable.badge_red)
                holder.tvTaskBadge.setTextColor(holder.itemView.context.getColor(R.color.badge_red_text))
            }
        }

        holder.itemView.setOnClickListener {
            onItemClick(task)
        }
        ViewCompat.setScreenReaderFocusable(holder.itemView, true)
        ViewCompat.replaceAccessibilityAction(holder.itemView, AccessibilityActionCompat.ACTION_CLICK,
            holder.itemView.context.getString(R.string.open_task_desc, task.title)) { _, _ ->
            onItemClick(task)
            true
        }
    }

    /** Retorna el nombre de files que ha de mostrar el RecyclerView. */
    override fun getItemCount(): Int = tasks.size

    /**
     * Guarda una còpia de [newTasks] i avisa que cal tornar a mostrar les dades.
     * Aquesta actualització completa és senzilla per a la petita llista de prova.
     */
    fun updateTasks(newTasks: List<TaskItem>) {
        tasks = newTasks.toList()
        notifyDataSetChanged()
    }
}
