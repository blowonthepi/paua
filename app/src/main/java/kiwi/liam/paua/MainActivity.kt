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
import com.google.firebase.FirebaseApp
import kiwi.liam.paua.dependencies.allKoinModules
import kiwi.liam.paua.dependencies.managers.AuthManager
import kiwi.liam.paua.routers.AppRouter
import kiwi.liam.paua.routers.AppRouterView
import kiwi.liam.paua.ui.theme.PauaTheme
import org.koin.android.ext.koin.androidContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin


class MainActivity : ComponentActivity(), KoinComponent {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }

        FirebaseApp.initializeApp(this)

        startKoin {
            modules(allKoinModules)
            androidContext(this@MainActivity)
        }

        // Start Auth Listener
        val authManager: AuthManager by inject()
        authManager.listenToAuthStatus()

        requestPermissions(arrayOf(android.Manifest.permission.BLUETOOTH_SCAN), 1)

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