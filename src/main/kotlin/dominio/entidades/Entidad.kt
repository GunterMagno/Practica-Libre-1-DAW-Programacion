package dominio.entidades

import kotlinx.serialization.Serializable


/**
 * Clase abstracta `Entidad` que representa a un personaje o criatura en el juego.
 * Puede ser usada como base para jugadores, enemigos, NPCs, etc.
 *
 * @property nombre Nombre de la entidad.
 * @property vidaMaxima Puntos de vida máximos de la entidad.
 * @property ataque Puntos de ataque de la entidad.
 * @property armadura Nivel de defensa de la entidad.
 * @property nivel Nivel de la entidad.
 * @property experiencia Puntos de experiencia que posee o proporciona la entidad.
 */
@Serializable
abstract class Entidad(var nombre: String,
                       var vidaMaxima: Int,
                       var ataque: Int,
                       var armadura: Int,
                       var nivel: Int,
                       var experiencia: Int
                      ) {


    var vida: Int = vidaMaxima
        set(value) {
            field = when {
                value < 0 -> 0
                value > vidaMaxima -> vidaMaxima
                else -> value
            }
        }

    private var armaduraTemporal = 0

    /**
     * Restablece la vida de la entidad a su valor máximo.
     */
    fun ajustarVida(){vida = vidaMaxima}

    /**
     * Defiende al personaje, aumentando temporalmente su armadura.
     */
    fun defender(){
        armaduraTemporal = armadura / 3

        armadura += armaduraTemporal

        println("\n$nombre esta defendiendose temporalmente aumentando su armadura a $armadura.")
    }

    /**
     * Método para recibir un ataque y calcular el daño efectivo tras aplicar la armadura.
     *
     * @param danio Cantidad de daño recibido.
     * @return Daño real sufrido después de la reducción por armadura.
     */
    fun recibirAtaque(danio:Int): Int{
        var danioEfectivo = danio - armadura
        if (danioEfectivo <= 0){ danioEfectivo = 1}

        vida -= danioEfectivo

        if (armaduraTemporal != 0){
            armadura -= armaduraTemporal
            armaduraTemporal = 0
        }

        return danioEfectivo
    }

    /**
     * Método para elegir un enemigo al atacar.
     *
     * @param enemigos Lista de enemigos disponibles.
     * @param aleatorio Indica si la selección del enemigo es aleatoria o manual.
     * @return La entidad enemiga seleccionada o `null` si no hay enemigos disponibles.
     */
    fun elegirEnemigo(enemigos: List<Entidad>, aleatorio: Boolean): Entidad? {
        if (!aleatorio){
            if (enemigos.isEmpty()) {
                println("\nNo hay enemigos a los que atacar.")
                return null
            }

            println("\nElige un enemigo para atacar:")
            enemigos.forEachIndexed { index, enemigo ->
                println("${index + 1}. ${enemigo.nombre} (Vida: ${enemigo.vida})")
            }
            print("-> ")

            val opcion = readlnOrNull()?.toIntOrNull()
            return if (opcion != null && opcion in 1..enemigos.size) {
                enemigos[opcion - 1]
            } else {
                println("Selección no válida.")
                null
            }
        }else{
            val enemigoAleatorio = enemigos.random()
            return enemigoAleatorio
        }
    }
}