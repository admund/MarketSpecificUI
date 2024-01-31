package io.github.admund.marketspecificui.compose

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import io.github.admund.marketspecificui.MarketSpecificUiLib

@Composable
fun marketSpecificStringResource(@StringRes id: Int): String {
    val specificId = MarketSpecificUiLib.provideMarketSpecificRes(id)
    return stringResource(id = specificId)
}

@Composable
fun marketSpecificPainterResource(@DrawableRes id: Int): Painter {
    val specificId = MarketSpecificUiLib.provideMarketSpecificRes(id)
    return painterResource(id = specificId)
}
