package components

import androidx.compose.material.*
import androidx.compose.material.icons.rounded.PlayCircle
import androidx.compose.material.icons.rounded.StopCircle
import androidx.compose.runtime.*
import kiwi.liam.paua.dependencies.services.MockTripDetectionService
import kiwi.liam.paua.dependencies.services.TripDetectionService
import kiwi.liam.paua.ui.theme.icons
import org.koin.androidx.compose.get

@Composable
fun DevActions() {
    val tripDetectionService: TripDetectionService = get()

    var menuIsExpanded by remember { mutableStateOf(false) }

    if (tripDetectionService is MockTripDetectionService) {
        IconButton(
            onClick = {
                menuIsExpanded = true
            },
        ) {
            Icon(
                if (tripDetectionService.isServiceRunning) MaterialTheme.icons.StopCircle
                else MaterialTheme.icons.PlayCircle,
                contentDescription = null,
            )
        }

        DropdownMenu(
            expanded = menuIsExpanded,
            onDismissRequest = { menuIsExpanded = false },
        ) {
            tripDetectionService.availableTrips.forEach { trip ->
                DropdownMenuItem(
                    onClick = {
                        menuIsExpanded = false
                        tripDetectionService.selectedTrip = trip
                        tripDetectionService.startService()
                    }
                ) {
                    Text(trip.route)
                }
            }
        }
    }
}