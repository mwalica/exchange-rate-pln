package ch.walica.exchange_rate_pln.utli

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ExperimentalGraphicsApi
import kotlin.random.Random

@OptIn(ExperimentalGraphicsApi::class)
fun randomColor(): Color = Color.hsl((0..360).random().toFloat(), Random.nextFloat(), Random.nextFloat())
