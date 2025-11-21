package servicios

import dominio.entidades.Entidad

/**
 * Interfaz `Combatiente` que define la acción de atacar de una entidad en el combate.
 * Las clases que implementen esta interfaz deben proporcionar una implementación del método `atacar`.
 * Este método permite que una entidad (ya sea un personaje o un enemigo) ataque a una lista de enemigos.
 *
 * @see Entidad Clase base que representa una entidad en el juego, que puede ser un jugador o un enemigo.
 */
interface Combatiente {
    /**
     * Método que permite a una entidad atacar a una lista de enemigos.
     *
     * @param enemigos Lista de enemigos a los cuales se les puede hacer un ataque.
     * @return El enemigo que ha sido atacado, o `null` si no se ha atacado a ningún enemigo.
     */
    fun atacar(enemigos: List<Entidad>): Entidad?
}