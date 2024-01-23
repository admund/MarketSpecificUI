package me.admund.marketspecificui.sample

import MarketSpecificUiResData
import android.app.Application
import me.admund.marketspecificui.MarketSpecificUiLib

class SampleApplication : Application() {
    init {
        MarketSpecificUiLib.addResData(MarketSpecificUiResData)
    }
}
