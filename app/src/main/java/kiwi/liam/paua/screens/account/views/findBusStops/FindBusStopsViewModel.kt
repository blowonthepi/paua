package kiwi.liam.paua.screens.account.views.findBusStops

import kiwi.liam.paua.dependencies.managers.TopologyManager
import kiwi.liam.paua.tools.BaseViewModel
import org.koin.core.component.inject

class FindBusStopsViewModel : BaseViewModel() {
    private val topologyManager: TopologyManager by inject()
    var stops = topologyManager.stops
}