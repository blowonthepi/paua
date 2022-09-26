package kiwi.liam.paua

import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import kiwi.liam.paua.dependencies.appServices
import kiwi.liam.paua.dependencies.serviceStates
import kiwi.liam.paua.dependencies.services.TripDetectionService
import kiwi.liam.paua.dependencies.viewModels
import kiwi.liam.paua.routers.AppRouter
import kiwi.liam.paua.routers.AppRouterView
import kiwi.liam.paua.ui.theme.PauaTheme
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module


class MainActivity : ComponentActivity(), KoinComponent {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startKoin {
            modules(
                serviceStates,
                appServices,
                viewModels,
                module { applicationContext },
            )
        }

        WindowCompat.setDecorFitsSystemWindows(window, false)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }


        // Start detecting trips
        val tripDetectionService: TripDetectionService by inject()
//        tripDetectionService.startService()

        setContent {
            PauaTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    AppRouterView(router = AppRouter())
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopKoin()
    }
}