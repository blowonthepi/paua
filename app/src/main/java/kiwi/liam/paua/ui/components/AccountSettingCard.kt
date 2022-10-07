package kiwi.liam.paua.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.sp
import kiwi.liam.paua.ui.theme.Dimens
import kiwi.liam.paua.ui.theme.icons
import java.util.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AccountSettingCard(
    title: String? = null,
    label: String,
    icon: ImageVector,
    backgroundColor: Color = MaterialTheme.colors.surface,
    onClick: (() -> Unit)? = null,
) {
    Card(
        modifier = Modifier
            .padding(Dimens.padding8dp)
            .fillMaxWidth(),
        elevation = Dimens.padding4dp,
        shape = MaterialTheme.shapes.medium,
        backgroundColor = backgroundColor,
        onClick = { onClick?.invoke() },
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(Dimens.padding12dp)
                .fillMaxWidth(),
        ) {
            Column {
                if (title != null) {
                    Text(
                        title.uppercase(Locale.getDefault()),
                        style = MaterialTheme.typography.overline,
                        modifier = Modifier.padding(Dimens.padding4dp),
                    )
                }
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
            }
            if (onClick != null) {
                Icon(
                    MaterialTheme.icons.ChevronRight,
                    contentDescription = null,
                )
            }
        }
    }
}