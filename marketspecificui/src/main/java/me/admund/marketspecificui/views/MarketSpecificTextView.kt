package me.admund.marketspecificui.views

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.withStyledAttributes
import me.admund.marketspecificui.MarketSpecificResolver
import me.admund.marketspecificui.R

class MarketSpecificTextView(context: Context, attrs: AttributeSet?) :
    AppCompatTextView(context, attrs) {

    private val resolver = MarketSpecificResolver

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
                setText(resolver.provideMarketSpecificRes(textRes))
            }
        }
    }

    fun setMarketSpecificText(@StringRes resId: Int) {
        setText(resolver.provideMarketSpecificRes(resId))
    }

    fun setMarketSpecificFormatText(@StringRes resId: Int, vararg args: Any) {
        text = String.format(context.getString(resolver.provideMarketSpecificRes(resId)), *args)
    }
}