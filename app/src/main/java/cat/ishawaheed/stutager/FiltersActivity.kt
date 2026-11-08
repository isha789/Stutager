package cat.ishawaheed.stutager

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class FiltersActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_filters)

        val filtersContent = findViewById<View>(R.id.filtersContent)

// Guardem el padding definit a l’XML abans d’aplicar els insets.
        val initialLeft = filtersContent.paddingLeft
        val initialTop = filtersContent.paddingTop
        val initialRight = filtersContent.paddingRight
        val initialBottom = filtersContent.paddingBottom

        ViewCompat.setOnApplyWindowInsetsListener(filtersContent) { v, insets ->
            val systemBars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars()
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
}
