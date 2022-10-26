package kiwi.liam.paua.tools

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.*
import org.koin.core.component.KoinComponent

/**
 * A [StateRouter] can be used when you want to preserve
 * the state of the routers views.
 *
 * @sample StateRouterExample
 */
abstract class StateRouter : KoinComponent {
    lateinit var stateHolder: SaveableStateHolder

    @Composable
    fun route(content: @Composable () -> Unit) {
        val key = currentCompositeKeyHash.toString()
        stateHolder.SaveableStateProvider(key) {
            content()
        }
    }
}

@Composable
fun <R : StateRouter> rememberRouterState(init: () -> R): R {
    val router = init()
    val stateHolder = rememberSaveableStateHolder()

    router.apply {
        this.stateHolder = stateHolder
    }

    return router
}

// Sample
object StateRouterExample {
    class Router : StateRouter() {
        @Composable
        fun screenOne() = route { // Using route is key here to remembering the state
            Box {
                // ...
            }
        }
    }

    @Composable
    fun RouterView() {
        val router = rememberRouterState { Router() }

        router.screenOne()
    }
}