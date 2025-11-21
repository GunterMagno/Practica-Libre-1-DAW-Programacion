package servicios

import dominio.entidades.Mazmorra
import dominio.entidades.Personaje
import dominio.serializable.PersonajeSerializable
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import java.io.File

/**
 * Clase `Consola` que maneja la interacción del jugador con el juego a través de la interfaz de línea de comandos.
 * Permite la creación, carga, listado y eliminación de partidas, así como la gestión de la partida actual,
 * incluyendo la exploración de mazmorras y el combate.
 *
 * @property directorioPartidas Directorio donde se guardan las partidas.
 */
class Consola {
    private val directorioPartidas = "PartidasMazmorra"

    init {
        val dir = File(directorioPartidas)
        if (!dir.exists()) dir.mkdir()
    }

    /**
     * Muestra el menú principal del juego y maneja las opciones seleccionadas por el jugador.
     * Permite crear una nueva partida, cargar una partida guardada, listar partidas guardadas,
     * borrar una partida o salir del juego.
     */
    fun mostrarMenuPrincipal() {
        while (true) {
            println("\n--- MENÚ PRINCIPAL ---")
            println("1. Crear nueva partida")
            println("2. Cargar partida")
            println("3. Listar partidas guardadas")
            println("4. Borrar partida")
            println("5. Salir")
            print("Selecciona una opción: ")

            when (readlnOrNull()?.toIntOrNull()) {
                1 -> crearPartida()
                2 -> cargarPartida()
                3 -> listarPartidas()
                4 -> borrarPartida()
                5 -> {
                    println("Saliendo del juego...")
                    return
                }
                else -> println("Opción inválida. Inténtalo de nuevo.")
            }
        }
    }

    /**
     * Permite crear una nueva partida, solicitando el nombre del personaje,
     * y luego guarda la partida inicial en el archivo correspondiente.
     * @return Nombre de la partida/jugador
     */
    private fun crearPartida(): String? {
        println("\n--- CREACIÓN DE PERSONAJE ---")
        print("Introduce el nombre de tu personaje: ")

        val nombre = readln()

        if (validarNombre(nombre)){
            val archivo = File("$directorioPartidas/$nombre.json")

            if (archivo.exists()) {
                println("Esta partida ya existe, seleccione la opcion de cargar!!.")
                return null
            }

            val personaje = Personaje.crearInstancia(nombre)

            guardarPartida(personaje,true)

            iniciarCombate(personaje)

            return nombre
        }else{
            println("ERROR -> El nombre solo puede contener letras.")
            return null
        }
    }

    /**
     * Valida el nombre del personaje usando una expresión regular.
     *
     * @param nombre El nombre a validar.
     * @return `true` si el nombre es válido, `false` en caso contrario.
     */
    private fun validarNombre(nombre: String): Boolean {
        val regex = Regex("^[A-Za-z]+$")
        return regex.matches(nombre)
    }

    /**
     * Guarda la partida del personaje en un archivo JSON.
     *
     * @param personaje El objeto Personaje que contiene los datos de la partida.
     * @param msj Indica si se debe mostrar un mensaje de confirmación tras guardar.
     */
    private fun guardarPartida(personaje: Personaje, msj :Boolean) {
        val archivo = File("$directorioPartidas/${personaje.nombre}.json")

        val personajeSerializable = PersonajeSerializable.fromPersonaje(personaje)

        archivo.writeText(Json.encodeToString(personajeSerializable))
        if (msj){ println("Partida guardada como ${archivo.name}") }
    }

    /**
     * Carga una partida guardada, deserializando el archivo correspondiente,
     * y restaura el estado del personaje para continuar jugando.
     */
    private fun cargarPartida() {
        val partidas = listarPartidas()
        if (partidas.isEmpty()) return

        print("Introduce el nombre de la partida a cargar: ")
        val nombre = readlnOrNull() ?: return

        val archivo = File("$directorioPartidas/$nombre.json")
        if (!archivo.exists()) {
            println("Esa partida no existe.")
            return
        }

        val personajeSerializable = Json.decodeFromString<PersonajeSerializable>(archivo.readText())
        val personaje = personajeSerializable.toPersonaje()

        println("Partida cargada: ${personaje.nombre}\n")

        iniciarCombate(personaje)
    }

    /**
     * Lista las partidas guardadas en el directorio de partidas.
     * Muestra los nombres de las partidas disponibles para que el jugador elija.
     *
     * @return Lista de nombres de las partidas guardadas.
     */
    private fun listarPartidas(): List<String> {
        val archivos = File(directorioPartidas).listFiles()?.filter { it.extension == "json" }?.map { it.nameWithoutExtension } ?: emptyList()

        if (archivos.isEmpty()) {
            println("No hay partidas guardadas.")
        } else {
            println("\n--- PARTIDAS GUARDADAS ---")
            archivos.forEachIndexed { index, nombre -> println("${index + 1}. $nombre") }
        }
        return archivos
    }

    /**
     * Borra una partida guardada del directorio de partidas.
     * Permite al jugador eliminar una partida específica o la actual.
     *
     * @param borrarActual Indica si se debe borrar la partida actual.
     * @param partidaActual Nombre de la partida actual a borrar, si es necesario.
     */
    private fun borrarPartida(borrarActual: Boolean = false, partidaActual: String? = null) {
        val nombre = if (borrarActual) {
            partidaActual ?: return println("No hay una partida actual para borrar.")
        } else {
            val partidas = listarPartidas()
            if (partidas.isEmpty()) return

            print("Introduce el nombre de la partida a borrar: ")
            readlnOrNull() ?: return
        }

        val archivo = File("$directorioPartidas/$nombre.json")
        if (archivo.exists()) {
            archivo.delete()
            println("Partida '$nombre' eliminada.")
        } else {
            println("Esa partida no existe.")
        }
    }

    /**
     * Inicia el combate en la mazmorra. El personaje explora y combate con los enemigos
     * hasta que su vida llegue a 0 o complete las mazmorras.
     *
     * @param personaje El personaje que va a explorar las mazmorras y luchar.
     */
    private fun iniciarCombate(personaje: Personaje) {

        while (personaje.vida > 0){

            val nivelMazmorra = Mazmorra.obtenerNivelMazmorra(personaje.rondasMazmorra)

            val mazmorra =
                Mazmorra.crearInstancia("${Mazmorra.obtenerNombreMazmorra()} -> Nivel $nivelMazmorra", nivelMazmorra)
            println("\n\n--- Has entrado en la mazmorra ${mazmorra.nombre} ---")

            val combate = Combate(personaje, mazmorra)
            combate.iniciar()

            if (personaje.vida <= 0){
                println("--------------------")
                borrarPartida(true, personaje.nombre)
                println("--------------------")
                return
            }

            println("--- Mazmorra ${mazmorra.nombre} completada!! ---")
            println("\nHas completado ${personaje.rondasMazmorra} mazmorras.")
            println("\nAl completar la mazmorra consigues su tesoro...")

            val cofre = mazmorra.generarCofre()
            val recompensasCofre = cofre.abrir()

            for (recompensa in recompensasCofre){
                personaje.inventario.add(recompensa)
            }

            personaje.rondasMazmorra++
            personaje.mazmorrasLimpiadas.add(mazmorra.nombre)

            guardarPartida(personaje,false)

            println("¿Quieres continuar explorando? (S/N)")
            print("-> ")
            val continuar = readlnOrNull()?.lowercase()
            if (continuar != "s") {
                println("Volviendo al menú principal...")
                return
            }
        }
    }
}