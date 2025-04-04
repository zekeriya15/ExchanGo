package com.muhamaddzikri0103.exchango.ui.screen

import android.content.res.Configuration
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.muhamaddzikri0103.exchango.R
import com.muhamaddzikri0103.exchango.model.EUR
import com.muhamaddzikri0103.exchango.model.GBP
import com.muhamaddzikri0103.exchango.model.IDR
import com.muhamaddzikri0103.exchango.model.JPY
import com.muhamaddzikri0103.exchango.model.USD
import com.muhamaddzikri0103.exchango.ui.theme.ExchanGoTheme
import java.text.DecimalFormat

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

    var selectedCurrency by remember { mutableStateOf(currencies["USD"] ?: USD()) }
    var fromCurrency by remember { mutableStateOf(selectedCurrency.code) }
    var toCurrency by remember { mutableStateOf("EUR") }

    var convCurrencyName by remember { mutableIntStateOf(selectedCurrency.name) }
    var convFromCurrency by remember { mutableStateOf(fromCurrency) }
    var convToCurrency by remember { mutableStateOf(toCurrency) }
    var displayAmount by remember { mutableStateOf("0") }

    var amount by remember { mutableStateOf("") }
    var convertedAmount by remember { mutableDoubleStateOf(0.0) }

    var amountError by remember { mutableStateOf(false) }

    var imageId by remember { mutableIntStateOf(selectedCurrency.flagResId[toCurrency] ?: R.drawable.usd_eur) }

    var isFromExpanded by remember { mutableStateOf(false) }
    var isToExpanded by remember { mutableStateOf(false) }

//    val context = LocalContext.current

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
            text = stringResource(R.string.title),
            fontWeight = FontWeight.SemiBold
        )
        Image(
            painter = painterResource(imageId),
            contentDescription = stringResource(R.string.curr_flag),
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
                value = stringResource(getStringResId(fromCurrency)),
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
                    if (currencyCode == toCurrency) return@forEach
                    DropdownMenuItem(
                        modifier = Modifier.fillMaxWidth(),
                        text = { Text(text = stringResource(getStringResId(currencyCode))) },
                        onClick = {
                            selectedCurrency = currencies[currencyCode] ?: USD()
                            fromCurrency = currencyCode
                            imageId = selectedCurrency.flagResId.get(toCurrency) ?: R.drawable.usd_eur
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
                value = stringResource(getStringResId(toCurrency)),
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
                    if (currencyCode == fromCurrency) return@forEach
                    DropdownMenuItem(
                        modifier = Modifier.fillMaxWidth(),
                        text = { Text(text = stringResource(getStringResId(currencyCode))) },
                        onClick = {
                            toCurrency = currencyCode
                            imageId = selectedCurrency.flagResId.get(toCurrency) ?: R.drawable.usd_eur
                            isToExpanded = false
                        }
                    )

                }
            }
        }
        OutlinedTextField(
            value = amount,
            onValueChange = { value ->
                amount = value
                amountError = false
            },
            label = { Text(text = stringResource(R.string.amount)) },
            leadingIcon = { IconPicker(amountError, selectedCurrency.symbol) },
            supportingText = {
                if (amountError) {
                    Text(text = stringResource(R.string.invalid_input))
                }
            },
            isError = amountError,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {
                val amountValue = amount.toDoubleOrNull()

                if (amountValue == null || amount.isEmpty() || amount == "0") {
                    amountError = true
                    return@Button
                }

                convertedAmount = selectedCurrency.convert(amountValue, toCurrency)
//              displayAmount, convCurrencyName, convFromCurrency, convToCurrency only change when the button
//              is clicked and not directly using the repr. state cuz repr. state will always recompose the changes
                displayAmount = amount
                convCurrencyName = selectedCurrency.name
                convFromCurrency = fromCurrency
                convToCurrency = toCurrency
            },
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(0.5f),
            contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
        ) {
            Text(text = stringResource(R.string.convert))
        }

//        once convertedAmount is not 0.0 the code below it will always be rendered and the changes
//        to the code below it only happen when convertedAmount recalculated inside the button onClick
        if (convertedAmount != 0.0) {
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 16.dp),
                thickness = 1.dp
            )
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "${formatNumber(displayAmount.toDoubleOrNull() ?: 0.0)} " + stringResource(convCurrencyName) + " =",
                    style = MaterialTheme.typography.titleSmall
                )
            }
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "${formatNumber(convertedAmount)} " + stringResource(getCurrName(convToCurrency)),
                    style = MaterialTheme.typography.headlineLarge,
                )
            }
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                val otherCurr = currencies[convToCurrency]?.conversionRates?.get(convFromCurrency)
                val selectedCurr = currencies[convFromCurrency]?.conversionRates?.get(convToCurrency)

                Text(
                    text = "1 $convFromCurrency = ${selectedCurr?.let { formatNumber(it) }} $convToCurrency",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "1 $convToCurrency = ${otherCurr?.let { formatNumber(it) }} $convFromCurrency",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth(0.4f).padding(top = 8.dp),
                contentPadding = PaddingValues(8.dp)
            ) {
                Text(text = stringResource(R.string.share))
            }

        }
    }
}

@Composable
fun IconPicker(isError: Boolean, unit: String) {
    if (isError) {
        Icon(imageVector = Icons.Filled.Warning, contentDescription = unit)
    } else {
        Text(text = unit)
    }
}

fun getStringResId(currencyCode: String): Int {
    return when (currencyCode) {
        "USD" -> {
            R.string.usd
        }
        "IDR" -> {
            R.string.idr
        }
        "EUR" -> {
            R.string.eur
        }
        "GBP" -> {
            R.string.gbp
        }
        else -> {
            R.string.jpy
        }
    }
}

fun getCurrName(currencyCode: String): Int {
    return when (currencyCode) {
        "USD" -> {
            R.string.usd_name
        }
        "IDR" -> {
            R.string.idr_name
        }
        "EUR" -> {
            R.string.eur_name
        }
        "GBP" -> {
            R.string.gbp_name
        }
        else -> {
            R.string.jpy_name
        }
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