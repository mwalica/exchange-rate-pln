package ch.walica.exchange_rate_pln.presentation.add_currency_screen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import ch.walica.exchange_rate_pln.domain.model.Currency
import ch.walica.exchange_rate_pln.domain.model.toCurrencyList
import ch.walica.exchange_rate_pln.presentation.add_currency_screen.AddCurrencyViewModel
import ch.walica.exchange_rate_pln.presentation.add_currency_screen.ListOperation
import ch.walica.exchange_rate_pln.presentation.ui.theme.color5

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddCurrencyScreen(
    navController: NavController,
    addCurrencyViewModel: AddCurrencyViewModel = hiltViewModel()
) {
    val listOfCurrency = toCurrencyList()
    val state = addCurrencyViewModel.state
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = buildAnnotatedString {
                append("Dodaj ")
                withStyle(style = SpanStyle(color = color5)) {
                    append(" walutę")
                }
            }.toUpperCase(),
            style = MaterialTheme.typography.h1,
            modifier = Modifier.padding(top = 22.dp, bottom = 22.dp)
        )

        if (listOfCurrency.isNotEmpty()) {
            val currencyMutableList = mutableListOf<String>()
            listOfCurrency.forEach { currency ->
                currencyMutableList.add(currency.currency)
            }

            ExposedDropdownMenuBox(
                expanded = state.isExpanded,
                onExpandedChange = { ListOperation.Expanded(!state.isExpanded) }) {
                TextField(
                    value = state.selectedCurrency,
                    onValueChange = {},
                    readOnly = true,
                    label = {
                        Text(text = "Wybierz walutę")
                    },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = state.isExpanded)
                    }
                )
                ExposedDropdownMenu(
                    expanded = state.isExpanded,
                    onDismissRequest = { ListOperation.Expanded(false) }) {
                    currencyMutableList.forEachIndexed { index, currency ->
                        DropdownMenuItem(onClick = {
                            addCurrencyViewModel.onAction(
                                ListOperation.SelectedCurrency(
                                    listOfCurrency[index].currency
                                )
                            )
                            addCurrencyViewModel.onAction(ListOperation.SelectedCode(listOfCurrency[index].code))
                            addCurrencyViewModel.onAction(ListOperation.Expanded(false))
                        }) {
                            Text(text = currency)
                        }
                    }
                }
            }
            Button(onClick = {
                addCurrencyViewModel.onAction(
                    ListOperation.AddCurrencyCode(
                        Currency(state.selectedCode, state.selectedCurrency)
                    )
                )
                navController.popBackStack()
            }) {
                Text(text = "Dodaj")
            }
        }
    }
}