package servicios

import dominio.entidades.Enemigo
import dominio.entidades.Mazmorra
import dominio.entidades.Personaje

/**
 * Clase `Combate` que gestiona el combate entre el jugador y los enemigos dentro de una mazmorra.
 * La clase contiene toda la lógica relacionada con las rondas de combate, las acciones del jugador y los enemigos.
 *
 * @param personaje El jugador que participa en el combate.
 * @param mazmorra La mazmorra que contiene los enemigos a los que se enfrentará el jugador.
 */
class Combate(private val personaje: Personaje, private val mazmorra: Mazmorra) {
    private var ronda = 1

    /**
     * Inicia el combate, alternando turnos entre el jugador y los enemigos en la mazmorra.
     * El combate continúa hasta que el jugador pierde toda su vida o derrota a todos los enemigos de la mazmorra.
     */
    fun iniciar() {
        ronda = 1
        while (personaje.vida > 0 && mazmorra.obtenerEnemigos().isNotEmpty()) {
            println("\n--- Ronda $ronda ---")
            val enemigos = mazmorra.obtenerEnemigos().toMutableList()

            combate(personaje, enemigos)

            mazmorra.eliminarEnemigosDerrotados()

            if (enemigos.isEmpty()) {
                println("¡Has limpiado la mazmorra! Avanzas al siguiente nivel.")
                mazmorra.limpiarMazmorra()
            }

            ronda++
        }

        if (personaje.vida <= 0) {
            println("\n------------------------------\nHas sido derrotado... Fin de la partida.\n------------------------------\n")
            ronda = 1
            return
        }
    }

    /**
     * Gestiona el flujo de un combate entre el jugador y los enemigos durante un turno.
     * El jugador puede elegir entre atacar, defender, usar objetos o ver estadísticas.
     *
     * @param jugador El personaje jugador que participa en el combate.
     * @param enemigos La lista de enemigos que enfrenta el jugador.
     */
    private fun combate(jugador: Personaje, enemigos: MutableList<Enemigo>) {
        var accionValida = false
        while (!accionValida) {
            println("\n--- TURNO DEL JUGADOR ---\n\n¿Qué quieres hacer?")
            println("1. Atacar")
            println("2. Defender")
            println("3. Usar objeto")
            println("4. Ver estadísticas")
            print("-> ")

            when (readlnOrNull()?.toIntOrNull()) {
                1 -> {
                    val objetivo = jugador.atacar(enemigos)
                    if (objetivo != null) {
                        accionValida = true
                        if (objetivo.vida == 0) {
                            println("\n${objetivo.nombre} a sido derrotado, ${jugador.nombre} gana ${objetivo.experiencia} de experiencia.\n")
                            jugador.experiencia += objetivo.experiencia
                            jugador.subirNivel()
                            if (objetivo is Enemigo) {
                                val objeto = objetivo.dropObjeto()
                                if (objeto != null) {
                                    println("El enemigo ha soltado un objeto -> ${objeto.nombre}. Se ha añadido a tu inventario.")
                                    jugador.inventario.add(objeto)
                                }

                                if (objetivo.esBoss) {
                                    jugador.bossesEliminados++
                                } else {
                                    jugador.enemigosEliminados++
                                }
                            }
                        }
                    }
                }

                2 -> {
                    jugador.defender()
                    accionValida = true
                }
                3 -> {
                    accionValida = jugador.usarObjeto(jugador.inventario)
                }
                4 -> println(jugador)
                else -> println("Opción inválida.")
            }
        }

        mazmorra.eliminarEnemigosDerrotados()
        if (enemigos.size == 1 && enemigos[0].vida == 0) {
            println("\nTodos los enemigos han sido derrotados!!")
        }else{
            println("\n--- TURNO DE LOS ENEMIGOS ---")

            repeat(mazmorra.nivel.numAtaques){
                val enemigo = enemigos.random()
                if (enemigo.vida > 0) {
                    enemigo.atacar(listOf(jugador))
                }
            }
        }
    }
}