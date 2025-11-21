package dominio.entidades

import servicios.Combatiente
/**
 * Clase que representa un personaje en el juego.
 *
 * @property nombre El nombre del personaje.
 * @property vidaMaxima La vida máxima del personaje.
 * @property ataque El valor de ataque del personaje.
 * @property armadura El valor de armadura del personaje.
 * @property nivel El nivel actual del personaje.
 * @property experiencia La experiencia acumulada del personaje.
 * @property inventario Lista de objetos en el inventario del personaje.
 * @property enemigosEliminados Número de enemigos eliminados por el personaje.
 * @property bossesEliminados Número de bosses eliminados por el personaje.
 * @property mazmorrasLimpiadas Lista de mazmorras limpiadas por el personaje.
 * @property rondasMazmorra Número de rondas de mazmorra completadas.
 */
class Personaje private constructor(nombre: String,
                vidaMaxima: Int,
                ataque: Int,
                armadura: Int,
                nivel: Int = 1,
                experiencia: Int = 0
                ): Entidad(nombre,vidaMaxima,ataque,armadura,nivel,experiencia), Combatiente {


    val inventario = mutableListOf<Objeto>()
    var enemigosEliminados = 0
    var bossesEliminados = 0
    var mazmorrasLimpiadas = mutableListOf<String>()
    var rondasMazmorra = 1

    /**
     * Ataca a un enemigo de la lista proporcionada.
     *
     * @param enemigos Lista de enemigos disponibles para atacar.
     * @return El enemigo atacado, o `null` si no se pudo atacar.
     */
    override fun atacar(enemigos: List<Entidad>): Entidad? {
        val objetivo = elegirEnemigo(enemigos,false)

        if (objetivo != null) {
            println("\n$nombre a atacado a ${objetivo.nombre} le ha infligido ${objetivo.recibirAtaque(ataque)} de daño.")
            println("A ${objetivo.nombre} le quedan ${objetivo.vida} puntos de vida.")
            return objetivo
        }else{
            println("No se a podido atacar a ningun enemigo.")
            return null
        }
    }

    /**
     * Permite al personaje usar un objeto del inventario.
     *
     * @param inventario Lista de objetos disponibles en el inventario.
     * @return `true` si el objeto se usó correctamente, `false` si no se pudo usar.
     */
    fun usarObjeto(inventario: MutableList<Objeto>): Boolean{
        if (inventario.isEmpty()) {
            println("No tienes objetos disponibles.")
            return false
        }

        println("Elige un objeto para usar: ")
        inventario.forEachIndexed { index, obj ->
            println("${index + 1}. ${obj.nombre}")
        }

        print("-> ")
        val opcion = readlnOrNull()?.toIntOrNull()
        val objeto: Objeto

        if (opcion != null && opcion in 1..inventario.size) {
            objeto = inventario.removeAt(opcion - 1)
        } else {
            println("Selección inválida.")
            return false
        }
        objeto.usar(this)
        return true
    }

    /**
     * Sube de nivel al personaje si tiene suficiente experiencia.
     */
    fun subirNivel(){
        if (experiencia >= 100){
            experiencia -= 100
            nivel++
            println("$nombre a subido a nivel $nivel")
            if (nivel % 2 == 0){
                vidaMaxima += 5
                println("Al subir a nivel $nivel la vida maxima a subido a $vidaMaxima")
            }
            vida += 10
            println("Al subir de nivel te curas 10 puntos de vida. $nombre tiene $vida puntos de vida.")
        }
    }

    companion object {
        /**
         * Crea una instancia de `Personaje` con valores predeterminados.
         *
         * @param nombre El nombre del personaje.
         * @return Una nueva instancia de `Personaje`.
         */
        fun crearInstancia(nombre: String): Personaje {
            return Personaje(nombre, vidaMaxima = 20, ataque = 10, armadura = 5)
        }
    }

    /**
     * Muestra el inventario del personaje en formato de cadena.
     */
    private fun mostrarInventario(){
        var cadena = "\t\t"
        for (i in inventario.indices){
            for (objeto in inventario) {
                cadena += "$i -> $objeto\n\t\t"
            }
        }
    }

    /**
     * Devuelve una representación en cadena del personaje.
     *
     * @return Una cadena con los detalles del personaje.
     */
    override fun toString(): String {
        return "Personaje $nombre, datos:\n\tVida: $vida\n\tVida maxima: $vidaMaxima\n\tAtaque: $ataque\n\tArmadura: $armadura\n\tNivel: $nivel\n\tExperiencia: $experiencia\n\tInventario: $inventario\n\tEnemigos eliminados: $enemigosEliminados\n\tBosses eliminados: $bossesEliminados\n\tMazmoras limpiadas: $mazmorrasLimpiadas"
    }
}