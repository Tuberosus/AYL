package com.tuberosus.ayl.feature.admin

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuberosus.ayl.domain.repository.AuthRepository
import com.tuberosus.ayl.domain.util.onFailure
import com.tuberosus.ayl.domain.util.onSuccess
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    var isLoggedIn by mutableStateOf(false)
        private set

    init {
        isLoggedIn = authRepository.getCurrentUser() != null
    }

    fun signOut() {
        viewModelScope.launch {
            authRepository.signOut()
        }
        isLoggedIn = false
    }

    fun signIn(
        email: String,
        password: String,
    ) {
        viewModelScope.launch {
            authRepository.signIn(
                email = email,
                password = password
            )
                .onSuccess {
                    isLoggedIn = true
                }
                .onFailure {
                    isLoggedIn = false
                }
        }
    }
}