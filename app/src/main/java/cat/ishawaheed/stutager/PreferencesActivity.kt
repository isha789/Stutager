package cat.ishawaheed.stutager

import android.os.Bundle
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButtonToggleGroup

class PreferencesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preferences)
        findViewById<MaterialButtonToggleGroup>(R.id.themeToggleGroup).allowLabelWrapping()
        findViewById<MaterialButtonToggleGroup>(R.id.sortToggleGroup).allowLabelWrapping()
        applySystemBarInsets()

        findViewById<android.view.View>(
            R.id.backPreferencesButton
        ).setOnClickListener {
            finish()
        }

        // Botó de desar preferències
        findViewById<MaterialButton>(R.id.savePreferencesButton)?.setOnClickListener {
            Toast.makeText(this, "Preferències desades", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
