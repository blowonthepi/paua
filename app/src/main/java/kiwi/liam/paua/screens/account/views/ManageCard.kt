package kiwi.liam.paua.screens.account.views

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.rounded.AddCard
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import kiwi.liam.paua.R
import kiwi.liam.paua.dependencies.models.Card
import kiwi.liam.paua.ui.theme.Dimens
import kiwi.liam.paua.ui.theme.icons

@Composable
fun ManageCard(card: Card?, updateCard: (Card) -> Unit) {
    var isShowingUpdateDialog by remember { mutableStateOf(false) }

    var cardNumber by remember { mutableStateOf("") }
    var cardCVV by remember { mutableStateOf("") }

    val updateCardLabel = stringResource(id = R.string.screen_account_savedCard_updateCard)

    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            stringResource(id = R.string.screen_account_manageCard_title),
            style = MaterialTheme.typography.h4,
        )

        Spacer(modifier = Modifier.weight(1f))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Card(Modifier.padding(Dimens.padding8dp)) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(fraction = 0.3f)
                        .padding(Dimens.padding8dp)
                        .align(Alignment.End),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.End,
                ) {
                    Text(
                        "${card?.number}",
                        style = MaterialTheme.typography.h6.copy(
                            fontFamily = FontFamily.Monospace,
                        ),
                        modifier = Modifier.padding(Dimens.padding4dp)
                    )
                    TextButton(onClick = { isShowingUpdateDialog = true }) {
                        Text(updateCardLabel)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))
    }

    if (isShowingUpdateDialog) {
        Dialog(onDismissRequest = { isShowingUpdateDialog = false }) {
            Card {
                Column(Modifier.padding(Dimens.padding8dp)) {
                    Icon(
                        MaterialTheme.icons.AddCard,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(Dimens.padding8dp)
                            .align(Alignment.CenterHorizontally)
                    )

                    Text(
                        updateCardLabel,
                        style = MaterialTheme.typography.h6,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(Dimens.padding8dp)
                            .align(Alignment.CenterHorizontally),
                    )

                    TextField(
                        value = cardNumber,
                        onValueChange = { cardNumber = it },
                        label = { Text("Card number") },
                        modifier = Modifier.padding(Dimens.padding8dp),
                    )
                    TextField(
                        value = cardCVV,
                        onValueChange = { cardCVV = it },
                        label = { Text("Card CVV") },
                        modifier = Modifier.padding(Dimens.padding8dp),
                    )
                    Button(
                        onClick = {
                            updateCard(
                                Card(
                                    number = cardNumber,
                                    cvv = cardCVV,
                                )
                            )
                            isShowingUpdateDialog = false
                        },
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(Dimens.padding8dp)
                    ) {
                        Text(updateCardLabel)
                    }
                }
            }
        }
    }
}