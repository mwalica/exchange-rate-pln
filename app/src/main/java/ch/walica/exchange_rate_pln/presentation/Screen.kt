package ch.walica.exchange_rate_pln.presentation

sealed class Screen(val route: String) {
    object ExchangeRatesListScreen: Screen(route = "exchange_rates_list")
    object AddCurrencyScreen: Screen(route = "add_currency")
    object ExchangeRatesDetailScreen: Screen(route = "exchange_rates_list")
}
