package me.admund.marketspecificui.sample.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.admund.marketspecificui.sample.R
import me.admund.marketspecificui.compose.marketSpecificPainterResource
import me.admund.marketspecificui.compose.marketSpecificStringResource
import me.admund.marketspecificui.sample.compose.theme.MarketSpecificComponentsTheme

class ComposeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MarketSpecificComponentsTheme {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Image(
                        modifier = Modifier.size(width = 120.dp, height = 70.dp),
                        painter = marketSpecificPainterResource(id = R.drawable.flag),
                        contentDescription = null
                    )
                    Text(text = marketSpecificStringResource(id = R.string.title))
                    Button(onClick = {}) {
                        Text(marketSpecificStringResource(id = R.string.button))
                    }
                }
            }
        }
    }
}