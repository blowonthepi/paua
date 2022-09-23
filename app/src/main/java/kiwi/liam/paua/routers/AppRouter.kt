package kiwi.liam.paua.routers

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kiwi.liam.paua.screens.splash.SplashScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get

enum class AppRouterScreen { Splash, Auth, Tabs }

class AppRouter {
    var screen by mutableStateOf(AppRouterScreen.Splash)

    init {
        checkAuthStatus()
    }

    private fun checkAuthStatus() {
        CoroutineScope(Dispatchers.Main).launch {
            delay(1000)
            screen = AppRouterScreen.Tabs
        }
    }
}

@Composable
fun AppRouterView() {
    val systemUiController = rememberSystemUiController()
    val router: AppRouter = get()

    if (isSystemInDarkTheme() && router.screen != AppRouterScreen.Splash) {
        systemUiController.setSystemBarsColor(
            color = MaterialTheme.colors.primarySurface
        )
    } else {
        systemUiController.setSystemBarsColor(
            color = MaterialTheme.colors.primaryVariant
        )
    }

    Crossfade(targetState = router.screen) { screen ->
        when (screen) {
            AppRouterScreen.Splash -> {
                SplashScreen()
            }
            AppRouterScreen.Auth -> {

            }
            AppRouterScreen.Tabs -> {
                TabRouterView()
            }
        }
    }
}