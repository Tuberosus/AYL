package com.tuberosus.ayl.feature.admin

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuberosus.ayl.domain.repository.AuthRepository
import com.tuberosus.ayl.domain.util.onFailure
import com.tuberosus.ayl.domain.util.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(AuthState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                observeValidationStates()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000L),
            AuthState()
        )

    init {
        setLoggedInState(
            authRepository.getCurrentUser() != null
        )
    }

    fun onAction(action: AuthAction) {
        when (action) {
            is AuthAction.OnEmailChange -> changeEmail(action.value)
            is AuthAction.OnPasswordChange -> changePassword(action.value)
            AuthAction.OnPasswordVisibleClick -> changePasswordVisible()
            AuthAction.OnLogInClick -> signIn()
            AuthAction.OnSingOut -> signOut()
        }
    }

    private fun observeValidationStates() {
        _state
            .map {
                Patterns.EMAIL_ADDRESS.matcher(it.email).matches()
                        && it.password.isNotEmpty()
                        && !it.isLoggingIn
            }
            .distinctUntilChanged()
            .onEach { canLogIn ->
                _state.update { it.copy(canLogIn = canLogIn) }
            }
            .launchIn(viewModelScope)
    }

    fun signOut() {
        viewModelScope.launch {
            authRepository.signOut()
        }
        setLoggedInState(false)
    }

    private fun signIn() {
        val state = _state.value

        if (!state.canLogIn) return

        viewModelScope.launch {
            _state.update {
                it.copy(isLoggingIn = true)
            }

            authRepository.signIn(
                email = state.email,
                password = state.password
            )
                .onSuccess {
                    setLoggedInState(true)
                }
                .onFailure {
                    setLoggedInState(false)
                }

            _state.update {
                it.copy(isLoggingIn = false)
            }
        }
    }

    private fun changeEmail(value: String) {
        val preparedEmail = value
            .trim()
            .lowercase()

        _state.update {
            it.copy(
                email = preparedEmail
            )
        }
    }

    private fun changePassword(value: String) {
        _state.update {
            it.copy(
                password = value
            )
        }
    }

    private fun changePasswordVisible() {
        _state.update {
            it.copy(
                isPasswordVisible = !it.isPasswordVisible
            )
        }
    }

    private fun setLoggedInState(value: Boolean) {
        _state.update {
            it.copy(isLoggedIn = value)
        }
    }
}