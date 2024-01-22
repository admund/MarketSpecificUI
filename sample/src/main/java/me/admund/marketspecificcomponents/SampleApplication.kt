package me.admund.marketspecificcomponents

import android.app.Application
import me.admund.marketspecificui.MarketSpecificLib

class SampleApplication : Application() {
    init {
        MarketSpecificLib.addResData(sampleResData)
    }
}