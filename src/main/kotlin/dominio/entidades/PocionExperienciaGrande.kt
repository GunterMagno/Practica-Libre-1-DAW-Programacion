package dominio.entidades

/**
 * Representa una poción que otorga una gran cantidad de experiencia al personaje.
 */
class PocionExperienciaGrande: Objeto("Poción de Experiencia Grande") {

    /**
     * Método que permite usar la poción de experiencia grande en un personaje.
     * Al usar la poción, el personaje recibe 150 puntos de experiencia y sube de nivel si es posible.
     *
     * @param personaje El personaje que usará la poción.
     */
    override fun usar(personaje: Personaje) {
        personaje.experiencia += 150
        println("${personaje.nombre} ha conseguido 150 puntos de experiencia.")
        this.usado()
        eliminarInventario(personaje,this)
        personaje.subirNivel()
    }
}