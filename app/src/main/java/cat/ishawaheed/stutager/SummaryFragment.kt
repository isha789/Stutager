package cat.ishawaheed.stutager

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

/** Mostra fins a tres tasques i actualitza el resum quan torna al primer pla. */
class SummaryFragment : Fragment() {
    private var taskAdapter: TaskAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_summary, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<LinearLayout>(R.id.activityTabs).fitTabLabels()
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewTasks)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        taskAdapter = TaskAdapter(emptyList()) { task ->
            startActivity(Intent(requireContext(), TaskDetailActivity::class.java)
                .putExtra(TaskRepository.EXTRA_TASK_ID, task.id))
        }
        recyclerView.adapter = taskAdapter

        view.findViewById<FloatingActionButton>(R.id.btnAddActivity).setOnClickListener {
            startActivity(Intent(requireContext(), TaskEditActivity::class.java))
        }
    }

    /** Torna a llegir les dades quan es tanca el detall o l'editor. */
    override fun onResume() {
        super.onResume()
        val root = requireView()
        val tasks = TaskRepository.tasks(requireContext(), summary = true)
        taskAdapter?.updateTasks(tasks)
        root.findViewById<View>(R.id.recyclerViewTasks).isVisible = tasks.isNotEmpty()
        root.findViewById<View>(R.id.tvEmptyTasks).isVisible = tasks.isEmpty()
        root.findViewById<TextView>(R.id.tvTasksCount).text =
            resources.getQuantityString(R.plurals.task_count, tasks.size, tasks.size)
    }

    override fun onDestroyView() {
        view?.findViewById<RecyclerView>(R.id.recyclerViewTasks)?.adapter = null
        taskAdapter = null
        super.onDestroyView()
    }
}
