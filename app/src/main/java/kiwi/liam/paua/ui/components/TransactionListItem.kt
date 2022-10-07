package kiwi.liam.paua.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kiwi.liam.paua.R
import kiwi.liam.paua.dependencies.models.Transaction
import kiwi.liam.paua.ui.theme.Dimens
import kiwi.liam.paua.ui.theme.icons
import java.util.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TransactionListItem(
    transaction: Transaction,
    isExpanded: Boolean = false,
    showArrow: Boolean = true,
    onClick: (Transaction) -> Unit,
) {
    Card(modifier = Modifier
        .padding(Dimens.padding8dp)
        .fillMaxWidth(),
        elevation = Dimens.padding4dp,
        shape = MaterialTheme.shapes.medium,
        onClick = { onClick(transaction) }) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(Dimens.padding12dp)
                    .fillMaxWidth(),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(9f)
                ) {
                    TransitCircle(
                        icon = transaction.getIcon(), routeIdentifier = transaction.routeId
                    )
                    Column {
                        if (transaction.disputed) {
                            Text(
                                stringResource(id = R.string.screen_account_transactionDisputed).uppercase(Locale.getDefault()),
                                style = MaterialTheme.typography.overline,
                                modifier = Modifier.padding(horizontal = Dimens.padding4dp),
                            )
                        }

                        Text(
                            transaction.routeName,
                            style = MaterialTheme.typography.subtitle2.copy(
                                fontSize = 18.sp,
                            ),
                            modifier = Modifier.padding(Dimens.padding4dp),
                        )
                    }
                }
                if (showArrow) {
                    Icon(
                        if (isExpanded) MaterialTheme.icons.KeyboardArrowUp
                        else MaterialTheme.icons.KeyboardArrowDown,
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                            .weight(1f)
                    )
                }
            }
            AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically() + fadeIn(),
                exit = fadeOut() + shrinkVertically(),
            ) {
                TripStopsList(stops = transaction.stops)
            }
        }
    }
}