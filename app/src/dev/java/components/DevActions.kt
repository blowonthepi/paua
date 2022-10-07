package components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.rounded.PlayCircle
import androidx.compose.material.icons.rounded.StopCircle
import androidx.compose.runtime.Composable
import kiwi.liam.paua.dependencies.services.MockTripDetectionService
import kiwi.liam.paua.dependencies.services.TripDetectionService
import kiwi.liam.paua.ui.theme.icons
import org.koin.androidx.compose.get

@Composable
fun RowScope.DevActions() {
    val tripDetectionService: TripDetectionService = get()

    if (tripDetectionService is MockTripDetectionService) {
        IconButton(
            onClick = {
                tripDetectionService.toggleService()
            },
        ) {
            Icon(
                if (tripDetectionService.isServiceRunning) MaterialTheme.icons.StopCircle
                else MaterialTheme.icons.PlayCircle,
                contentDescription = null,
            )
        }
    }
}