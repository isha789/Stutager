package cat.ishawaheed.stutager

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

/**
 * LoginActivity
 * 
 * Manages the Login user interface view. Observes [LoginViewModel] state to display localized
 * field error messages and handle navigation on valid form submission.
 */
class LoginActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModels()
    private var isPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        // Apply system bar and IME (keyboard) insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.ime())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val tvEmailError = findViewById<TextView>(R.id.tvEmailError)
        val tvPasswordError = findViewById<TextView>(R.id.tvPasswordError)
        val btnTogglePassword = findViewById<ImageView>(R.id.btnTogglePassword)

        // Back button action
        findViewById<ImageView>(R.id.btnBack)?.setOnClickListener {
            finish()
        }

        // Toggle password visibility mode (visible text vs hidden dots)
        btnTogglePassword?.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            if (isPasswordVisible) {
                etPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                etPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
            etPassword.setSelection(etPassword.text.length)
            btnTogglePassword.contentDescription = getString(
                if (isPasswordVisible) R.string.hide_password_desc else R.string.show_password_desc
            )
        }

        // Forgot password link action
        findViewById<TextView>(R.id.tvForgotPassword)?.setOnClickListener {
            Toast.makeText(this, R.string.password_recovery_message, Toast.LENGTH_SHORT).show()
        }

        // Observe ViewModel state
        viewModel.uiState.observe(this) { state ->
            // Display or clear email error
            if (state.emailError != null) {
                tvEmailError.text = getErrorMessage(state.emailError)
                tvEmailError.visibility = View.VISIBLE
            } else {
                tvEmailError.visibility = View.GONE
            }

            // Display or clear password error
            if (state.passwordError != null) {
                tvPasswordError.text = getErrorMessage(state.passwordError)
                tvPasswordError.visibility = View.VISIBLE
            } else {
                tvPasswordError.visibility = View.GONE
            }

            // Navigate to MainActivity on accepted submission
            if (state.submissionAccepted) {
                viewModel.onSubmissionHandled()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }

        // Enter / Login button action -> submits form to ViewModel
        findViewById<Button>(R.id.btnEntra)?.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            viewModel.submit(email, password)
        }
    }

    /**
     * Maps [AuthValidator.Error] enum codes to localized resource strings.
     */
    private fun getErrorMessage(error: AuthValidator.Error): String {
        return when (error) {
            AuthValidator.Error.REQUIRED -> getString(R.string.login_error_required)
            AuthValidator.Error.INVALID_EMAIL -> getString(R.string.login_error_invalid_email)
            AuthValidator.Error.EMAIL_TOO_LONG -> getString(R.string.login_error_email_too_long)
            AuthValidator.Error.PASSWORD_TOO_SHORT -> getString(R.string.login_error_password_too_short)
            AuthValidator.Error.PASSWORD_TOO_LONG -> getString(R.string.login_error_password_too_long)
            AuthValidator.Error.PASSWORD_MISSING_LETTER -> getString(R.string.login_error_password_missing_letter)
            AuthValidator.Error.PASSWORD_MISSING_DIGIT -> getString(R.string.login_error_password_missing_digit)
            else -> getString(R.string.login_error_required)
        }
    }
}
