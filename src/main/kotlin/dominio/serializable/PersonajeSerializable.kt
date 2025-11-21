package dominio.serializable

import dominio.entidades.CreadorObjetos
import dominio.entidades.Personaje
import kotlinx.serialization.Serializable

/**
 * Esta clase representa una versión serializable del objeto `Personaje`.
 * Su propósito es permitir la serialización y deserialización de los datos del personaje
 * para almacenarlos en un formato fácilmente guardable (como JSON) o transferible
 * entre diferentes instancias de la aplicación (por ejemplo, entre el servidor y el cliente).
 *
 * Los objetos serializados contienen la información básica del personaje como su nombre,
 * estadísticas (vida, ataque, armadura, nivel), inventario y progreso (enemigos derrotados, mazmorras limpiadas).
 *
 * La clase provee métodos para convertir entre la representación serializada y la representación
 * real del personaje en el juego.
 */
@Serializable
data class PersonajeSerializable(
    val nombre: String,
    val vidaMaxima: Int,
    val vida: Int,
    val ataque: Int,
    val armadura: Int,
    val nivel: Int = 1,
    val experiencia: Int = 0,
    val inventario: List<String> = emptyList(),
    val enemigosEliminados: Int = 0,
    val bossesEliminados: Int = 0,
    val mazmorrasLimpiadas: MutableList<String> = mutableListOf(),
    val rondasMazmorra: Int = 0,
) {

    /**
     * Convierte este objeto `PersonajeSerializable` a un objeto `Personaje`.
     * Utiliza los datos serializados para restaurar el estado del personaje,
     * incluidos sus atributos como vida, ataque, armadura, nivel y progreso.
     * Además, reconstruye el inventario del personaje a partir de los nombres de los objetos
     * guardados en el inventario.
     *
     * @return Un objeto `Personaje` con la información restaurada desde este `PersonajeSerializable`.
     */
    fun toPersonaje(): Personaje {
        val personaje = Personaje.crearInstancia(nombre)
        personaje.vidaMaxima = vidaMaxima
        personaje.vida = vida
        personaje.ataque = ataque
        personaje.armadura = armadura
        personaje.nivel = nivel
        personaje.experiencia = experiencia
        personaje.enemigosEliminados = enemigosEliminados
        personaje.bossesEliminados = bossesEliminados
        personaje.mazmorrasLimpiadas = mazmorrasLimpiadas
        personaje.rondasMazmorra = rondasMazmorra

        personaje.inventario.addAll(CreadorObjetos.crearListaDesdeNombres(inventario))

        return personaje
    }

    companion object {

        /**
         * Convierte un objeto `Personaje` a su representación serializada `PersonajeSerializable`.
         *
         * @param personaje El objeto `Personaje` que se va a serializar.
         * @return Un objeto `PersonajeSerializable` con la información del personaje.
         */
        fun fromPersonaje(personaje: Personaje): PersonajeSerializable {
            return PersonajeSerializable(
                personaje.nombre,
                personaje.vidaMaxima,
                personaje.vida,
                personaje.ataque,
                personaje.armadura,
                personaje.nivel,
                personaje.experiencia,
                personaje.inventario.map { it.nombre },
                personaje.enemigosEliminados,
                personaje.bossesEliminados,
                personaje.mazmorrasLimpiadas,
                personaje.rondasMazmorra
            )
        }
    }
}
