package me.admund.marketspecificui.sample

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import me.admund.marketspecificui.sample.compose.ComposeActivity
import me.admund.marketspecificui.sample.compose.UiSystemChooser
import me.admund.marketspecificui.sample.compose.theme.MarketSpecificComponentsTheme
import me.admund.marketspecificui.sample.views.ViewsActivity

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
