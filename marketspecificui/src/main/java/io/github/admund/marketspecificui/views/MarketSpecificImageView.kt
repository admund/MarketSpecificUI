package io.github.admund.marketspecificui.views

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.withStyledAttributes
import io.github.admund.marketspecificui.MarketSpecificUiLib
import io.github.admund.marketspecificui.R

class MarketSpecificImageView(context: Context, attrs: AttributeSet?) :
    AppCompatImageView(context, attrs) {

    init {
        context.withStyledAttributes(set = attrs, attrs = R.styleable.MarketSpecificView) {
            val imageRes = getResourceId(
                R.styleable.MarketSpecificView_msu__imageRes,
                0
            )
            if (imageRes > 0) {
                setImageResource(MarketSpecificUiLib.provideMarketSpecificRes(imageRes))
            }
        }
    }

    override fun setImageResource(@DrawableRes resId: Int) {
        super.setImageResource(MarketSpecificUiLib.provideMarketSpecificRes(resId))
    }
}