package kiwi.liam.paua.screens.onTrip

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.contentColorFor
import androidx.compose.material.icons.rounded.DirectionsBoat
import androidx.compose.material.icons.rounded.DirectionsBus
import androidx.compose.material.icons.rounded.DirectionsRailway
import androidx.compose.material.icons.rounded.DirectionsSubway
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kiwi.liam.paua.R
import kiwi.liam.paua.dependencies.models.TransitType
import kiwi.liam.paua.dependencies.models.Trip
import kiwi.liam.paua.ui.components.TripStopsList
import kiwi.liam.paua.ui.theme.Dimens
import kiwi.liam.paua.ui.theme.icons

@Composable
fun OnTripView(trip: Trip?) {
    Column(
        Modifier
            .padding(Dimens.padding8dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        OnTripIcons(type = trip?.type)
        Text(
            stringResource(id = R.string.screen_onTrip_headline, trip?.route ?: ""),
            style = MaterialTheme.typography.h6,
        )
        Column(
            Modifier
                .verticalScroll(state = rememberScrollState())
                .fillMaxWidth()
        ) {
            TripStopsList(
                stops = trip?.stops ?: emptyList(),
            )
        }
    }
}

@Composable
private fun OnTripIcons(type: TransitType? = TransitType.Train) {
    val icon = when (type) {
        TransitType.Bus -> MaterialTheme.icons.DirectionsBus
        TransitType.Ferry -> MaterialTheme.icons.DirectionsBoat
        TransitType.CableCar -> MaterialTheme.icons.DirectionsSubway
        else -> MaterialTheme.icons.DirectionsRailway
    }

    Box(
        modifier = Modifier
            .padding(Dimens.padding24dp)
            .clip(CircleShape)
            .background(MaterialTheme.colors.primary)
            .padding(Dimens.padding12dp),
    ) {
        Icon(
            icon,
            tint = MaterialTheme.colors.contentColorFor(MaterialTheme.colors.primary),
            contentDescription = "Trip type icon",
            modifier = Modifier
                .size(100.dp)
        )
    }
}