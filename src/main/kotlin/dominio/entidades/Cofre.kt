package dominio.entidades

/**
 * Clase genérica `Cofre<T>` que representa un cofre en el juego.
 * Puede contener cualquier tipo de objeto, incluyendo listas de objetos.
 *
 * @param T Tipo de contenido almacenado en el cofre (puede ser un objeto único o una lista de objetos).
 * @property contenido El contenido del cofre.
 */
class Cofre<T>(private val contenido: T) {

    /**
     * Método para abrir el cofre y revelar su contenido.
     *
     * @return El contenido del cofre, que puede ser un objeto individual o una lista de objetos.
     */
    fun abrir(): T {
        println("Has encontrado los siguientes objetos en el cofre:")

        if (contenido is List<*>) {
            for (objeto in contenido) {
                println("\t$objeto")
            }
        }else { println("\n\t$contenido") }

        return contenido
    }
}
