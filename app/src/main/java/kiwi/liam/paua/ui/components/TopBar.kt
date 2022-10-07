package kiwi.liam.paua.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import components.DevActions
import kiwi.liam.paua.R
import kiwi.liam.paua.ui.theme.icons

@Composable
fun TopBar(backAction: (() -> Unit)? = null) {

    TopAppBar(
        title = { Text(stringResource(id = R.string.app_name)) },
        elevation = 0.dp,
        navigationIcon = backAction?.let {
            {
                IconButton(onClick = it) {
                    Icon(
                        MaterialTheme.icons.ArrowBack,
                        contentDescription = null,
                    )
                }
            }
        },
        actions = { DevActions() },
        modifier = Modifier
            .padding(
                top = WindowInsets.statusBars
                    .asPaddingValues()
                    .calculateTopPadding()
            )
            .fillMaxWidth()
            .background(MaterialTheme.colors.primarySurface),
    )
}
