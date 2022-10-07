package kiwi.liam.paua.dependencies.managers

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import kiwi.liam.paua.dependencies.models.Route
import kiwi.liam.paua.dependencies.models.Stop
import kiwi.liam.paua.dependencies.services.PauaAPIService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

interface TopologyManager {
    var stops: SnapshotStateList<Stop>
    var routes: SnapshotStateList<Route>
}

class AppTopologyManager(private val apiService: PauaAPIService) : TopologyManager {
    override var stops = mutableStateListOf<Stop>()
    override var routes = mutableStateListOf<Route>()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            stops.addAll(apiService.api.getStops())
        }
        CoroutineScope(Dispatchers.IO).launch {
            routes.addAll(apiService.api.getRoutes())
        }
    }
}

class MockTopologyManager : TopologyManager {
    override var stops = mutableStateListOf<Stop>()
    override var routes = mutableStateListOf<Route>()
}