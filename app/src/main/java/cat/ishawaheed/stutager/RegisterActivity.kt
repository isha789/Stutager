package cat.ishawaheed.stutager

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.button.MaterialButton

/** Recull les dades del formulari i mostra els errors que publica [RegisterViewModel]. */
class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        applySystemBarInsets()

        val viewModel = ViewModelProvider(this)[RegisterViewModel::class.java]
        val fullName = findViewById<EditText>(R.id.registerFullName)
        val email = findViewById<EditText>(R.id.registerEmail)
        val password = findViewById<EditText>(R.id.registerPassword)
        val confirmation = findViewById<EditText>(R.id.registerConfirmPassword)
        val fullNameError = findViewById<TextView>(R.id.registerFullNameError)
        val emailError = findViewById<TextView>(R.id.registerEmailError)
        val passwordError = findViewById<TextView>(R.id.registerPasswordError)
        val confirmationError = findViewById<TextView>(R.id.registerConfirmationError)

        viewModel.uiState.observe(this) { state ->
            showError(fullNameError, state.fullNameError, R.string.register_full_name_required)
            showError(emailError, state.emailError, R.string.register_email_required)
            showError(passwordError, state.passwordError, R.string.register_password_required)
            showError(confirmationError, state.confirmationError, R.string.register_confirmation_required)

            if (state.submissionAccepted) {
                viewModel.onSubmissionHandled()
                finish()
            }
        }

        findViewById<ImageView>(R.id.btnBack).setOnClickListener {
            finish()
        }

        findViewById<MaterialButton>(R.id.btnCreateAccount).setOnClickListener {
            viewModel.submit(
                fullName.text.toString(),
                email.text.toString(),
                password.text.toString(),
                confirmation.text.toString()
            )
        }

        findViewById<TextView>(R.id.tvAlreadyHaveAccount).setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    /** Mostra el missatge del camp o l'amaga quan el camp és vàlid. */
    private fun showError(view: TextView, error: AuthValidator.Error?, @StringRes requiredMessage: Int) {
        val messageId = when (error) {
            null -> null
            AuthValidator.Error.REQUIRED -> requiredMessage
            AuthValidator.Error.FULL_NAME_TOO_SHORT -> R.string.register_full_name_too_short
            AuthValidator.Error.FULL_NAME_TOO_LONG -> R.string.register_full_name_too_long
            AuthValidator.Error.INVALID_EMAIL -> R.string.register_invalid_email
            AuthValidator.Error.EMAIL_TOO_LONG -> R.string.register_email_too_long
            AuthValidator.Error.PASSWORD_TOO_SHORT -> R.string.register_password_too_short
            AuthValidator.Error.PASSWORD_TOO_LONG -> R.string.register_password_too_long
            AuthValidator.Error.PASSWORD_MISSING_LETTER -> R.string.register_password_missing_letter
            AuthValidator.Error.PASSWORD_MISSING_DIGIT -> R.string.register_password_missing_digit
            AuthValidator.Error.PASSWORD_MISMATCH -> R.string.register_password_mismatch
        }
        view.text = messageId?.let { getString(it) }
        view.isVisible = messageId != null
    }
}
