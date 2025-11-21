package dominio.entidades

/**
 * Clase `Espadon` que representa un arma poderosa dentro del juego.
 * Hereda de la clase `Objeto` y proporciona una mejora significativa de ataque al ser usada.
 */
class Espadon: Objeto("Espadon") {

    /**
     * Método que permite al personaje equiparse con el espadón.
     * Si el arma ya ha sido usada antes, la mejora es menor.
     *
     * @param personaje El personaje que usará el espadón.
     */
    override fun usar(personaje: Personaje) {
        if (nombre in listaObjetosUsados){
            personaje.ataque += 2
            println("${personaje.nombre} mejora el espadon ya usado, el ataque sube 2 puntos.")

        }else {
            personaje.ataque += 5
            println("${personaje.nombre} se equipa con el espadon, el ataque sube 5 puntos.")

        }
        this.usado()
        eliminarInventario(personaje,this)
    }
}