package dominio.entidades

/**
 * Clase `EspadaRota` que representa un arma en mal estado dentro del juego.
 * Hereda de la clase `Objeto` y proporciona una mejora de ataque al ser usada.
 */
class EspadaRota: Objeto("Espada Rota") {

    /**
     * Método que permite al personaje equiparse con la espada rota.
     * Si el arma ya ha sido usada antes, la mejora es menor.
     *
     * @param personaje El personaje que usará la espada.
     */
    override fun usar(personaje: Personaje) {
        if (nombre in listaObjetosUsados){
            personaje.ataque += 1
            println("${personaje.nombre} mejora la espada rota ya usada, el ataque sube 1 punto.")

        }else{
            personaje.ataque += 2
            println("${personaje.nombre} se equipa con la espada rota, el ataque sube 2 puntos.")

        }
        this.usado()
        eliminarInventario(personaje,this)
    }
}