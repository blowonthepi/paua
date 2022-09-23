package kiwi.liam.paua.screens.wallet

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import kiwi.liam.paua.dependencies.models.Transaction
import kiwi.liam.paua.ui.components.TransitCircle
import kiwi.liam.paua.ui.theme.Dimens
import kiwi.liam.paua.ui.theme.Typography
import kiwi.liam.paua.ui.theme.icons
import org.koin.androidx.compose.getViewModel

@Composable
fun WalletScreen() {
    val viewModel: WalletViewModel = getViewModel()

    Column {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(Dimens.padding8dp), horizontalArrangement = Arrangement.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    viewModel.getAccountBalance(),
                    style = Typography.h3,
                    fontWeight = FontWeight.Bold,
                )
                ExtendedFloatingActionButton(
                    text = { Text("Top up") },
                    icon = { Icon(MaterialTheme.icons.Add, contentDescription = "Top up plus icon") },
                    onClick = { /*TODO*/ },
                )
            }
        }
        TransactionList()
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun TransactionList() {
    val viewModel: WalletViewModel = getViewModel()

    @Composable
    fun TransactionItem(transaction: Transaction) {
        var isExpanded by remember { mutableStateOf(false) }
        Card(
            modifier = Modifier
                .padding(Dimens.padding8dp)
                .fillMaxWidth(),
            elevation = Dimens.padding4dp,
            shape = MaterialTheme.shapes.medium,
            onClick = { isExpanded = !isExpanded }
        ) {
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
                            icon = viewModel.getTransitIcon(transaction.type), routeIdentifier = transaction.routeId
                        )
                        Text(
                            transaction.routeName, style = MaterialTheme.typography.subtitle2.copy(
                                fontSize = 18.sp,
                            ), modifier = Modifier.padding(Dimens.padding4dp)
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
                    Column(
                        modifier = Modifier.padding(Dimens.padding8dp),
                    ) {
                        transaction.stops.forEach { stop ->
                            val stopName = when (transaction.stops.indexOf(stop)) {
                                0 -> "Start: $stop"
                                transaction.stops.size - 1 -> "End: $stop"
                                else -> stop
                            }
                            Text(stopName)
                        }
                    }
                }
            }
        }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Divider(Modifier.padding(Dimens.padding8dp))

        Text(
            "Transaction History",
            modifier = Modifier.padding(Dimens.padding8dp),
            style = MaterialTheme.typography.h6,
        )
        LazyColumn(Modifier.fillMaxHeight()) {
            items(viewModel.transactions) { transaction ->
                TransactionItem(transaction)
            }
        }
    }
}