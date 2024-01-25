package me.admund.marketspecificui.views

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.withStyledAttributes
import me.admund.marketspecificui.MarketSpecificUiLib
import me.admund.marketspecificui.R

class MarketSpecificTextView(context: Context, attrs: AttributeSet?) :
    AppCompatTextView(context, attrs) {

    init {
        context.withStyledAttributes(
            set = attrs,
            attrs = R.styleable.MarketSpecificView
        ) {
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

    fun setMarketSpecificFormatText(@StringRes resId: Int, vararg args: Any) {
        text = String.format(context.getString(MarketSpecificUiLib.provideMarketSpecificRes(resId)), *args)
    }
}