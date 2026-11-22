package cat.ishawaheed.stutager

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

/**
 * InitialHelpActivity
 * 
 * Onboarding screen displaying key features (Afegeix, Organitza, Actualitza)
 * and providing navigation to the Welcome screen.
 */
class InitialHelpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_initial_help)
        applySystemBarInsets()

        val fromMenu = intent.getBooleanExtra(EXTRA_FROM_MENU, false)
        findViewById<Button>(R.id.btnComenca).apply {
            if (fromMenu) setText(R.string.back_desc)
            setOnClickListener {
                if (fromMenu) finish()
                else startActivity(Intent(this@InitialHelpActivity, WelcomeActivity::class.java))
            }
        }
    }

    companion object {
        const val EXTRA_FROM_MENU = "help_from_menu"
    }
}
