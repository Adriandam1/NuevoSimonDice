package com.example.nuevosimondice

import android.media.MediaPlayer
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import android.widget.Toast
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


/**
 * Interfaz para mostrar el numero de aciertos del usuario
 */
@Composable
fun showAciertos(aciertos:Int){
    Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(top = 90.dp, start = 126.dp)) {
        Text(text = "Aciertos: $aciertos" ,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold)
        Log.d("Record", aciertos.toString())
    }
}

/**
 * Interfaz para mostrar el record maximo del usuario en el juego
 */
@Composable
fun RecordMaximo(record:Int){
    Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
            .padding(top = 50.dp, start = 130.dp)) {

        Text(text = "Record: $record" ,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold)
    }
}
/**
 * Interfaz con el boton rojo
 * La función recibe el ViewModel y las listas que almacenan los colores seleccionados y los colores aleatorios del juego.
 * El botón se habilita o deshabilita dependiendo del estado observado desde el ViewModel.
 * Cuando el usuario presiona un botón, se llama a addColor para registrar la selección de color.
 * El color del botón depende del parámetro color que se pasa.
 */
@Composable
fun buttonColor(viewModel: MyViewModel, listaColores: MutableList<Int>, lista_Random:MutableList<Int>, colorValor:Int, color: Color){

    val context = LocalContext.current // Obtener el contexto actual

    var _activo by remember { mutableStateOf(viewModel.estadoLiveData.value!!.botonesColoresActivos) }

    viewModel.estadoLiveData.observe(LocalLifecycleOwner.current) {
        _activo = viewModel.estadoLiveData.value!!.botonesColoresActivos
    }

    Button(
        enabled = _activo,
        onClick = {
            viewModel.addColor(context, colorValor,listaColores, lista_Random)
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = color,
        ),
        modifier = Modifier
            .clip(RectangleShape)
            .padding(3.dp)
            .size(95.dp)
    ){

    }
}
/**
 * Interfaz para mostrar las rondas que lleva el usuario
 */
@Composable
fun showRondas(numeroRondas:Int){

    Text(
        text = "Ronda: $numeroRondas",
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        modifier = Modifier
            .padding(top = 100.dp)
    )
}
/**
 * Interfaz que muestra el boton de start
 * Esta función observa el estado de la variable startActivo en el ViewModel.
 * Si el botón está habilitado, el usuario puede hacer clic para comenzar el juego.
 * Al hacer clic, se llama a la función setRandom para generar la secuencia de colores inicial del juego.
 * El botón tiene un diseño rectangular con un color rosa.
 */
@Composable
fun showButtonStart(viewModel: MyViewModel){

    var _activo by remember { mutableStateOf(viewModel.estadoLiveData.value!!.startActivo) }

    viewModel.estadoLiveData.observe(LocalLifecycleOwner.current) {
        _activo = viewModel.estadoLiveData.value!!.startActivo
    }
    // Inicializamos MediaPlayer
    val mediaPlayer = MediaPlayer.create(LocalContext.current, R.raw.discord)

    val rosa = Color(0xFFFF00C9)
    Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(top = 40.dp))

    {
        Button(
            enabled = _activo,
            onClick = { viewModel.setRandom()
                // Reproducir sonido cuando se presiona el botón
                mediaPlayer.start()
                      },

            colors = ButtonDefaults.buttonColors(
                containerColor = rosa,
                contentColor = Color.Black
            ),
            modifier = Modifier
                .size(145.dp)
                .clip(RectangleShape)
        ) {
            Text(text = "Start",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold)

        }
    }

}
/**
 * app principal del juego
 * Objetivo: Esta es la interfaz principal del juego que junta todas las vistas anteriores y organiza cómo se muestran en pantalla.
 * Uso: Crea un Box que contiene una imagen de fondo, las vistas de rondas y aciertos, y los botones de colores que el usuario puede presionar.
 * También incluye el botón para iniciar el juego.
 */
@Composable
fun myApp(viewModel: MyViewModel) {

    val record by viewModel.recordLiveData.observeAsState(viewModel.getRecord())
    val rondas by viewModel.rondasLiveData.observeAsState(viewModel.getRondas())
    val aciertos by viewModel.aciertosLiveData.observeAsState(viewModel.getAciertos())

    var lista_colores = remember { mutableStateListOf<Int>() }
    val colorRojo by viewModel.colorRojoLiveData.observeAsState(viewModel.getColorRed())
    val colorVerde by viewModel.colorVerdeLiveData.observeAsState(viewModel.getColorGreen())
    val colorAzul by viewModel.colorAzulLiveData.observeAsState(viewModel.getColorBlue())
    val colorAmarillo by viewModel.colorAmarilloLiveData.observeAsState(viewModel.getColorYellow())

    Box (modifier = Modifier
        .fillMaxSize()
    ){
        val backgroundImage = painterResource(id = R.drawable.daniel)
        Image(
            painter = backgroundImage,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column {
            RecordMaximo(record)
            showAciertos(aciertos)
        }
        Column(

            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(70.dp)
                .padding(top = 190.dp, start = 15.dp)
        ) {
            Row {

                buttonColor(viewModel, lista_colores, viewModel.getRandom(), Colores.ROJO.valorColor, colorRojo)
                buttonColor(viewModel, lista_colores, viewModel.getRandom(), Colores.VERDE.valorColor, colorVerde)
            }
            Row {
                buttonColor(viewModel, lista_colores, viewModel.getRandom(), Colores.AZUL.valorColor, colorAzul)
                buttonColor(viewModel, lista_colores, viewModel.getRandom(), Colores.AMARILLO.valorColor, colorAmarillo)
            }
            showRondas(rondas)
            showButtonStart(viewModel)
        }

        // Aqui me tome una licencia por que me parecio muy chulo el MediaPlayer y puse otro boton para poner musicote------
        // Estado para habilitar o deshabilitar el botón Doom
        var isButtonEnabled by remember { mutableStateOf(true) }

        // Crea el mediaPlayer para reproducir el sonido "doom"
        val mediaPlayerDoom = MediaPlayer.create(LocalContext.current, R.raw.doom)

        // Configura un listener para volver a habilitar el botón cuando termine la reproducción
        mediaPlayerDoom.setOnCompletionListener {
            isButtonEnabled = true // Habilita el botón cuando termine de sonar
        }

        Button(
            onClick = {
                if (isButtonEnabled) { // Solo permite iniciar el sonido si el botón está habilitado
                    mediaPlayerDoom.start()
                    isButtonEnabled = false // Deshabilita el botón al iniciar el sonido
                }
            },
            modifier = Modifier
                .align(Alignment.BottomEnd) // Coloca el botón en la parte inferior derecha
                .size(85.dp), // Hacer el botón pequeño y cuadrado
            colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray), // Color de fondo del botón
            shape = RoundedCornerShape(0.dp), // Sin bordes redondeados, forma rectangular
            enabled = isButtonEnabled // Estado del botón, habilitado o deshabilitado
        ) {
            Text(
                text = "Doom", // El texto que aparecerá en el botón
                color = Color.White, // Color del texto
                fontWeight = FontWeight.Bold // Texto en negrita
            )
        }
    }
}