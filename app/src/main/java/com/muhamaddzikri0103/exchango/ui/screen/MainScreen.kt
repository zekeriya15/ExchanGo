package com.muhamaddzikri0103.exchango.ui.screen

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
        "USD" to listOf(stringResource(R.string.usd), USD()),
        "IDR" to listOf(stringResource(R.string.idr), IDR()),
        "EUR" to listOf(stringResource(R.string.eur), EUR()),
        "GBP" to listOf(stringResource(R.string.gbp), GBP()),
        "JPY" to listOf(stringResource(R.string.jpy), JPY())
    )

    var selectedCurrency by remember { mutableStateOf(currencies["USD"]?.get(1) as Currency? ?: USD()) }
    var fromCurrency by remember { mutableStateOf(selectedCurrency.name) }
    var toCurrency by remember { mutableStateOf("EUR") }

    var amount by remember { mutableStateOf(0.0) }
    var convertedAmount by remember { mutableStateOf(0.0) }

    var imageId by remember { mutableStateOf(selectedCurrency.flagResId[toCurrency] ?: R.drawable.usd_usd) }

    var isFromExpanded by remember { mutableStateOf(false) }
    var isToExpanded by remember { mutableStateOf(false) }

    val context = LocalContext.current

    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
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
            onExpandedChange = { isFromExpanded = !isFromExpanded }
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth().menuAnchor(),
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
                onDismissRequest = { isFromExpanded = false }
            ) {
                currencies.keys.forEach { currencyCode ->
                    DropdownMenuItem(
                        modifier = Modifier.fillMaxWidth(),
                        text = {
                            Text(text = stringResource(getString(currencyCode)))
                        },
                        onClick = {
                            selectedCurrency = currencies[currencyCode]?.get(1) as Currency? ?: USD()
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
            onExpandedChange = { isToExpanded = !isToExpanded }
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth().menuAnchor(),
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
                onDismissRequest = { isToExpanded = false }
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







@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun MainScreenPreview() {
    ExchanGoTheme {
        MainScreen()
    }
}