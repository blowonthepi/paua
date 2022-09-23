package kiwi.liam.paua.screens.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kiwi.liam.paua.ui.theme.Typography

@Composable
fun SplashScreen() {
    Box(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primary)
    ) {
        Text(
            "Pāua",
            modifier = Modifier.align(Alignment.Center),
            style = Typography.h2,
        )
    }
}