package com.ivy.iyannah.foodpost.ui

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import com.ivy.iyannah.foodpost.extension.lerpF
import com.ivy.iyannah.foodpost.extension.toDp
import com.ivy.iyannah.foodpost.extension.toPxf

enum class Screen {
    Home,
    MyPosts,
    Profile,
}


@Composable
fun rememberScreenState(
    constraints: Constraints,
    density: Density = LocalDensity.current,
    isInPreviewMode: Boolean = LocalInspectionMode.current,
) = remember(constraints) {
    ScreenState(
        constraints,
        density,
        isInPreviewMode
    )
}

@Stable
class ScreenState(
    constraints: Constraints,
    private val density: Density,
    isInPreviewMode: Boolean = false,
) {
    @Stable
    private fun Float.toDp() = this.toDp(density)

    var maxContentWidth = constraints.maxWidth
    var maxContentHeight = constraints.maxHeight

    private val easing = FastOutLinearInEasing

    var currentScreen by mutableStateOf(Screen.Home)

    var currentDragOffset by mutableStateOf(0f)

    val songInfoContainerHeight = (maxContentHeight * PlayerControlRatio).toInt()

    val playerContainerHeight = (maxContentHeight * SongInfoContainerRatio).toInt()

    val songInfoContentHeight = songInfoContainerHeight - playerContainerHeight

    val albumContainerHeight = (maxContentHeight * AlbumContainerRatio).toInt()

    val albumImageWidth =
        min((maxContentWidth * 0.35f).toDp(), (maxContentHeight * 0.16f).toDp())

    val commentsContainerHeight = maxContentHeight - albumContainerHeight

    val backHandlerEnabled by derivedStateOf {
        currentScreen != Screen.Home && fromPlayerControlsToAlbumsListProgress == 1f
    }

    val fromPlayerControlsToAlbumsListProgress by derivedStateOf {
        if (isInPreviewMode) {
            0f
        } else {
            currentDragOffset / maxContentWidth
        }
    }

    val playerControlOffset by derivedStateOf {
        val cubicBezierEasing = CubicBezierEasing(
            a = 0.25f,
            b = -songInfoContainerHeight.toFloat() / 5,
            c = 0.5f,
            d = -10.dp.toPxf(density)
        )
        cubicBezierEasing.transform(fromPlayerControlsToAlbumsListProgress) + songInfoOffset
    }

    val commentsListOffset by derivedStateOf {
        -(maxContentWidth * (1f - fromPlayerControlsToAlbumsListProgress)).toInt()
    }

    val songInfoOffset by derivedStateOf {
        -(songInfoContainerHeight * fromPlayerControlsToAlbumsListProgress)
    }

    val albumsInfoSize = (maxContentHeight * AlbumInfoRatio).toDp()
    val photoScale by derivedStateOf {
        easing.transform(
            lerpF(
                StartAlbumPhotoScale,
                StopAlbumPhotoScale,
                fromPlayerControlsToAlbumsListProgress
            )
        )
    }
}

const val SongInfoContainerRatio = 0.5f
const val PlayerControlRatio = 0.75f

const val AlbumInfoRatio = 0.4f
const val StartAlbumPhotoScale = 1f
const val StopAlbumPhotoScale = 1.3f

const val AlbumContainerRatio = 0.45f