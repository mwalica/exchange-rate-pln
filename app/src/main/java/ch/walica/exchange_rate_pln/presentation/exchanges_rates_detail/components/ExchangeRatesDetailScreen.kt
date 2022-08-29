package ch.walica.exchange_rate_pln.presentation.exchanges_rates_detail.components

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ch.walica.exchange_rate_pln.R
import ch.walica.exchange_rate_pln.presentation.exchanges_rates_detail.ExchangeRatesDetailViewModel
import ch.walica.exchange_rate_pln.presentation.ui.theme.color5
import ch.walica.exchange_rate_pln.presentation.ui.theme.grey1

@Composable
fun ExchangeRatesDetailScreen(
    navController: NavController,
    viewModel: ExchangeRatesDetailViewModel = hiltViewModel()
) {

    val state by viewModel.state
    val activity = LocalContext.current as? Activity
    var showMenu by remember {
        mutableStateOf(false)
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    state.exchangeRatesDetail?.let {
                        Text(
                            text = it.currency,
                            style = MaterialTheme.typography.h1,
                            color = if (isSystemInDarkTheme()) grey1 else Color.DarkGray,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                },
                backgroundColor = Color.Transparent,
                elevation = 0.dp,
                actions = {
                    IconButton(
                        onClick = { showMenu = !showMenu }
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "drop down menu"
                        )
                    }
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }) {
                        DropdownMenuItem(onClick = { activity?.finish() }) {
                            Text(text = stringResource(id = R.string.close_app))
                        }
                    }
                }
            )
        }
    ) {
        Box() {
            LazyColumn() {
                state.exchangeRatesDetail?.let { exchangeRatesDetail ->
                    items(exchangeRatesDetail.rates) { rate ->
                        Text(text = rate.mid.toString())
                    }
                }
            }
        }
    }
}