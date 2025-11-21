package dominio.entidades

/**
 * Representa una poción de vida en el juego, que puede ser usada para recuperar puntos de vida de un personaje.
 */
class Pocion : Objeto("Poción de Vida") {

    /**
     * Método que permite usar la poción en un personaje.
     * Al usar la poción, el personaje recupera 20 puntos de vida.
     *
     * @param personaje El personaje que usará la poción.
     */
    override fun usar(personaje: Personaje) {
        personaje.vida += 20
        println("${personaje.nombre} recupera 20 puntos de vida.")
        this.usado()
        eliminarInventario(personaje,this)
    }
}