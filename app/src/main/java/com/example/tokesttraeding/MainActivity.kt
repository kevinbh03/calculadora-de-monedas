package com.example.tokesttraeding

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.tokesttraeding.ui.theme.TokestTraedingTheme
import dagger.hilt.android.AndroidEntryPoint
import java.lang.ref.Reference

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: MonedaViewModel by viewModels()
        installSplashScreen()
        setContent {
            TokestTraedingTheme {
                // A surface container using the 'background' color from the theme
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    ScreeamFrams(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun ScreeamFrams(viewModel: MonedaViewModel) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (title, navegacion, content) = createRefs()
        Box(modifier = Modifier.constrainAs(title) {
            top.linkTo(parent.top)
        }) { Titulo() }
        Box(modifier = Modifier.constrainAs(navegacion) {
            bottom.linkTo(parent.bottom)
        }) { BarraNavegacion() }
        Box(modifier = Modifier.constrainAs(content) {
            top.linkTo(title.bottom)
            bottom.linkTo(navegacion.top)
        }) { Calculadora(viewModel = viewModel) }
    }
}

@Composable
fun Titulo() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 15.dp, bottomEnd = 15.dp))
            .background(Color.Gray),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "TITULO", style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        )
    }
}


@Composable
fun BarraNavegacion() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp))
            .background(Color.Gray),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .clickable { /*TODO*/ }
                .padding(10.dp), contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.home),
                modifier = Modifier.size(25.dp),
                contentDescription = "Home"
            )
        }
        Spacer(
            modifier = Modifier
                .background(Color.Black)
                .width(1.dp)
                .height(30.dp)
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .clickable { /*TODO*/ }
                .padding(10.dp), contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.banderas),
                modifier = Modifier.size(25.dp),
                contentDescription = "banderas"
            )
        }
        Spacer(
            modifier = Modifier
                .background(Color.Black)
                .width(1.dp)
                .height(30.dp)
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .clickable { /*TODO*/ }
                .padding(10.dp), contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.historial),
                modifier = Modifier.size(25.dp),
                contentDescription = "Historial"
            )
        }
    }
}

@Composable
fun Calculadora(viewModel: MonedaViewModel) {
    val showDialogFor = remember { mutableStateOf<Int?>(null) }
    val selectedCurrency1 by viewModel.selectedCurrency1.collectAsState()
    val selectedCurrency2 by viewModel.selectedCurrency2.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //encabezado de monedas
        Row(
            modifier = Modifier
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = selectedCurrency1?.imageRes?: R.drawable.peru),
                modifier = Modifier
                    .padding(10.dp)
                    .size(85.dp)
                    .clickable { showDialogFor.value = 1 },
                contentDescription = "bandera 1"
            )
            Column(
                modifier = Modifier.padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = selectedCurrency1?.code ?: "SOL")
                Spacer(
                    modifier = Modifier
                        .height(1.dp)
                        .width(80.dp)
                        .background(Color.Black)
                )
                Text(text = selectedCurrency2?.code ?: "USD")
            }
            Image(
                painter = painterResource(id = selectedCurrency2?.imageRes?: R.drawable.eeuu),
                modifier = Modifier
                    .padding(10.dp)
                    .size(80.dp)
                    .clickable { showDialogFor.value = 2 },
                contentDescription = "bandera 2"
            )
        }
        //valor moneda 1
        Text(text = selectedCurrency1?.name ?: "Nuevo Sol")
        NumericInputTextField(viewModel, true)
        //boton de cambio
        Box(
            modifier = Modifier.padding(15.dp)
        ) {
            FloatingActionButton(onClick = {  viewModel.swapValues() }) {
                Image(
                    painter = painterResource(id = R.drawable.cambio),
                    contentDescription = "cambio",
                    modifier = Modifier.size(45.dp),
                )
            }
        }
        //valor moneda2
        Text(text = selectedCurrency2?.name ?: "Dólar")
        NumericInputTextField(viewModel, false)
        // botones de accion
        Row() {
            Button(modifier = Modifier.padding(5.dp), onClick = { viewModel.clearTextFields() }) {
                Text(text = "Limpiar")

            }
            Button(modifier = Modifier.padding(5.dp), onClick = { viewModel.convertCurrency() }) {
                Text(text = "Convertir")
            }
        }
        if (showDialogFor.value != null) {
            MonedaOpcion(
                viewModel = viewModel,
                onDismiss = { showDialogFor.value = null },
                forFirstFlag = showDialogFor.value == 1
            )
        }
    }
}

@Composable
fun NumericInputTextField(viewModel: MonedaViewModel, forFirstFlag: Boolean) {
    val textState =
        if (forFirstFlag) viewModel.textState1.collectAsState() else viewModel.textState2.collectAsState()

    TextField(
        value = textState.value,
        onValueChange = { newValue ->
            viewModel.selectText(newValue, forFirstFlag)
        },
        modifier = Modifier.padding(16.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        label = { Text("Introduce número") }
    )
}

@Composable
fun MonedaOpcion(viewModel: MonedaViewModel, onDismiss: () -> Unit, forFirstFlag: Boolean) {
    val selectedCurrencyFlow =
        if (forFirstFlag) viewModel.selectedCurrency1 else viewModel.selectedCurrency2
    val selectedCurrency by selectedCurrencyFlow.collectAsState()

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("Elija la moneda a calcular") },
        text = {
            Column(modifier = Modifier.padding(10.dp)) {
                currenciesList.forEach { currency ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(4.dp))
                            .clickable { viewModel.selectCurrency(currency, forFirstFlag) }
                            .background(if (selectedCurrency == currency) Color.LightGray else Color.Transparent)
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = currency.imageRes),
                            modifier = Modifier.size(60.dp),
                            contentDescription = "Price${currency.name}"
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = currency.name,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = currency.code,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(onClick = { onDismiss() }) {
                Text("Aceptar")
            }
        }
    )
}


