package io.github.admund.marketspecificui.views;

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.withStyledAttributes
import io.github.admund.marketspecificui.MarketSpecificUiLib
import io.github.admund.marketspecificui.R

class MarketSpecificButton(context: Context, attrs: AttributeSet?) :
    AppCompatButton(context, attrs) {

    init {
        context.withStyledAttributes(set = attrs, attrs = R.styleable.MarketSpecificView) {
            val textRes = getResourceId(
                R.styleable.MarketSpecificView_msu__textRes,
                0
            )
            if (textRes > 0) {
                setText(MarketSpecificUiLib.provideMarketSpecificRes(textRes))
            }
        }
    }

    fun setMarketSpecificText(@StringRes resId: Int) {
        setText(MarketSpecificUiLib.provideMarketSpecificRes(resId))
    }
}
