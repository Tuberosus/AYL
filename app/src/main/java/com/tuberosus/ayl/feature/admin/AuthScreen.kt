package com.tuberosus.ayl.feature.admin

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tuberosus.ayl.R
import com.tuberosus.ayl.ui.components.layouts.AdminLayout
import com.tuberosus.ayl.ui.util.ObserveAsEvents

@Composable
fun AuthScreenRoot(
    viewModel: AuthViewModel,
    onDismiss: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val context = LocalContext.current

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is AuthEvent.InfoMessage -> {
                Toast.makeText(
                    context,
                    event.message,
                    Toast.LENGTH_SHORT
                ).show()
            }

            is AuthEvent.ExitForm -> {
                onDismiss()
            }
        }
    }

    AuthScreen(
        state = state,
        onAction = viewModel::onAction,
        onDismiss = onDismiss
    )
}

@Composable
private fun AuthScreen(
    state: AuthState,
    onAction: (AuthAction) -> Unit,
    onDismiss: () -> Unit,
) {
    AdminLayout(
        canAction = state.canLogIn,
        isAction = state.isLoggingIn,
        onAction = {
            onAction(AuthAction.OnLogInClick)
        },
        actionTitle = "Войти",
        onDismiss = onDismiss
    ) {
        Text(
            text = "Вход для админа",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.email,
            onValueChange = { onAction(AuthAction.OnEmailChange(it)) },
            placeholder = { Text("Email") },
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_mail),
                    contentDescription = null
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            singleLine = true,
            shape = RoundedCornerShape(14.dp)
        )
        Spacer(modifier = Modifier.height(18.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.password,
            onValueChange = { onAction(AuthAction.OnPasswordChange(it)) },
            placeholder = { Text("Пароль") },
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_key_vertical),
                    contentDescription = null
                )
            },
            trailingIcon = {
                Icon(
                    modifier = Modifier
                        .clickable(
                            onClick = {
                                onAction(AuthAction.OnPasswordVisibleClick)
                            }
                        ),
                    painter = if (state.isPasswordVisible) {
                        painterResource(R.drawable.ic_visibility_off)
                    } else {
                        painterResource(R.drawable.ic_visibility)
                    },
                    contentDescription = null
                )
            },
            visualTransformation = if (state.isPasswordVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            singleLine = true,
            shape = RoundedCornerShape(14.dp)
        )
    }
}