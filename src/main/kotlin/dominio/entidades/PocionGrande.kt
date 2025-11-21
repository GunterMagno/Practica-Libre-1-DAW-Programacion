package dominio.entidades

/**
 * Representa una poción que recupera una cantidad grande de vida al personaje.
 */
class PocionGrande: Objeto("Poción Grande") {

    /**
     * Método que permite usar la poción grande en un personaje.
     * Al usar la poción, el personaje recupera 50 puntos de vida.
     *
     * @param personaje El personaje que usará la poción.
     */
    override fun usar(personaje: Personaje) {
        personaje.vida += 50
        println("${personaje.nombre} recupera 50 puntos de vida.")
        this.usado()
        eliminarInventario(personaje,this)
    }
}