package me.admund.marketspecificui.sample

import android.app.Application
import me.admund.marketspecificui.MarketSpecificLib

class SampleApplication : Application() {
    init {
        MarketSpecificLib.addResData(MarketSpecificComponentsResData)
    }
}
