package me.admund.marketspecificui.compose

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import me.admund.marketspecificui.MarketSpecificLib

@Composable
fun marketSpecificStringResource(@StringRes id: Int): String {
    val specificId = MarketSpecificLib.provideMarketSpecificRes(id)
    return stringResource(id = specificId)
}

@Composable
fun marketSpecificPainterResource(@DrawableRes id: Int): Painter {
    val specificId = MarketSpecificLib.provideMarketSpecificRes(id)
    return painterResource(id = specificId)
}
