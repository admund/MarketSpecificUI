package me.admund.marketspecificcomponents

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import me.admund.marketspecificcomponents.ui.theme.MarketSpecificComponentsTheme

class ViewsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MarketSpecificComponentsTheme {
                Text(text = "Views")
            }
        }
    }
}