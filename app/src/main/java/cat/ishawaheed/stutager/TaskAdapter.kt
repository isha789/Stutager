package cat.ishawaheed.stutager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(
    private val tasks: List<TaskItem>,
    private val onItemClick: (TaskItem) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivTaskType: ImageView = itemView.findViewById(R.id.ivTaskType)
        val tvTaskTitle: TextView = itemView.findViewById(R.id.tvTaskTitle)
        val tvTaskModule: TextView = itemView.findViewById(R.id.tvTaskModule)
        val tvTaskDate: TextView = itemView.findViewById(R.id.tvTaskDate)
        val tvTaskBadge: TextView = itemView.findViewById(R.id.tvTaskBadge)
        val ivTaskAction: ImageView = itemView.findViewById(R.id.ivTaskAction)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.tvTaskTitle.text = task.title
        holder.tvTaskModule.text = task.module
        holder.tvTaskDate.text = task.date
        holder.tvTaskBadge.text = task.status

        if (task.isExam) {
            holder.ivTaskType.setImageResource(R.drawable.ic_exam)
        } else {
            holder.ivTaskType.setImageResource(R.drawable.ic_task)
        }

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
                holder.tvTaskBadge.setBackgroundResource(R.drawable.badge_yellow) // fallback or green
                holder.tvTaskBadge.setTextColor(holder.itemView.context.getColor(R.color.badge_green_text))
            }
            BadgeType.RED -> {
                holder.tvTaskBadge.setBackgroundResource(R.drawable.badge_yellow)
                holder.tvTaskBadge.setTextColor(holder.itemView.context.getColor(R.color.badge_red_text))
            }
        }

        holder.itemView.setOnClickListener {
            onItemClick(task)
        }
    }

    override fun getItemCount(): Int = tasks.size
}
