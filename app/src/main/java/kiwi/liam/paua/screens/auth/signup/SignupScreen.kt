package kiwi.liam.paua.screens.auth.signup

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

interface SignupRouterDelegate {
    fun openLogin()
}

@Composable
fun SignupScreen(
    viewModel: SignupViewModel,
    delegate: SignupRouterDelegate,
) {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            stringResource(id = R.string.screen_signup_title),
            style = MaterialTheme.typography.h3,
        )
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
        Button(onClick = { viewModel.signUp() }) {
            Text(stringResource(id = R.string.screen_signup_title))
        }
        TextButton(onClick = { delegate.openLogin() }) {
            Text(stringResource(id = R.string.screen_signup_loginButton))
        }
    }
}