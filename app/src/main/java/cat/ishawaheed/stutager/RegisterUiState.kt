package cat.ishawaheed.stutager

/** Estat immutable del registre amb els errors de cada camp i el resultat de l'enviament. */
data class RegisterUiState(
    val fullNameError: AuthValidator.Error? = null,
    val emailError: AuthValidator.Error? = null,
    val passwordError: AuthValidator.Error? = null,
    val confirmationError: AuthValidator.Error? = null,
    val submissionAccepted: Boolean = false
)
