package kiwi.liam.paua.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.sp
import kiwi.liam.paua.ui.theme.Dimens
import kiwi.liam.paua.ui.theme.icons

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AccountSettingCard(
    label: String,
    icon: ImageVector,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .padding(Dimens.padding8dp)
            .fillMaxWidth(),
        elevation = Dimens.padding4dp,
        shape = MaterialTheme.shapes.medium,
        onClick = onClick,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(Dimens.padding12dp)
                .fillMaxWidth(),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(icon, contentDescription = null)

                Text(
                    label,
                    style = MaterialTheme.typography.subtitle2.copy(
                        fontSize = 18.sp,
                    ),
                    modifier = Modifier.padding(Dimens.padding4dp),
                )
            }
            Icon(
                MaterialTheme.icons.ChevronRight,
                contentDescription = null,
            )
        }
    }
}