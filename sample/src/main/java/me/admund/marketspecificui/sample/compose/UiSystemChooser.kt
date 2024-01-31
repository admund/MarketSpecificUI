package me.admund.marketspecificui.sample.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import me.admund.marketspecificui.sample.compose.theme.MarketSpecificComponentsTheme
import io.github.admund.marketspecificui.MarketSpecificUiLib

@Composable
fun UiSystemChooser(
    onComposeClick: () -> Unit,
    onViewsClick: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        var marketSuffix by rememberSaveable { mutableStateOf("default") }
        Text(text = "Market: $marketSuffix")
        Button(
            onClick = {
                marketSuffix = "us"
                changeMarket("us")
            }
        ) {
            Text(text = "USA")
        }
        Button(
            onClick = {
                marketSuffix = "it"
                changeMarket("it")
            }
        ) {
            Text(text = "ITALY")
        }
        Button(
            onClick = {
                marketSuffix = "mx"
                changeMarket("mx")
            }
        ) {
            Text(text = "MEXICO")
        }
        Button(
            onClick = {
                marketSuffix = "pl"
                changeMarket("pl")
            }
        ) {
            Text(text = "POLAND")
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = onComposeClick) {
                Text(text = "Compose")
            }
            Button(onClick = onViewsClick) {
                Text(text = "Views")
            }
        }
    }
}

private fun changeMarket(marketSuffix: String) {
    MarketSpecificUiLib.setMarketSuffix(marketSuffix)
}

@Preview(showBackground = true)
@Composable
fun UiSystemChooserPreview() {
    MarketSpecificComponentsTheme {
        UiSystemChooser(
            onComposeClick = {},
            onViewsClick = {},
        )
    }
}
