package ch.walica.exchange_rate_pln.presentation.exchanges_rates_list.components

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import ch.walica.exchange_rate_pln.data.remote.dto.Rate
import ch.walica.exchange_rate_pln.domain.model.ExchangeRates
import ch.walica.exchange_rate_pln.presentation.ui.theme.*
import ch.walica.exchange_rate_pln.utli.randomColor


//€ $ £ ¥ ₣
@Composable
fun RateItem(
    rate: Rate,
    ratePrev: Rate,
    randomColor: Color = randomColor()
) {
    val currencySymbols =
        mapOf("EUR" to "€", "USD" to "$", "GBP" to "£", "JPY" to "¥", "CHF" to "₣")
    val color = when (rate.code) {
        "EUR" -> color1
        "USD" -> color2
        "GBP" -> color3
        "JPY" -> color4
        "CHF" -> color5
        else -> randomColor
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 22.dp)
            .padding(top = 28.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = 16.dp,
        backgroundColor = if(isSystemInDarkTheme()) Color.Black else Color.White
    ) {
        val percentValue = ((rate.mid * 100) / ratePrev.mid) - 100
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape)
                        .background(color),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = currencySymbols.filter { it.key == rate.code }[rate.code]
                            ?: "${rate.currency.first().uppercase()}",
                        style = MaterialTheme.typography.h5
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = rate.currency.replaceFirstChar { it.uppercase() }
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = buildAnnotatedString {
                        append("1 ${rate.code} = ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("${rate.mid} PLN ")
                        }
                    },
                    style = MaterialTheme.typography.h3,
                    color = if(isSystemInDarkTheme()) grey1 else Color.DarkGray,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "%.2f".format(percentValue) + "%",
                    style = MaterialTheme.typography.h4,
                    color = if (percentValue > 0) color5 else if(percentValue < 0) color2 else grey1
                )
            }
        }
    }

}