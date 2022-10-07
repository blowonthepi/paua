package kiwi.liam.paua.screens.account.views.disputeTravel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import kiwi.liam.paua.R
import kiwi.liam.paua.ui.components.TransactionListItem
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@Composable
fun DisputeTravelView() {
    val viewModel: DisputeTravelViewModel = getViewModel()

    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val snackbarMsg = stringResource(id = R.string.screen_account_disputeTravel_snackbarMsg)

    Scaffold(scaffoldState = scaffoldState) {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                stringResource(id = R.string.screen_account_disputeTravel_title),
                style = MaterialTheme.typography.h4,
            )

            LazyColumn(Modifier.fillMaxHeight()) {
                items(viewModel.transactions) { transaction ->
                    TransactionListItem(
                        transaction,
                        showArrow = false,
                        onClick = {
                            viewModel.dispute(transaction)
                            coroutineScope.launch {
                                scaffoldState.snackbarHostState.showSnackbar(
                                    message = snackbarMsg,
                                    actionLabel = null,
                                )
                            }
                        },
                    )
                }
            }
        }
    }
}