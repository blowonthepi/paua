package kiwi.liam.paua.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kiwi.liam.paua.ui.theme.Dimens

@Composable
fun TransitCircle(
    icon: ImageVector,
    routeIdentifier: String,
) {
    Box(
        Modifier
            .clip(CircleShape)
            .background(MaterialTheme.colors.primary)
            .padding(Dimens.padding4dp),
    ) {
        Icon(
            icon,
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .padding(Dimens.padding4dp),
            tint = MaterialTheme.colors.onPrimary
        )
    }
    Box(
        Modifier
            .offset(x = (-20).dp)
            .clip(CircleShape)
            .background(MaterialTheme.colors.secondary)
            .padding(Dimens.padding4dp)
            .size(40.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            routeIdentifier,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.onSecondary,
            modifier = Modifier
                .align(Alignment.Center),
        )
    }
}