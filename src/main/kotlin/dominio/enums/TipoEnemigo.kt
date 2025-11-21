package dominio.enums

/**
 * Enum que representa los tipos de enemigos en el juego.
 */
enum class TipoEnemigo(private val nombre: String) {
    GOBLIN("Goblin"),
    ESQUELETO("Esqueleto"),
    ORCO("Orco"),
    ALTO_ORCO("Alto Orco"),;

    /**
     * Método toString que retorna el nombre del tipo de enemigo.
     *
     * @return El nombre del tipo de enemigo.
     */
    override fun toString(): String {
        return nombre
    }
}