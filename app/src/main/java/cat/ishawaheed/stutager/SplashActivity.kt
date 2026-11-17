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

/**
 * SplashActivity
 * 
 * Launcher activity that displays the animated logo on application start,
 * provides a brief branding delay, and seamlessly opens the main entry flow (InitialHelpActivity).
 */
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)

        // Configure edge-to-edge system window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.splashContainer)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Load and trigger the custom entrance logo animation (res/anim/splash_logo_in.xml)
        val ivLogo = findViewById<ImageView>(R.id.ivSplashLogo)
        val anim = AnimationUtils.loadAnimation(this, R.anim.splash_logo_in)
        ivLogo.startAnimation(anim)

        // Delay 1.8 seconds, launch InitialHelpActivity, and finish SplashActivity to prevent returning on Back press
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, InitialHelpActivity::class.java)
            startActivity(intent)
            finish()
        }, 1800)
    }
}
