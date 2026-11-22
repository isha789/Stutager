package cat.ishawaheed.stutager

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton


/**
 * RegisterActivity
 *
 * User registration form screen with full name, email, password input fields,
 * account creation button, and navigation to LoginActivity.
 */
class RegisterActivity :AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        applySystemBarInsets()

        // Back button
        findViewById<ImageView>(R.id.btnBack).setOnClickListener {
            finish()
        }

        // Create account button
        findViewById<MaterialButton>(R.id.btnCreateAccount).setOnClickListener {
            finish()
        }

        // "Ja tens compte? Inicia sessió" opens LoginActivity
        findViewById<TextView>(R.id.tvAlreadyHaveAccount).setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
