package ch.walica.exchange_rate_pln.presentation.exchanges_rates_detail.components

import android.os.Build
import android.widget.Space
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ch.walica.exchange_rate_pln.data.remote.dto.RateDetail
import ch.walica.exchange_rate_pln.presentation.ui.theme.*
import ch.walica.exchange_rate_pln.utli.randomColor
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


@Composable
fun ExchangeRatesDetailContent(
    rate: RateDetail,
    code: String,
    randomColor: Color = randomColor(),
) {
    Divider()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "%.4f".format(rate.mid) + " PLN",
            style = MaterialTheme.typography.h3,
            color = if (isSystemInDarkTheme()) grey2 else Color.DarkGray,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)
                .format(LocalDate.parse(rate.effectiveDate)),
        )

    }

}