package ch.walica.exchange_rate_pln.presentation

sealed class Screen(val route: String) {
    object ExchangeRatesListScreen: Screen(route = "exchange_rates_list")
}
