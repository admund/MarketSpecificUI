package me.admund.marketspecificcomponents

import android.app.Application
import android.util.Log
import me.admund.marketspecificui.MarketSpecificResolver

class SampleApplication : Application() {
    init {
        Log.e("ZXC", "init app")
        MarketSpecificResolver.addResData(sampleResData)
    }
}