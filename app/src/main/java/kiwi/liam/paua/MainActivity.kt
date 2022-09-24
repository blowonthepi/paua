package kiwi.liam.paua

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kiwi.liam.paua.dependencies.appRouters
import kiwi.liam.paua.dependencies.appServices
import kiwi.liam.paua.dependencies.serviceStates
import kiwi.liam.paua.dependencies.services.TripDetectionService
import kiwi.liam.paua.dependencies.viewModels
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
                appRouters,
                viewModels,
                module { applicationContext },
            )
        }

        // Start detecting trips
        val tripDetectionService: TripDetectionService by inject()
//        tripDetectionService.startService()

        setContent {
            PauaTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    AppRouterView()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopKoin()
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    startKoin { modules(appServices) }
    PauaTheme {
        AppRouterView()
    }
}