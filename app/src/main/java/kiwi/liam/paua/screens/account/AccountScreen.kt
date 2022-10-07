package kiwi.liam.paua.screens.account

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.rounded.CreditCard
import androidx.compose.material.icons.rounded.Logout
import androidx.compose.material.icons.rounded.ModeOfTravel
import androidx.compose.material.icons.rounded.Report
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import kiwi.liam.paua.BuildConfig
import kiwi.liam.paua.R
import kiwi.liam.paua.ui.components.AccountSettingCard
import kiwi.liam.paua.ui.theme.Dimens
import kiwi.liam.paua.ui.theme.icons
import org.koin.androidx.compose.getViewModel

@FunctionalInterface
interface AccountNavigationDelegate {
    fun toManagedSavedCards()
    fun toFindBusStops()
    fun toDisputeTravel()
}

@Composable
fun AccountScreen(navigation: AccountNavigationDelegate) {
    val viewModel: AccountViewModel = getViewModel()

    Column(
        Modifier
            .fillMaxSize()
            .padding(Dimens.padding8dp)
    ) {
        AccountSettingCard(
            label = "Manage saved card",
            icon = MaterialTheme.icons.CreditCard,
        ) {
            navigation.toManagedSavedCards()
        }
        AccountSettingCard(
            label = "Find bus stops",
            icon = MaterialTheme.icons.ModeOfTravel,
        ) {
            navigation.toFindBusStops()
        }
        AccountSettingCard(
            label = "Dispute travel",
            icon = MaterialTheme.icons.Report,
        ) {
            navigation.toDisputeTravel()
        }

        Spacer(Modifier.weight(1f))

        AccountSettingCard(
            label = stringResource(id = R.string.screen_account_signOut),
            icon = MaterialTheme.icons.Logout,
        ) {
            viewModel.isShowingConfirmAlert = true
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.padding4dp),
        ) {
            Text(
                "Version: ${BuildConfig.VERSION_NAME}",
                textAlign = TextAlign.Center,
            )
        }
    }

    if (viewModel.isShowingConfirmAlert) {
        ConfirmSignOutDialog(
            onConfirm = {
                viewModel.signOut()
                viewModel.isShowingConfirmAlert = false
            },
            onDismiss = { viewModel.isShowingConfirmAlert = false },
        )
    }
}

@Composable
fun ConfirmSignOutDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(stringResource(id = R.string.screen_account_confirmAlert_title)) },
        text = { Text(stringResource(id = R.string.screen_account_confirmAlert_msg)) },
        buttons = {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(Dimens.padding8dp),
                horizontalArrangement = Arrangement.End,
            ) {
                TextButton(onClick = { onDismiss() }) {
                    Text(stringResource(id = R.string.screen_account_confirmAlert_cancel))
                }
                Spacer(modifier = Modifier.padding(Dimens.padding4dp))
                Button(onClick = { onConfirm() }) {
                    Text(stringResource(id = R.string.screen_account_confirmAlert_confirm))
                }
            }
        },
    )
}