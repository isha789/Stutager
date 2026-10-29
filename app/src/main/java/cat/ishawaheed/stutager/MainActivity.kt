package cat.ishawaheed.stutager

import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val chipAll = findViewById<TextView>(R.id.chipAll)
        val chipTreballs = findViewById<TextView>(R.id.chipTreballs)
        val chipExamens = findViewById<TextView>(R.id.chipExamens)

        fun selectChip(selected: TextView) {
            listOf(chipAll, chipTreballs, chipExamens).forEach { chip ->
                if (chip == selected) {
                    chip.setBackgroundResource(R.drawable.bg_chip_selected)
                    chip.setTextColor(getColor(R.color.white_background))
                } else {
                    chip.setBackgroundResource(R.drawable.bg_chip_unselected)
                    chip.setTextColor(getColor(R.color.text_gray))
                }
            }
        }

        chipAll?.setOnClickListener { selectChip(chipAll) }
        chipTreballs?.setOnClickListener { selectChip(chipTreballs) }
        chipExamens?.setOnClickListener { selectChip(chipExamens) }

        findViewById<ImageView>(R.id.btnMenu)?.setOnClickListener {
            Toast.makeText(this, "Menú principal", Toast.LENGTH_SHORT).show()
        }

        findViewById<LinearLayout>(R.id.cardPrototipDSAM)?.setOnClickListener {
            Toast.makeText(this, "Prototip DSAM seleccionat", Toast.LENGTH_SHORT).show()
        }

        findViewById<LinearLayout>(R.id.cardExamenKotlin)?.setOnClickListener {
            Toast.makeText(this, "Examen Kotlin seleccionat", Toast.LENGTH_SHORT).show()
        }

        findViewById<LinearLayout>(R.id.cardJavaFitxers)?.setOnClickListener {
            Toast.makeText(this, "Java fitxers seleccionat", Toast.LENGTH_SHORT).show()
        }

        findViewById<FloatingActionButton>(R.id.fabAddActivity)?.setOnClickListener {
            Toast.makeText(this, "Nova activitat (veure 6)", Toast.LENGTH_SHORT).show()
        }
    }
}
