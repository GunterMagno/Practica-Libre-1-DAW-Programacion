package dominio.entidades

/**
 * Representa una poción que otorga una cantidad moderada de experiencia al personaje.
 */
class PocionExperiencia: Objeto("Poción de Experiencia") {

    /**
     * Método que permite usar la poción de experiencia en un personaje.
     * Al usar la poción, el personaje recibe 30 puntos de experiencia y puede subir de nivel si es posible.
     *
     * @param personaje El personaje que usará la poción.
     */
    override fun usar(personaje: Personaje) {
        personaje.experiencia += 30
        println("${personaje.nombre} ha conseguido 30 puntos de experiencia.")
        this.usado()
        eliminarInventario(personaje,this)
        personaje.subirNivel()
    }
}