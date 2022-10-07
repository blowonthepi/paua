package kiwi.liam.paua.screens.account

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.rounded.CreditCard
import androidx.compose.material.icons.rounded.ModeOfTravel
import androidx.compose.material.icons.rounded.Report
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
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

    Column(Modifier.fillMaxSize()) {
        Box(
            Modifier
                .padding(Dimens.padding8dp)
                .clip(CircleShape)
                .background(MaterialTheme.colors.primary)
                .size(150.dp)
                .align(Alignment.CenterHorizontally),
        )
        Text(
            stringResource(R.string.screen_account_namePlaceholder),
            style = MaterialTheme.typography.h4,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.weight(1f))
        Column(
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier.padding(Dimens.padding8dp),
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
}