# Market-specific UI
Gradle plugin and Android library for market-specific resources for Android apps.

## Plugin

### Configuration
Add dependency in your root `build.gradle.kts` file:
```
plugins {
  id("io.github.admund.marketspecificui") version ("newest_version")
}
```
And configuration
```
configure<io.github.admund.marketspecificui.plugin.MarketSpecificUiPluginExtension> {
    suffixList.set("your_suffix_list")
    resultResDataClassDestinationPath.set("your_destination_path")
}
```
To make this plugin work correctly, your resources must have specific names. You should have "default" res, e.g.:`some_res`. And market-specific version for it with defined suffixes, e.g.: `some_res_us`, `some_res_it`, `some_res_pl`.
In this example, your suffixes list will look like that:
```
suffixList = listOf("us", "pl", "it")
```

`resultResDataClassDestinationPath` is the path where `YourResData.kt` file will be created.

### Usage
Run
```
./gradlew buildMarketSpecificUiResData
```
Or attach to another gradle job that suits you best:
```
project.tasks["build"].dependsOn(":buildMarketSpecificUiResData")

OR

project.tasks[":app:assembleDebug"].dependsOn(":buildMarketSpecificUiResData")
```

## Library

### Configuration
Add library dependency 
```
dependecies {
  implementation("io.github.admund.marketspecificui:newest_version")
}
```
And intialize it (best place is your custom Application class):
```
class YourApplication : Application() {
    init {
        MarketSpecificUiLib.addResData(YourResData)
    }
}
```
You can use `ResData` generated using plugin or you can create it manually. `ResData` should be `Map<Int, Map<String, Int>>`, eg:
```
val YourResData: Map<Int, Map<String, Int>> = mapOf(
  me.admund.marketspecificui.sample.R.string.button to mapOf(
    "us" to me.admund.marketspecificui.sample.R.string.button_us,
    "it" to me.admund.marketspecificui.sample.R.string.button_it,
    "mx" to me.admund.marketspecificui.sample.R.string.button_mx,
    "pl" to me.admund.marketspecificui.sample.R.string.button_pl,
  ),
  me.admund.marketspecificui.sample.R.drawable.flag to mapOf(
    "us" to me.admund.marketspecificui.sample.R.drawable.flag_us,
    "it" to me.admund.marketspecificui.sample.R.drawable.flag_it,
    "mx" to me.admund.marketspecificui.sample.R.drawable.flag_mx,
    "pl" to me.admund.marketspecificui.sample.R.drawable.flag_pl,
  ),
)
```
### Usage
For Compose use:
```
Image(
  ...
  painter = marketSpecificPainterResource(id = R.drawable.flag),
)

OR

Text(
  ...
  text = marketSpecificStringResource(id = R.string.title)
)
```
For old View system:
```
<me.admund.marketspecificui.views.MarketSpecificImageView
    ...
    app:msu__imageRes="@drawable/flag" />

<me.admund.marketspecificui.views.MarketSpecificTextView
    ...
    app:msu__textRes="@string/title" />

<me.admund.marketspecificui.views.MarketSpecificButton
    ...
    app:msu__textRes="@string/button" />
```
Or you can use `object MarketSpecificUiLib` directly, for eg in you custom views.
```
val defualtResId = R.string.some_res
val specificResId = MarketSpecificUiLib.provideMarketSpecificRes(defualtResId)
```

## Read More
An article about this plugin and library is coming soon...
