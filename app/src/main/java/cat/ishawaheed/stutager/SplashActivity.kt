package cat.ishawaheed.stutager

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.splashContainer)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Load and start logo animation
        val ivLogo = findViewById<ImageView>(R.id.ivSplashLogo)
        val anim = AnimationUtils.loadAnimation(this, R.anim.splash_logo_in)
        ivLogo.startAnimation(anim)

        // Delay 1.8 seconds, then open existing entry flow (InitialHelpActivity) and finish splash
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, InitialHelpActivity::class.java)
            startActivity(intent)
            finish()
        }, 1800)
    }
}
