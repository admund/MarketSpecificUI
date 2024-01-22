package me.admund.marketspecificui

typealias ResData = Map<Int, Map<String, Int>>

object MarketSpecificLib {

    private var _marketSuffix: String? = null
    private var _resData = mutableMapOf<Int, Map<String, Int>>()

    fun addResData(resData: ResData) {
        _resData.putAll(resData)
    }

    fun setMarketSuffix(marketSuffix: String) {
        _marketSuffix = marketSuffix
    }

    fun provideMarketSpecificRes(resId: Int): Int =
        if (_marketSuffix == null) {
            resId
        } else {
            _resData[resId]?.get(_marketSuffix) ?: resId
        }
}