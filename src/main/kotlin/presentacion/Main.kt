package presentacion

import servicios.Consola

// Punto de entrada del programa. La función main es la que comienza el flujo del juego.
fun main() {

    val titulo = "MAZMORRAS DE GUNTER: EL ASCENSO DEL REY DEMONIO"
    val longitud = 60

    val espaciado = (longitud - titulo.length) / 2

    // Función que muestra el título del juego
    fun mostrarTitulo() {
        println("_".repeat(longitud + 2))
        println("|" + " ".repeat(espaciado) + " ".repeat(titulo.length) + " ".repeat(longitud - espaciado - titulo.length) + "|")
        println("|" + " ".repeat(espaciado) + titulo + " ".repeat(longitud - espaciado - titulo.length) + "|")
        println("|" + " ".repeat(espaciado) + " ".repeat(titulo.length) + " ".repeat(longitud - espaciado - titulo.length) + "|")
        println("_".repeat(longitud + 2))
    }

    mostrarTitulo()

    val consola = Consola()

    consola.mostrarMenuPrincipal()

}