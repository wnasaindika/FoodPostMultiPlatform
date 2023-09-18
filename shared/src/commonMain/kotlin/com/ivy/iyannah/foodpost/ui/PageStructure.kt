package com.ivy.iyannah.foodpost.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.ivy.iyannah.foodpost.common.GRAY_950
import com.ivy.iyannah.foodpost.extension.statusBarsPaddingWithOffset
import com.ivy.iyannah.foodpost.extension.toDp


@Composable
fun FoodPostPage(
    modifier: Modifier,
    onBackPres: () -> Unit,
    onSearchPres: () -> Unit,
    content: @Composable () -> Unit
) {

    BoxWithConstraints(modifier = modifier) {
        val screenState = rememberScreenState(constraints, LocalDensity.current)

        MovingUpPanel(
            modifier = Modifier,
            screenState = screenState,
        ) {
            InfoContainer(
                height = screenState.songInfoContentHeight.toDp()
            )
            ControlContainer(
            )
        }
        BackHandler(isEnabled = screenState.backHandlerEnabled) {
            when {
                sharedElementTransitioned -> goBackFromNowPlayingScreen()
                screenState.currentScreen != Screen.Player -> {
                    screenState.currentScreen = Screen.TransitionToComments
                    collapse()
                }
            }
        }
    }

}