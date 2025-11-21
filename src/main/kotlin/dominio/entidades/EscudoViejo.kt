package dominio.entidades

/**
 * Clase `EscudoViejo` que representa un escudo viejo en el juego.
 * Hereda de la clase `Objeto` y proporciona una mejora de armadura cuando se usa.
 */
class EscudoViejo: Objeto("Escudo viejo") {

    /**
     * Método que permite al personaje usar el escudo viejo.
     * Si el escudo ya ha sido usado previamente, proporciona una mejora menor.
     *
     * @param personaje El personaje que usará el escudo.
     */
    override fun usar(personaje: Personaje) {
        if (nombre in listaObjetosUsados){
            personaje.armadura += 1
            println("${personaje.nombre} mejora el escudo viejo ya usado, la armadura sube 1 punto.")

        } else{
            personaje.armadura += 2
            println("${personaje.nombre} se equipa con el escudo viejo, la armadura sube 2 puntos.")

        }
        this.usado()
        eliminarInventario(personaje,this)
    }
}