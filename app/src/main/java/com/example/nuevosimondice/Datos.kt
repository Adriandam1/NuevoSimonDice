package com.example.nuevosimondice

import androidx.compose.ui.graphics.Color

/**
 * Singletons con los datos de la aplicacion
 */
object Datos {

    // Los aciertos que lleva el usuario
    var aciertos = 0
    // Las rondas que lleva el usuario
    var rondas = 0
    // Los numeros randoms para meter en la lista de la maquina
    var numRandom = 0
    // El record maximo del usuario
    var record = 0
    // La lista de numeros randoms de la maquina
    val listaNumerosRandom : MutableList<Int> = mutableListOf()


    var listaColores : MutableList<Int> = mutableListOf()
}

/**
 * Clase enum donde tenemos los colores asociados a un valor en concreto
 */
enum class Colores(val valorColor:Int){
    ROJO(1),
    VERDE(2),
    AZUL(3),
    AMARILLO(4)}

enum class ColoresIluminados(var colorNomal : Color, var colorIluminado:Color = Color.Transparent){
    ROJO_PARPADEO(colorNomal = Color(0xFFFF0000)),
    VERDE_PARPADEO(colorNomal = Color(0xFF00FE08)),
    AZUL_PARPADEO(colorNomal = Color(0xFF0017FF)),
    AMARILLO_PARPADEO(colorNomal = Color(0xFFF0FF00))
}

/**
 * Clase enum para manejar los estados de la aplicación
 */
enum class Estados(val startActivo: Boolean, val botonesColoresActivos:Boolean){

    /**
     * Estados de la aplicación
     * 1. Inicio -> cuando se inicia la aplicación y aun no le dimos al start
     * 2. Generando -> cuando la maquina genera los numeros randoms cuando le das al start y salen las toast informadoras
     * 3. Advininando -> cuando el usuario teclea os botones de colores para adivinar los numeros
     */
    INICIO(startActivo = true, botonesColoresActivos = false),
    ADIVINANDO(startActivo = false, botonesColoresActivos = true),

}