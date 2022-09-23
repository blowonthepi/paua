package kiwi.liam.paua.ui.components

import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import kiwi.liam.paua.R

@Composable
fun TopBar() {
    TopAppBar(
        title = { Text(stringResource(id = R.string.app_name)) },
    )
}