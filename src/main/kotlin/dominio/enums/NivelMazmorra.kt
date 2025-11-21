package dominio.enums

/**
 * Enum que representa los diferentes niveles de dificultad de las mazmorras.
 * Cada nivel tiene un nombre asociado y un número de ataques relacionados con su dificultad.
 */
enum class NivelMazmorra(private val nombre:String, val numAtaques: Int) {
    FACIL("Facil",1),
    MEDIO("Medio",2),
    DIFICIL("Difici",2),
    IMPOSIBLE("Imposible",3),
    GUNTER("Gunter",3);

    /**
     * Método toString que retorna el nombre del nivel de mazmorras.
     *
     * @return El nombre del nivel de la mazmorra.
     */
    override fun toString(): String {
        return nombre
    }
}