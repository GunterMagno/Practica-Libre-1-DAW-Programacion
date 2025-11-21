package dominio.entidades

/**
 * Clase que actúa como una fábrica de objetos en el juego.
 * Permite crear objetos a partir de su nombre y generar listas de objetos desde una lista de nombres.
 */
class CreadorObjetos {
    companion object {
        // Lista de objetos disponibles en el juego.
        // Esta lista se usa como base para buscar y crear objetos a partir de su nombre.
        private val objetosDisponibles = listOf(
            Pocion(),
            PocionGrande(),
            PocionExperiencia(),
            PocionExperienciaGrande(),
            EspadaRota(),
            Espadon(),
            EscudoViejo(),
            EscudoHierro()
        )

        /**
         * Busca un objeto en la lista de objetos disponibles basado en su nombre.
         *
         * @param nombre El nombre del objeto a buscar.
         * @return El objeto correspondiente si se encuentra en la lista, o `null` si no existe.
         */
        fun crearDesdeNombre(nombre: String): Objeto? {
            return objetosDisponibles.find { it.nombre == nombre }
        }

        /**
         * Crea una lista de objetos a partir de una lista de nombres.
         *
         * @param nombres Lista de nombres de objetos a buscar.
         * @return Lista de objetos encontrados. Si un nombre no coincide con ningún objeto, se omite.
         */
        fun crearListaDesdeNombres(nombres: List<String>): List<Objeto> {
            return nombres.mapNotNull { crearDesdeNombre(it) }
        }
    }
}