package kiwi.liam.paua.routers

import androidx.compose.animation.*
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kiwi.liam.paua.dependencies.services.TripDetectionState
import kiwi.liam.paua.screens.onTrip.OnTripView
import kiwi.liam.paua.screens.splash.SplashScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

enum class AppRouterScreen { Splash, Auth, Tabs }

class AppRouter : KoinComponent {
    private val tripDetectionState: TripDetectionState by inject()

    var screen by mutableStateOf(AppRouterScreen.Splash)
    var showOnTripOverlay by mutableStateOf(false)
    var currentTrip = tripDetectionState.currentTrip

    init {
        checkAuthStatus()
        CoroutineScope(Dispatchers.Main).launch {
            tripDetectionState.isOnTrip.collectLatest {
                showOnTripOverlay = it
            }
        }
    }

    private fun checkAuthStatus() {
        CoroutineScope(Dispatchers.Main).launch {
            delay(1000)
            screen = AppRouterScreen.Tabs
        }
    }
}

@Composable
fun AppRouterView(router: AppRouter) {
    val systemUiController = rememberSystemUiController()

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
                TabRouterView(router = TabRouter())
            }
        }
    }

    AnimatedVisibility(
        visible = router.showOnTripOverlay,
        enter = slideInVertically { it } + fadeIn(),
        exit = fadeOut() + slideOutVertically { it },
    ) {
        Surface(Modifier.fillMaxSize()) {
            OnTripView(router.currentTrip.value)
        }
    }
}