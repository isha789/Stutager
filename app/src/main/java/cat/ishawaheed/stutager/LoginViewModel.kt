package cat.ishawaheed.stutager

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * ViewModel for managing Login form validation state.
 *
 * Exposes an immutable LiveData stream of [LoginUiState] and keeps passwords secure
 * without writing them to persistent storage or logs.
 */
class LoginViewModel : ViewModel() {

    private val _uiState = MutableLiveData<LoginUiState>()
    val uiState: LiveData<LoginUiState> get() = _uiState

    /**
     * Submits email and password for validation.
     * Updates [uiState] with any field errors and sets submissionAccepted to true if valid.
     */
    fun submit(email: String, password: String) {
        val result = AuthValidator.validateLogin(email, password)
        _uiState.value = LoginUiState(
            emailError = result.emailError,
            passwordError = result.passwordError,
            submissionAccepted = result.isValid
        )
    }

    /**
     * Resets the submissionAccepted flag to false after navigation is handled.
     * Prevents repeated navigation when activity observation restarts on screen rotation.
     */
    fun onSubmissionHandled() {
        _uiState.value = _uiState.value?.copy(submissionAccepted = false)
    }
}
