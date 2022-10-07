package kiwi.liam.paua.screens.wallet

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import kiwi.liam.paua.dependencies.models.Transaction
import kiwi.liam.paua.screens.wallet.views.TransactionHistoryView
import kiwi.liam.paua.ui.components.TopBar
import kiwi.liam.paua.ui.theme.Dimens
import kiwi.liam.paua.ui.theme.Typography
import kiwi.liam.paua.ui.theme.icons
import org.koin.androidx.compose.getViewModel

@Composable
fun WalletScreen() {
    val viewModel: WalletViewModel = getViewModel()

    Scaffold(
        topBar = { TopBar() },
    ) {
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
                        icon = {
                            Icon(
                                MaterialTheme.icons.Add,
                                contentDescription = "Top up plus icon",
                            )
                        },
                        onClick = { viewModel.topUp() },
                    )
                }
            }
            TransactionList()
        }
    }
}

@Composable
private fun TransactionList() {
    val viewModel: WalletViewModel = getViewModel()
    var expandedTransaction by remember { mutableStateOf<Transaction?>(null) }

    TransactionHistoryView(
        transactions = viewModel.transactions,
        expandedTransaction = expandedTransaction
    ) {
        expandedTransaction = when (expandedTransaction) {
            it -> null
            else -> it
        }
    }
}