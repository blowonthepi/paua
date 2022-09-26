@file:OptIn(ExperimentalAnimationApi::class)

package kiwi.liam.paua.ui.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import components.DevActions
import kiwi.liam.paua.R

interface TopBarRouterDelegate {

    fun showWalletScreen()
    fun showAccountScreen()
}

@Composable
fun TopBar() {

    TopAppBar(
        title = { Text(stringResource(id = R.string.app_name)) }, elevation = 0.dp,
        actions = { DevActions() },
        modifier = Modifier
            .padding(
                top = WindowInsets.statusBars
                    .asPaddingValues()
                    .calculateTopPadding()
            )
            .fillMaxWidth()
            .shadow(4.dp)
            .background(MaterialTheme.colors.primarySurface),
    )
}