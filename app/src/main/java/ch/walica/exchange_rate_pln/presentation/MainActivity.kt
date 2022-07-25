package ch.walica.exchange_rate_pln.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ch.walica.exchange_rate_pln.presentation.add_currency_screen.components.AddCurrencyScreen
import ch.walica.exchange_rate_pln.presentation.exchanges_rates_list.components.ExchangeRatesListScreen
import ch.walica.exchange_rate_pln.presentation.ui.theme.Exchange_rate_plnTheme
import ch.walica.exchange_rate_pln.presentation.ui.theme.lightBlack
import ch.walica.exchange_rate_pln.presentation.ui.theme.lightGrey
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            Exchange_rate_plnTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colors.background
                    color = if(isSystemInDarkTheme())  lightBlack else lightGrey
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.ExchangeRatesListScreen.route
                    ) {
                        composable(
                            route = Screen.ExchangeRatesListScreen.route
                        ) {
                            ExchangeRatesListScreen(navController = navController)
                        }

                        composable(
                            route = Screen.AddCurrencyScreen.route
                        ) {
                            AddCurrencyScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}
