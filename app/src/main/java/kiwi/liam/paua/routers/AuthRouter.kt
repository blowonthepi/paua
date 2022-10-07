package kiwi.liam.paua.routers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kiwi.liam.paua.screens.auth.login.LoginRouterDelegate
import kiwi.liam.paua.screens.auth.login.LoginScreen
import kiwi.liam.paua.screens.auth.login.LoginViewModel
import kiwi.liam.paua.screens.auth.signup.SignupRouterDelegate
import kiwi.liam.paua.screens.auth.signup.SignupScreen
import kiwi.liam.paua.screens.auth.signup.SignupViewModel

enum class AuthRouterScreen {
    Login,
    Signup
}

class AuthRouter {
    var screen by mutableStateOf(AuthRouterScreen.Login)
}

fun AuthRouter.loginDelegate() = object : LoginRouterDelegate {
    override fun openSignup() {
        this@loginDelegate.screen = AuthRouterScreen.Signup
    }
}

fun AuthRouter.signupDelegate() = object : SignupRouterDelegate {
    override fun openLogin() {
        this@signupDelegate.screen = AuthRouterScreen.Login
    }
}

@Composable
fun AuthRouterView(router: AuthRouter) {
    when (router.screen) {
        AuthRouterScreen.Login -> {
            LoginScreen(
                viewModel = LoginViewModel(),
                delegate = router.loginDelegate(),
            )
        }
        AuthRouterScreen.Signup -> {
            SignupScreen(
                viewModel = SignupViewModel(),
                delegate = router.signupDelegate(),
            )
        }
    }
}