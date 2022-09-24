package kiwi.liam.paua.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import kiwi.liam.paua.ui.theme.Dimens

private enum class StopPosition {
    Start,
    Mid,
    End,
}

@Composable
fun TripStopsList(stops: List<String>) {
    Row(
        Modifier.padding(
            vertical = Dimens.padding8dp,
            horizontal = Dimens.padding24dp
        ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column {
            // Iterate using indexes to correctly style start/mid/end stops
            stops.withIndex().forEach { indexedValue ->
                val position = when (indexedValue.index) {
                    0 -> StopPosition.Start
                    stops.size - 1 -> StopPosition.End
                    else -> StopPosition.Mid
                }
                StopCircle(position = position)
            }
        }
        Column {
            stops.forEach { stop ->
                Text(
                    stop,
                    modifier = Modifier.padding(Dimens.padding12dp),
                    style = MaterialTheme.typography.h6,
                )
            }
        }
    }
}

@Composable
private fun StopCircle(position: StopPosition) {
    val alignment = when (position) {
        StopPosition.Start -> Alignment.BottomCenter
        StopPosition.Mid -> Alignment.Center
        StopPosition.End -> Alignment.TopCenter
    }

    val heightFraction = when (position) {
        StopPosition.Mid -> 1f
        else -> 0.5f
    }

    Box(
        Modifier
            .height(Dimens.padding24dp.times(2))
    ) {
        Box(
            Modifier
                .clip(RectangleShape)
                .width(Dimens.padding4dp)
                .fillMaxHeight(fraction = heightFraction)
                .align(alignment)
                .background(MaterialTheme.colors.primaryVariant)
        )
        Box(
            Modifier
                .clip(CircleShape)
                .size(Dimens.padding24dp)
                .background(MaterialTheme.colors.primary)
                .align(Alignment.Center)
        )
        Box(
            Modifier
                .clip(CircleShape)
                .size(Dimens.padding12dp)
                .background(MaterialTheme.colors.primaryVariant)
                .align(Alignment.Center)
        )
    }
}