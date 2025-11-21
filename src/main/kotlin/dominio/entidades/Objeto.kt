package dominio.entidades

/**
 * Clase abstracta que representa un objeto en el juego.
 *
 * @property nombre Nombre del objeto.
 * @property usado Indica si el objeto ya ha sido utilizado (por defecto, `false`).
 */
abstract class Objeto(val nombre: String, private var usado: Boolean = false) {

    /**
     * Método abstracto que define la funcionalidad del objeto al ser usado por un personaje.
     *
     * @param personaje El personaje que usará el objeto.
     */
    abstract fun usar(personaje: Personaje)

    /**
     * Marca el objeto como usado si no ha sido registrado antes en la lista de objetos usados.
     */
    fun usado() {
        if (listaObjetosUsados.none { it == this.nombre }) {
            this.usado = true
            listaObjetosUsados.add(this.nombre)
        }
    }

    /**
     * Elimina el objeto del inventario del personaje después de ser usado.
     *
     * @param personaje El personaje cuyo inventario se modificará.
     * @param objeto Objeto a eliminar del inventario.
     */
    fun eliminarInventario(personaje: Personaje, objeto: Objeto) {
        personaje.inventario.remove(objeto)
    }

    /**
     * Representación en cadena del objeto, devuelve su nombre.
     *
     * @return El nombre del objeto.
     */
    override fun toString(): String {
        return nombre
    }

    companion object {
        val listaObjetosUsados = mutableListOf<String>()

        /**
         * Método privado que genera una cadena con los objetos usados y sus índices.
         *
         * @param listaObjetosUsados Lista de nombres de objetos que han sido usados.
         */
        private fun objetosUsados(listaObjetosUsados: MutableList<String>) {
            var cadena = ""
            for (i in listaObjetosUsados.indices){
                for (objeto in listaObjetosUsados) {
                    cadena += "$i -> $objeto\n\t"
                }
            }
        }
    }
}
