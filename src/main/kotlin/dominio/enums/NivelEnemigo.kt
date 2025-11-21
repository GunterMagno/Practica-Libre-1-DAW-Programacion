package dominio.enums

/**
 * Enum que representa los diferentes niveles de dificultad de los enemigos.
 * Cada nivel tiene un nombre asociado que se utiliza para su identificación.
 */
enum class NivelEnemigo(private val nombre:String) {
    COMUN("Común"),
    INTERMEDIO("Intermedio"),
    ELITE("Elite"),
    JEFE("Jefe"),
    GUNTER("Gunter");

    /**
     * Método toString que retorna el nombre del nivel de enemigo.
     *
     * @return El nombre del nivel del enemigo.
     */
    override fun toString(): String {
        return nombre
    }
}