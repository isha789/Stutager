package cat.ishawaheed.stutager

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.LinearLayout
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


/**
 * FiltersActivity
 *
 * Activity responsible for managing the search and filter screen for activities.
 *
 * Allows filtering tasks by search text, Module, Type, Status, and Priority,
 * providing options to reset form fields and apply active filters.
 */
class FiltersActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_filters)
        findViewById<MaterialButtonToggleGroup>(R.id.typeFilterToggleGroup).allowLabelWrapping()
        findViewById<LinearLayout>(R.id.firstFilterResult).stackResultWhenConstrained()
        findViewById<LinearLayout>(R.id.secondFilterResult).stackResultWhenConstrained()

        val filtersContent = findViewById<View>(R.id.filtersContent)

// Guardem el padding definit a l’XML abans d’aplicar els insets.
        val initialLeft = filtersContent.paddingLeft
        val initialTop = filtersContent.paddingTop
        val initialRight = filtersContent.paddingRight
        val initialBottom = filtersContent.paddingBottom

        ViewCompat.setOnApplyWindowInsetsListener(filtersContent) { v, insets ->
            val systemBars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.ime()
            )

            v.setPadding(
                initialLeft + systemBars.left,
                initialTop + systemBars.top,
                initialRight + systemBars.right,
                initialBottom + systemBars.bottom
            )

            insets
        }

        // Referències als elements del formulari de filtres
        val filterSearchEditText = findViewById<EditText>(R.id.filterSearchEditText)
        val moduleFilterSpinner = findViewById<Spinner>(R.id.moduleFilterSpinner)
        val statusFilterSpinner = findViewById<Spinner>(R.id.statusFilterSpinner)
        val priorityFilterSpinner = findViewById<Spinner>(R.id.priorityFilterSpinner)
        val typeFilterToggleGroup = findViewById<MaterialButtonToggleGroup>(R.id.typeFilterToggleGroup)

        // Botó de retorn enrere
        findViewById<ImageButton>(R.id.backFiltersButton)?.setOnClickListener {
            finish()
        }

        // Botó de netejar filtres
        findViewById<MaterialButton>(R.id.clearFiltersButton)?.setOnClickListener {
            filterSearchEditText?.setText("")
            moduleFilterSpinner?.setSelection(0)
            statusFilterSpinner?.setSelection(0)
            priorityFilterSpinner?.setSelection(0)
            typeFilterToggleGroup?.check(R.id.typeFilterAllButton)
            Toast.makeText(this, "Filtres reiniciats", Toast.LENGTH_SHORT).show()
        }

        // Botó d'aplicar filtres
        findViewById<MaterialButton>(R.id.applyFiltersButton)?.setOnClickListener {
            Toast.makeText(this, "Filtres aplicats", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        val store = TaskRepository(this)
        val results = listOf(R.id.firstFilterResult to "prototip", R.id.secondFilterResult to "accessibility")
        var count = 0
        for ((viewId, taskId) in results) {
            val row = findViewById<View>(viewId)
            val exists = store.find(taskId) != null
            row.visibility = if (exists) View.VISIBLE else View.GONE
            if (exists) count++
        }
        findViewById<TextView>(R.id.tvFilterResultsCount).text = resources.getQuantityString(
            R.plurals.filter_result_count, count, count
        )
    }
}
