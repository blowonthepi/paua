package kiwi.liam.paua.dependencies.models

import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.rounded.DirectionsBoat
import androidx.compose.material.icons.rounded.DirectionsBus
import androidx.compose.material.icons.rounded.DirectionsRailway
import androidx.compose.material.icons.rounded.DirectionsSubway
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import kiwi.liam.paua.ui.theme.icons

data class Transaction(
    val type: TransitType,
    val routeId: String,
    val routeName: String,
    val stops: List<String>
) {
    @Composable
    fun getIcon(): ImageVector {
        return when (type) {
            TransitType.Bus -> MaterialTheme.icons.DirectionsBus
            TransitType.Train -> MaterialTheme.icons.DirectionsRailway
            TransitType.Ferry -> MaterialTheme.icons.DirectionsBoat
            TransitType.CableCar -> MaterialTheme.icons.DirectionsSubway
        }
    }
}