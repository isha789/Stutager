package cat.ishawaheed.stutager


/**
 * Shared validation engine and result contracts for Login and Registration forms.
 *
 * Provides thread-safe, pure Kotlin validation methods for user credentials without
 * Android framework dependencies, ensuring testability and consistent business rules across screens.
 */
object AuthValidator {
    /** Minimum required length for a full name after trimming. */
    const val MIN_FULL_NAME_LENGTH = 2
    /** Maximum allowed length for a full name. */
    const val MAX_FULL_NAME_LENGTH = 80
    /** Maximum allowed length for an email address according to RFC standards. */
    const val MAX_EMAIL_LENGTH = 254
    /** Minimum required length for passwords. */
    const val MIN_PASSWORD_LENGTH = 8
    /** Maximum allowed length for passwords. */
    const val MAX_PASSWORD_LENGTH = 128
    /**
     * Enumeration of all possible form validation error codes.
     */
    enum class Error {
        /** Field is blank or empty. */
        REQUIRED,
        /** Full name length is below [MIN_FULL_NAME_LENGTH] characters after trimming. */
        FULL_NAME_TOO_SHORT,
        /** Full name length exceeds [MAX_FULL_NAME_LENGTH] characters. */
        FULL_NAME_TOO_LONG,
        /** Email address does not match standard syntax pattern. */
        INVALID_EMAIL,
        /** Email length exceeds [MAX_EMAIL_LENGTH] characters. */
        EMAIL_TOO_LONG,
        /** Password length is below [MIN_PASSWORD_LENGTH] characters. */
        PASSWORD_TOO_SHORT,
        /** Password length exceeds [MAX_PASSWORD_LENGTH] characters. */
        PASSWORD_TOO_LONG,
        /** Password does not contain at least one letter character. */
        PASSWORD_MISSING_LETTER,
        /** Password does not contain at least one digit character. */
        PASSWORD_MISSING_DIGIT,
        /** Confirmation password does not match original password exactly. */
        PASSWORD_MISMATCH
    }
    /**
     * Holds aggregate validation error results for the Login form.
     *
     * @property emailError Validation error for the email field, or `null` if valid.
     * @property passwordError Validation error for the password field, or `null` if valid.
     */
    data class LoginResult(
        val emailError: Error? = null,
        val passwordError: Error? = null
    ) {
        val isValid: Boolean
            get() = emailError == null && passwordError == null
    }
    /**
     * Holds aggregate validation error results for the Registration form.
     *
     * @property fullNameError Validation error for full name, or `null` if valid.
     * @property emailError Validation error for email, or `null` if valid.
     * @property passwordError Validation error for password, or `null` if valid.
     * @property confirmationError Validation error for password confirmation, or `null` if valid.
     */
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

    /** Normalizes a full name input by trimming leading and trailing whitespace. */
    fun normalizeFullName(value: String): String = value.trim()
    /** Normalizes an email input by trimming leading and trailing whitespace. */
    fun normalizeEmail(value: String): String = value.trim()

    /**
     * Validates a full name input string after normalization.
     *
     * Rules checked in order:
     * 1. Blank input -> [Error.REQUIRED]
     * 2. Length < [MIN_FULL_NAME_LENGTH] -> [Error.FULL_NAME_TOO_SHORT]
     * 3. Length > [MAX_FULL_NAME_LENGTH] -> [Error.FULL_NAME_TOO_LONG]
     *
     * @param value The full name string to validate.
     * @return The first applicable [Error], or `null` if valid.
     */fun validateFullName(value: String): Error? {
        val normalized = normalizeFullName(value)
        return when {
            normalized.isBlank() -> Error.REQUIRED
            normalized.length < MIN_FULL_NAME_LENGTH -> Error.FULL_NAME_TOO_SHORT
            normalized.length > MAX_FULL_NAME_LENGTH -> Error.FULL_NAME_TOO_LONG
            else -> null
        }
    }

    /**
     * Validates an email address input string after normalization.
     *
     * Rules checked in order:
     * 1. Blank input -> [Error.REQUIRED]
     * 2. Length > [MAX_EMAIL_LENGTH] -> [Error.EMAIL_TOO_LONG]
     * 3. Regex syntax mismatch -> [Error.INVALID_EMAIL]
     *
     * @param value The email string to validate.
     * @return The first applicable [Error], or `null` if valid.
     */fun validateEmail(value: String): Error? {
        val normalized = normalizeEmail(value)
        return when {
            normalized.isBlank() -> Error.REQUIRED
            normalized.length > MAX_EMAIL_LENGTH -> Error.EMAIL_TOO_LONG
            !emailPattern.matches(normalized) -> Error.INVALID_EMAIL
            else -> null
        }
    }

    /** Delegates to [validateRegistrationPassword] to enforce identical password policies. */
    fun validateLoginPassword(value: String): Error? = validateRegistrationPassword(value)

    /**
     * Validates a password input string without trimming whitespace.
     *
     * Rules checked in order:
     * 1. Blank input -> [Error.REQUIRED]
     * 2. Length < [MIN_PASSWORD_LENGTH] -> [Error.PASSWORD_TOO_SHORT]
     * 3. Length > [MAX_PASSWORD_LENGTH] -> [Error.PASSWORD_TOO_LONG]
     * 4. No letter character -> [Error.PASSWORD_MISSING_LETTER]
     * 5. No digit character -> [Error.PASSWORD_MISSING_DIGIT]
     *
     * @param value The raw password string to validate.
     * @return The first applicable [Error], or `null` if valid.
     */fun validateRegistrationPassword(value: String): Error? {
        return when {
            value.isBlank() -> Error.REQUIRED
            value.length < MIN_PASSWORD_LENGTH -> Error.PASSWORD_TOO_SHORT
            value.length > MAX_PASSWORD_LENGTH -> Error.PASSWORD_TOO_LONG
            value.none { it.isLetter() } -> Error.PASSWORD_MISSING_LETTER
            value.none { it.isDigit() } -> Error.PASSWORD_MISSING_DIGIT
            else -> null
        }
    }

    /**
     * Validates password confirmation matching.
     *
     * Rules checked in order:
     * 1. Blank confirmation -> [Error.REQUIRED]
     * 2. Exact case-sensitive inequality -> [Error.PASSWORD_MISMATCH]
     *
     * @param password The raw original password.
     * @param confirmation The raw confirmation password to check.
     * @return The applicable [Error], or `null` if passwords match.
     */fun validatePasswordConfirmation(password: String, confirmation: String): Error? {
        return when {
            confirmation.isBlank() -> Error.REQUIRED
            password != confirmation -> Error.PASSWORD_MISMATCH
            else -> null
        }
    }

    /**
     * Evaluates aggregate Login validation for all input fields.
     *
     * @param email Raw email input string.
     * @param password Raw password input string.
     * @return A [LoginResult] containing validation status for each field.
     */fun validateLogin(email: String, password: String): LoginResult {
        return LoginResult(
            emailError = validateEmail(email),
            passwordError = validateLoginPassword(password)
        )
    }

    /**
     * Evaluates aggregate Registration validation for all input fields.
     *
     * @param fullName Raw full name input string.
     * @param email Raw email input string.
     * @param password Raw password input string.
     * @param confirmation Raw password confirmation input string.
     * @return A [RegistrationResult] containing validation status for each field.
     */fun validateRegistration(
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
