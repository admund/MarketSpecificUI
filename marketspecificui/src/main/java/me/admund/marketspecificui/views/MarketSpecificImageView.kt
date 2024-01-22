package me.admund.marketspecificui.views

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.withStyledAttributes
import me.admund.marketspecificui.MarketSpecificLib
import me.admund.marketspecificui.R

class MarketSpecificImageView(context: Context, attrs: AttributeSet?) :
    AppCompatImageView(context, attrs) {

    private val resolver = MarketSpecificLib

    init {
        context.withStyledAttributes(set = attrs, attrs = R.styleable.MarketSpecificView) {
            val imageRes = getResourceId(
                R.styleable.MarketSpecificView_msu__imageRes,
                0
            )
            if (imageRes > 0) {
                setImageResource(resolver.provideMarketSpecificRes(imageRes))
            }
        }
    }

    override fun setImageResource(@DrawableRes resId: Int) {
        super.setImageResource(resolver.provideMarketSpecificRes(resId))
    }
}