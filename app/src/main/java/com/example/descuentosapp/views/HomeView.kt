package com.example.descuentosapp.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.descuentosapp.components.Alert
import com.example.descuentosapp.components.MainButton
import com.example.descuentosapp.components.MainTextField
import com.example.descuentosapp.components.SpaceH
import com.example.descuentosapp.components.TwoCards
import kotlin.math.round

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView() {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "App descuentos")
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )

        }
    ) {
        ContentHomeView(it)
    }

}

@Composable
fun ContentHomeView(paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .padding(paddingValues = paddingValues)
            .padding(10.dp)
            .fillMaxSize(),
        // verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var price by remember {
            mutableStateOf("")
        }
        var descount by remember {
            mutableStateOf("")
        }
        var priceDescuont by remember {
            mutableStateOf(0.0)
        }
        var totalDescount by remember {
            mutableStateOf(0.0)
        }
        var showAlert by remember {
            mutableStateOf(false)
        }

        TwoCards(
            title1 = "Total",
            number1 = totalDescount,
            title2 = "Descuento",
            number2 = priceDescuont
        )

        MainTextField(value = price, onValueChange = { price = it }, label = "Price")
        SpaceH()
        MainTextField(value = descount, onValueChange = { descount = it }, label = "Descount %")
        SpaceH(10.dp)
        MainButton(text = "Generar descuento") {
            if (price != "" && descount != "") {
                priceDescuont =
                    calculatePrice(price = price.toDouble(), descount = descount.toDouble())
                totalDescount =
                    calculateDescount(price = price.toDouble(), descount = descount.toDouble())
            } else {
                showAlert = true
            }

        }
        SpaceH()
        MainButton(text = "Limpiar", color = Color.Red) {
            price = ""
            descount = ""
            priceDescuont = 0.0
            totalDescount = 0.0
        }

        if (showAlert) {
            Alert(
                title = "Alerta",
                message = "Escribe el precio y descuento",
                confirmText = "Aceptar",
                onConfirmClick = { showAlert = false }) {

            }
        }
    }
}

fun calculatePrice(price: Double, descount: Double): Double {
    val res = price - calculateDescount(price = price, descount = descount)
    return round(res * 100) / 100.0
}

fun calculateDescount(price: Double, descount: Double): Double {
    val res = price * (1 - descount / 100)
    return round(res * 100) / 100.0
}