package kiwi.liam.paua.screens.account.views.findBusStops

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem
import kiwi.liam.paua.dependencies.managers.TopologyManager
import kiwi.liam.paua.tools.BaseViewModel
import org.koin.core.component.inject

class FindBusStopsViewModel : BaseViewModel() {
    private val topologyManager: TopologyManager by inject()
    val stops: List<ClusterItem> = topologyManager.stops.map {
        object : ClusterItem {
            override fun getPosition(): LatLng {
                return LatLng(it.stopLat, it.stopLon)
            }

            override fun getTitle(): String {
                return it.stopName
            }

            override fun getSnippet(): String {
                return it.stopDesc
            }

        }
    }

}