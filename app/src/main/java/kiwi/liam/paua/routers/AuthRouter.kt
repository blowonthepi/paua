package kiwi.liam.paua.routers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kiwi.liam.paua.screens.auth.login.LoginRouterDelegate
import kiwi.liam.paua.screens.auth.login.LoginScreen
import kiwi.liam.paua.screens.auth.signup.SignupRouterDelegate
import kiwi.liam.paua.screens.auth.signup.SignupScreen

enum class AuthRouterScreen {
    Login, Signup
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
            LoginScreen(delegate = router.loginDelegate())
        }
        AuthRouterScreen.Signup -> {
            SignupScreen(delegate = router.signupDelegate())
        }
    }
}