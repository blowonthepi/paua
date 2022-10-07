package kiwi.liam.paua.screens.wallet.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import kiwi.liam.paua.R
import kiwi.liam.paua.dependencies.models.Transaction
import kiwi.liam.paua.ui.components.TransactionListItem
import kiwi.liam.paua.ui.theme.Dimens

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

        if (transactions.isEmpty()) {
            Text(
                stringResource(id = R.string.screen_wallet_emptyTransactions)
            )
        } else {
            LazyColumn(Modifier.fillMaxHeight()) {
                items(transactions) { transaction ->
                    TransactionListItem(
                        transaction,
                        isExpanded = expandedTransaction == transaction,
                        onClick = { onClick(transaction) },
                    )
                }
            }
        }
    }
}