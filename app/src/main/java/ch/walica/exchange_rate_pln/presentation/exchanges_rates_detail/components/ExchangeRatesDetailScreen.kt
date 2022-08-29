package ch.walica.exchange_rate_pln.presentation.exchanges_rates_detail.components

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
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
    viewModel: ExchangeRatesDetailViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
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
                            text = it.currency.uppercase(),
                            style = MaterialTheme.typography.h1,
                            color = if (isSystemInDarkTheme()) grey1 else Color.DarkGray,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                },
                navigationIcon = {
                                 IconButton(onClick = { navController.popBackStack()}) {
                                     Icon(
                                         imageVector = Icons.Default.ArrowBack,
                                         contentDescription = "Back Arrow"
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
        Box(
            modifier = modifier
        ) {
            Card(
                modifier = Modifier
                    .padding(horizontal = 22.dp)
                    .padding(top = 28.dp),
                shape = RoundedCornerShape(12.dp),
                elevation = 16.dp,
                backgroundColor = if (isSystemInDarkTheme()) Color.Black else Color.White
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    state.exchangeRatesDetail?.let { item -> Text(
                        text = "1 ${item.code}",
                        style = MaterialTheme.typography.h1,
                        color = color5,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                    ) }

                    LazyColumn {
                        state.exchangeRatesDetail?.let { exchangeRatesDetail ->
                            items(exchangeRatesDetail.rates.reversed()) { rate ->
                                ExchangeRatesDetailContent(rate, exchangeRatesDetail.code)
                            }
                        }
                    }
                }

            }


            if(state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            if(state.error.isNotBlank()) {
                Text(
                    text = state.error,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}