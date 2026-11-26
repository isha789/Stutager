package cat.ishawaheed.stutager

/**
 * Shared validation rules and results for Login and Registration forms.
 */
object AuthValidator {
    const val MIN_FULL_NAME_LENGTH = 2
    const val MAX_FULL_NAME_LENGTH = 80
    const val MAX_EMAIL_LENGTH = 254
    const val MIN_PASSWORD_LENGTH = 8
    const val MAX_PASSWORD_LENGTH = 128

    enum class Error {
        REQUIRED,
        FULL_NAME_TOO_SHORT,
        FULL_NAME_TOO_LONG,
        INVALID_EMAIL,
        EMAIL_TOO_LONG,
        PASSWORD_TOO_SHORT,
        PASSWORD_TOO_LONG,
        PASSWORD_MISSING_LETTER,
        PASSWORD_MISSING_DIGIT,
        PASSWORD_MISMATCH
    }

    data class LoginResult(
        val emailError: Error? = null,
        val passwordError: Error? = null
    ) {
        val isValid: Boolean
            get() = emailError == null && passwordError == null
    }

    data class RegistrationResult(
        val fullNameError: Error? = null,
        val emailError: Error? = null,
        val passwordError: Error? = null,
        val confirmationError: Error? = null
    ) {
        val isValid: Boolean
            get() = fullNameError == null && emailError == null &&
                    passwordError == null && confirmationError == null
    }

    private val emailPattern = Regex("^[^\\s@]+@[^\\s@.]+(?:\\.[^\\s@.]+)+$")

    fun normalizeFullName(value: String): String = value.trim()
    fun normalizeEmail(value: String): String = value.trim()

    fun validateFullName(value: String): Error? {
        val normalized = normalizeFullName(value)
        return when {
            normalized.isBlank() -> Error.REQUIRED
            normalized.length < MIN_FULL_NAME_LENGTH -> Error.FULL_NAME_TOO_SHORT
            normalized.length > MAX_FULL_NAME_LENGTH -> Error.FULL_NAME_TOO_LONG
            else -> null
        }
    }

    fun validateEmail(value: String): Error? {
        val normalized = normalizeEmail(value)
        return when {
            normalized.isBlank() -> Error.REQUIRED
            normalized.length > MAX_EMAIL_LENGTH -> Error.EMAIL_TOO_LONG
            !emailPattern.matches(normalized) -> Error.INVALID_EMAIL
            else -> null
        }
    }

    fun validateLoginPassword(value: String): Error? = validateRegistrationPassword(value)

    fun validateRegistrationPassword(value: String): Error? {
        return when {
            value.isBlank() -> Error.REQUIRED
            value.length < MIN_PASSWORD_LENGTH -> Error.PASSWORD_TOO_SHORT
            value.length > MAX_PASSWORD_LENGTH -> Error.PASSWORD_TOO_LONG
            value.none { it.isLetter() } -> Error.PASSWORD_MISSING_LETTER
            value.none { it.isDigit() } -> Error.PASSWORD_MISSING_DIGIT
            else -> null
        }
    }

    fun validatePasswordConfirmation(password: String, confirmation: String): Error? {
        return when {
            confirmation.isBlank() -> Error.REQUIRED
            password != confirmation -> Error.PASSWORD_MISMATCH
            else -> null
        }
    }

    fun validateLogin(email: String, password: String): LoginResult {
        return LoginResult(
            emailError = validateEmail(email),
            passwordError = validateLoginPassword(password)
        )
    }

    fun validateRegistration(
        fullName: String, email: String,
        password: String, confirmation: String
    ): RegistrationResult {
        return RegistrationResult(
            fullNameError = validateFullName(fullName),
            emailError = validateEmail(email),
            passwordError = validateRegistrationPassword(password),
            confirmationError = validatePasswordConfirmation(password, confirmation)
        )
    }
}
