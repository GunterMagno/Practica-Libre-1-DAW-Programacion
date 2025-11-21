package dominio.entidades

/**
 * Clase `EscudoHierro` que representa un escudo de hierro en el juego.
 * Hereda de la clase `Objeto` y proporciona una mejora de armadura cuando se usa.
 */
class EscudoHierro: Objeto("Escudo de Hierro") {

    /**
     * Método que permite al personaje usar el escudo de hierro.
     * Si el escudo ya ha sido usado previamente, proporciona una mejora menor.
     *
     * @param personaje El personaje que usará el escudo.
     */
    override fun usar(personaje: Personaje) {
        if (nombre in listaObjetosUsados){
            personaje.armadura += 2
            println("${personaje.nombre} mejora el escudo de hierro ya usado, la armadura sube 2 puntos.")

        } else {
            personaje.armadura += 5
            println("${personaje.nombre} se equipa con el escudo de hierro, la armadura sube 5 puntos.")

        }
        this.usado()
        eliminarInventario(personaje, this)
    }
}