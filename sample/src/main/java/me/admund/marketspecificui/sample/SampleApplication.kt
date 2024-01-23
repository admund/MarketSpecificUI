package me.admund.marketspecificui.sample

import MarketSpecificComponentsResData
import android.app.Application
import me.admund.marketspecificui.MarketSpecificUiLib

class SampleApplication : Application() {
    init {
        MarketSpecificUiLib.addResData(MarketSpecificComponentsResData)
    }
}
