package cat.ishawaheed.stutager

/**
 * Immutable UI State for the Login screen.
 *
 * Holds validation errors for email and password fields, and a flag indicating
 * whether the submission was accepted.
 */
data class LoginUiState(
    val emailError: AuthValidator.Error? = null,
    val passwordError: AuthValidator.Error? = null,
    val submissionAccepted: Boolean = false
)
