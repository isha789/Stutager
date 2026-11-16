package cat.ishawaheed.stutager

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.LinearLayout
import com.google.android.material.button.MaterialButtonToggleGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

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

        // Botó de retorn enrere
        findViewById<ImageButton>(R.id.backFiltersButton)?.setOnClickListener {
            finish()
        }

        // Botó d'aplicar filtres (tanca la pantalla per simular l'aplicació)
        findViewById<View>(R.id.applyFiltersButton)?.setOnClickListener {
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
