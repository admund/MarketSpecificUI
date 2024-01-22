package me.admund.marketspecificcomponents

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import me.admund.marketspecificcomponents.compose.ComposeActivity
import me.admund.marketspecificcomponents.compose.UiSystemChooser
import me.admund.marketspecificcomponents.compose.theme.MarketSpecificComponentsTheme
import me.admund.marketspecificcomponents.views.ViewsActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MarketSpecificComponentsTheme {
                UiSystemChooser(
                    onComposeClick = {
                        startActivity(ComposeActivity::class.java)
                    },

                    onViewsClick = {
                        startActivity(ViewsActivity::class.java)
                    }
                )
            }
        }
    }

    private fun <T : Activity> startActivity(activityClass: Class<T>) {
        startActivity(
            Intent(this, activityClass)
        )
    }
}
