package kiwi.liam.paua.screens.auth.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import kiwi.liam.paua.R
import kiwi.liam.paua.ui.theme.Dimens

interface LoginRouterDelegate {
    fun openSignup()
}

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    delegate: LoginRouterDelegate,
) {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            stringResource(id = R.string.screen_login_title),
            style = MaterialTheme.typography.h3,
        )
        viewModel.errorMsg?.let {
            Text(it, color = MaterialTheme.colors.error)
        }
        TextField(
            value = viewModel.email,
            onValueChange = {
                viewModel.email = it
            },
            label = { Text(stringResource(id = R.string.screen_auth_emailLabel)) },
            modifier = Modifier.padding(Dimens.padding8dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        TextField(
            value = viewModel.password,
            onValueChange = {
                viewModel.password = it
            },
            label = { Text(stringResource(id = R.string.screen_auth_passwordLabel)) },
            modifier = Modifier.padding(Dimens.padding8dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = PasswordVisualTransformation(),
        )
        Button(onClick = { viewModel.signIn() }) {
            Text(stringResource(id = R.string.screen_login_title))
        }
        TextButton(onClick = { delegate.openSignup() }) {
            Text(stringResource(id = R.string.screen_login_signUpButton))
        }
    }
}