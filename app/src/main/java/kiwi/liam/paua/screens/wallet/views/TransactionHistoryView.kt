package kiwi.liam.paua.screens.wallet.views

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import kiwi.liam.paua.dependencies.models.Transaction
import kiwi.liam.paua.ui.components.TransitCircle
import kiwi.liam.paua.ui.components.TripStopsList
import kiwi.liam.paua.ui.theme.Dimens
import kiwi.liam.paua.ui.theme.icons

@Composable
fun TransactionHistoryView(
    transactions: List<Transaction>,
    expandedTransaction: Transaction?,
    onClick: (Transaction) -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Divider(Modifier.padding(Dimens.padding8dp))

        Text(
            "Transaction History",
            modifier = Modifier.padding(Dimens.padding8dp),
            style = MaterialTheme.typography.h6,
        )
        LazyColumn(Modifier.fillMaxHeight()) {
            items(transactions) { transaction ->
                TransactionItem(
                    transaction,
                    isExpanded = expandedTransaction == transaction,
                    onClick = { onClick(transaction) },
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun TransactionItem(
    transaction: Transaction,
    isExpanded: Boolean,
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
                Row(verticalAlignment = Alignment.CenterVertically) {
                    TransitCircle(
                        icon = transaction.getIcon(), routeIdentifier = transaction.routeId
                    )
                    Text(
                        transaction.routeName,
                        style = MaterialTheme.typography.subtitle2.copy(
                            fontSize = 18.sp,
                        ),
                        modifier = Modifier.padding(Dimens.padding4dp),
                    )
                }
                Icon(
                    if (isExpanded) MaterialTheme.icons.KeyboardArrowUp
                    else MaterialTheme.icons.KeyboardArrowDown,
                    contentDescription = null,
                )
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