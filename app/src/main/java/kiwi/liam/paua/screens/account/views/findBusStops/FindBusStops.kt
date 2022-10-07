package kiwi.liam.paua.screens.account.views.findBusStops

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.compose.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(MapsComposeExperimentalApi::class)
@Composable
fun FindBusStops(viewModel: FindBusStopsViewModel) {
    val wellington = LatLng(-41.279298, 174.780275)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(wellington, 10f)
    }

    val uiSettings by remember { mutableStateOf(MapUiSettings()) }
    val properties by remember {
        mutableStateOf(MapProperties(mapType = MapType.NORMAL))
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        uiSettings = uiSettings,
        properties = properties,
    ) {
        val context = LocalContext.current
        var clusterManager by remember { mutableStateOf<ClusterManager<ClusterItem>?>(null) }
        MapEffect(viewModel.stops) { map ->
            clusterManager = ClusterManager<ClusterItem>(context, map)
            clusterManager?.addItems(viewModel.stops)
        }
        LaunchedEffect(key1 = cameraPositionState.isMoving) {
            clusterManager?.onCameraIdle()
            clusterManager?.setOnClusterClickListener {
                CoroutineScope(Dispatchers.Main).launch {
                    val newZoom = cameraPositionState.position.zoom * 2
                    cameraPositionState.animate(
                        update = CameraUpdateFactory.newLatLngZoom(
                            it.position,
                            newZoom,
                        ),
                        durationMs = 300,
                    )
                }
                return@setOnClusterClickListener true
            }
        }
    }
}