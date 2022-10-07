package kiwi.liam.paua.screens.account.views.findBusStops

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

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
//        viewModel.stops.value.forEach {
//            Circle(
//                center = LatLng(it.stopLat, it.stopLon),
//                fillColor = MaterialTheme.colors.primary,
//                radius = 10.0,
//            )
//        }
    }
}