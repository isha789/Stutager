package cat.ishawaheed.stutager

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/** Valida el formulari i exposa el resultat a la pantalla de registre. */
class RegisterViewModel : ViewModel() {
    private val _uiState = MutableLiveData(RegisterUiState())

    /** Estat observable que la pantalla pot consultar i que només canvia el ViewModel. */
    val uiState: LiveData<RegisterUiState> = _uiState

    /** Valida tots els camps i publica un estat nou amb els errors actuals. */
    fun submit(fullName: String, email: String, password: String, confirmation: String) {
        val result = AuthValidator.validateRegistration(fullName, email, password, confirmation)
        _uiState.value = RegisterUiState(
            fullNameError = result.fullNameError,
            emailError = result.emailError,
            passwordError = result.passwordError,
            confirmationError = result.confirmationError,
            submissionAccepted = result.isValid
        )
    }

    /** Restableix l'acceptació abans de tancar el registre per evitar repetir la navegació. */
    fun onSubmissionHandled() {
        val state = _uiState.value ?: return
        _uiState.value = state.copy(submissionAccepted = false)
    }
}
