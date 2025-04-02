package com.muhamaddzikri0103.exchango.ui.screen

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.muhamaddzikri0103.exchango.R
import com.muhamaddzikri0103.exchango.model.Currency
import com.muhamaddzikri0103.exchango.model.EUR
import com.muhamaddzikri0103.exchango.model.GBP
import com.muhamaddzikri0103.exchango.model.IDR
import com.muhamaddzikri0103.exchango.model.JPY
import com.muhamaddzikri0103.exchango.model.USD
import com.muhamaddzikri0103.exchango.ui.theme.ExchanGoTheme
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.app_name))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { innerPadding ->
        ScreenContent(Modifier.padding(innerPadding))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenContent(modifier: Modifier = Modifier) {
    val currencies = mapOf(
        "USD" to USD(),
        "IDR" to IDR(),
        "EUR" to EUR(),
        "GBP" to GBP(),
        "JPY" to JPY()
    )

    var selectedCurrency by remember { mutableStateOf(currencies["USD"] as Currency? ?: USD()) }
    var fromCurrency by remember { mutableStateOf(selectedCurrency.code) }
    var toCurrency by remember { mutableStateOf("EUR") }

    var amount by remember { mutableStateOf("") }
    var convertedAmount by remember { mutableStateOf(0.0) }

    var imageId by remember { mutableStateOf(selectedCurrency.flagResId[toCurrency] ?: R.drawable.usd_usd) }

    var isFromExpanded by remember { mutableStateOf(false) }
    var isToExpanded by remember { mutableStateOf(false) }

    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
        ,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "ExchanGo",
//            modifier = modifier
        )
        Image(
            painter = painterResource(imageId),
            contentDescription = "usd to eur",
//            contentScale = ContentScale.Crop,
//            modifier = Modifier.size(132.dp)
        )
        ExposedDropdownMenuBox(
            expanded = isFromExpanded,
            onExpandedChange = { isFromExpanded = !isFromExpanded },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                value = stringResource(getString(fromCurrency)),
                onValueChange = {},
                label = { Text(text = stringResource(R.string.from)) },
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = isFromExpanded)
                },
            )
            ExposedDropdownMenu(
                expanded = isFromExpanded,
                onDismissRequest = { isFromExpanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                currencies.keys.forEach { currencyCode ->
                    DropdownMenuItem(
                        modifier = Modifier.fillMaxWidth(),
                        text = {
                            Text(text = stringResource(getString(currencyCode)))
                        },
                        onClick = {
                            selectedCurrency = currencies[currencyCode] as Currency? ?: USD()
                            fromCurrency = currencyCode
                            imageId = selectedCurrency?.flagResId?.get(toCurrency) ?: R.drawable.usd_usd
                            isFromExpanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }
        ExposedDropdownMenuBox(
            expanded = isToExpanded,
            onExpandedChange = { isToExpanded = !isToExpanded },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                value = stringResource(getString(toCurrency)),
                onValueChange = {},
                label = { Text(text = stringResource(R.string.to)) },
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = isToExpanded)
                }
            )
            ExposedDropdownMenu(
                expanded = isToExpanded,
                onDismissRequest = { isToExpanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                currencies.keys.forEach { currencyCode ->
                    DropdownMenuItem(
                        modifier = Modifier.fillMaxWidth(),
                        text = {
                            Text(text = stringResource(getString(currencyCode)))
                        },
                        onClick = {
                            toCurrency = currencyCode
                            imageId = selectedCurrency?.flagResId?.get(toCurrency) ?: R.drawable.usd_usd
                            isToExpanded = false
                        }
                    )

                }
            }
        }
        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text(text = stringResource(R.string.amount)) },
            leadingIcon = { Text(text = selectedCurrency.symbol) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {
                convertedAmount = selectedCurrency.convert(amount.toDouble(), toCurrency)
            },
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(0.5f),
            contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
        ) {
            Text(text = stringResource(R.string.convert))
        }

        if (convertedAmount != 0.0) {
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 16.dp),
                thickness = 1.dp
            )
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "${formatNumber(amount.toDouble())} " + stringResource(selectedCurrency.name) + " =",
                    style = MaterialTheme.typography.titleSmall
                )
            }
            Column(
                modifier = Modifier.fillMaxWidth(),
//                verticalArrangement = Arrangement.Center
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "${formatNumber(convertedAmount)} " + stringResource(getCurrName(toCurrency)),
                    style = MaterialTheme.typography.headlineLarge,
                )
            }
            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                val otherCurr = currencies[toCurrency]?.conversionRates?.get(fromCurrency)
                val selectedCurr = currencies[fromCurrency]?.conversionRates?.get(toCurrency)

                Text(
                    text = "1 $fromCurrency = ${selectedCurr?.let { formatNumber(it) }} $toCurrency",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "1 $toCurrency = ${otherCurr?.let { formatNumber(it) }} $fromCurrency",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

fun getString(currencyCode: String): Int {
    return if (currencyCode == "USD") {
        R.string.usd
    } else if (currencyCode == "IDR") {
        R.string.idr
    } else if (currencyCode == "EUR") {
        R.string.eur
    } else if (currencyCode == "GBP") {
        R.string.gbp
    } else {
        R.string.jpy
    }
}

fun getCurrName(currencyCode: String): Int {
    return if (currencyCode == "USD") {
        R.string.usd_name
    } else if (currencyCode == "IDR") {
        R.string.idr_name
    } else if (currencyCode == "EUR") {
        R.string.eur_name
    } else if (currencyCode == "GBP") {
        R.string.gbp_name
    } else {
        R.string.jpy_name
    }
}

fun formatNumber(amount: Double): String {
    val pattern = if (amount <= 1) "#,###.######" else "#,###.##"
    val formatter = DecimalFormat(pattern)
    return formatter.format(amount)
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun MainScreenPreview() {
    ExchanGoTheme {
        MainScreen()
    }
}