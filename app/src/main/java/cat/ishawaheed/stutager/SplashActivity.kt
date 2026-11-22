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
    private val handler = Handler(Looper.getMainLooper())
    private val openHelp = Runnable {
        if (!isFinishing) {
            startActivity(Intent(this, InitialHelpActivity::class.java))
            finish()
        }
    }

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

    }

    override fun onResume() {
        super.onResume()
        val ivLogo = findViewById<ImageView>(R.id.ivSplashLogo)
        ivLogo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.splash_logo_in))
        handler.postDelayed(openHelp, 1800)
    }

    override fun onPause() {
        handler.removeCallbacks(openHelp)
        findViewById<ImageView>(R.id.ivSplashLogo).clearAnimation()
        super.onPause()
    }
}
