package dominio.entidades

import dominio.enums.NivelEnemigo
import dominio.enums.NivelMazmorra
import dominio.enums.TipoEnemigo
import kotlin.random.Random

/**
 * Representa una mazmorra en el juego, que contiene enemigos y puede generar cofres con recompensas.
 * Se crea con un nombre y un nivel de dificultad.
 *
 * @property nombre Nombre de la mazmorra.
 * @property nivel Nivel de dificultad de la mazmorra.
 */
class Mazmorra private constructor(val nombre: String, val nivel: NivelMazmorra) {

    private var enemigos = mutableListOf<Enemigo>()

    init {
        generarEnemigos()
    }

    /**
     * Genera enemigos dentro de la mazmorra en función de su nivel de dificultad.
     * Define la cantidad de enemigos, su tipo y la probabilidad de aparición de un jefe.
     */
    private fun generarEnemigos() {
        val cantidadEnemigos: Int
        val bossProbabilidad: Double
        val tipoEnemigos: List<TipoEnemigo>
        val nivelEnemigos: NivelEnemigo

        when (nivel) {
            NivelMazmorra.FACIL -> {
                cantidadEnemigos = (1..2).random()
                bossProbabilidad = 0.1
                tipoEnemigos = listOf(TipoEnemigo.GOBLIN)
                nivelEnemigos = NivelEnemigo.COMUN
            }
            NivelMazmorra.MEDIO -> {
                cantidadEnemigos = (2..3).random()
                bossProbabilidad = 0.2
                tipoEnemigos = listOf(TipoEnemigo.GOBLIN, TipoEnemigo.ESQUELETO)
                nivelEnemigos = NivelEnemigo.INTERMEDIO
            }
            NivelMazmorra.DIFICIL -> {
                cantidadEnemigos = (3..4).random()
                bossProbabilidad = 0.3
                tipoEnemigos = listOf(TipoEnemigo.ESQUELETO, TipoEnemigo.ORCO)
                nivelEnemigos = NivelEnemigo.ELITE
            }
            NivelMazmorra.IMPOSIBLE -> {
                cantidadEnemigos = (4..5).random()
                bossProbabilidad = 0.5
                tipoEnemigos = listOf(TipoEnemigo.ORCO, TipoEnemigo.ALTO_ORCO)
                nivelEnemigos = NivelEnemigo.ELITE
            }

            NivelMazmorra.GUNTER -> {
                cantidadEnemigos = (5..10).random()
                bossProbabilidad = 0.8
                tipoEnemigos = listOf(TipoEnemigo.ALTO_ORCO)
                nivelEnemigos = NivelEnemigo.GUNTER
            }
        }

        repeat(cantidadEnemigos) { _ ->
            val esBoss = (Math.random() < bossProbabilidad * (bossProbabilidad * 2))

            val enemigo = if (esBoss) {
                val tipoElegido = tipoEnemigos.last()
                Enemigo("Boss $tipoEnemigos de $tipoElegido", true, tipoElegido, nivelEnemigos)
            } else {
                val tipoElegido = tipoEnemigos.random()
                Enemigo(tipoElegido.toString(), false, tipoElegido, nivelEnemigos)
            }
            enemigos.add(enemigo)
        }
    }

    /**
     * Obtiene una lista de los enemigos que aún tienen vida dentro de la mazmorra.
     *
     * @return Lista de enemigos vivos.
     */
    fun obtenerEnemigos(): List<Enemigo> {
        return enemigos.filter { it.vida > 0 }
    }

    /**
     * Elimina los enemigos que han sido derrotados dentro de la mazmorra.
     */
    fun eliminarEnemigosDerrotados() {
        val eliminados = enemigos.filter { it.vida <= 0 }
        enemigos.removeAll(eliminados)

        if (enemigos.size == 1 && enemigos[0].vida == 0 ){
            enemigos = mutableListOf()
        }
    }

    /**
     * Vacía la mazmorra eliminando todos los enemigos.
     */
    fun limpiarMazmorra() {
        enemigos.clear()
    }

    /**
     * Genera un cofre con objetos aleatorios como recompensa tras limpiar la mazmorra.
     *
     * @return Cofre con una lista de objetos.
     */
    fun generarCofre(): Cofre<MutableList<Objeto>> {
        val recompensas = listOf(
            Pocion(),
            Pocion(),
            Pocion(),
            EspadaRota(),
            EscudoViejo(),
            PocionGrande(),
            PocionGrande(),
            PocionExperiencia(),
            PocionExperiencia(),
            PocionExperiencia(),
            PocionExperienciaGrande(),
            Espadon(),
            EscudoHierro()
        )

        val recompensasObtenidas = mutableListOf<Objeto>()
        repeat(Random.nextInt(1, 3)) {
            recompensasObtenidas.add(recompensas.random())
        }

        val cofre = Cofre(recompensasObtenidas)

        return cofre
    }

    companion object {

        /**
         * Crea una instancia de `Mazmorra` con un nombre y nivel de dificultad.
         *
         * @param nombre Nombre de la mazmorra.
         * @param nivel Nivel de la mazmorra.
         * @return Nueva instancia de Mazmorra.
         */
        fun crearInstancia(nombre: String,nivel: NivelMazmorra): Mazmorra {
            return Mazmorra(nombre,nivel)
        }

        /**
         * Obtiene un nombre aleatorio para una mazmorra de una lista predefinida.
         *
         * @return Nombre de mazmorra seleccionado aleatoriamente.
         */
        fun obtenerNombreMazmorra(): String {
            val nombresUsados = mutableListOf<String>()
            val nombresMazmorras = mutableListOf(
                "Cueva Oscura",
                "Templo del Olvido",
                "Fortaleza Abandonada",
                "Bosque Maldito",
                "Ruinas de la Esperanza",
                "La Tumba del Rey Caído",
                "Mazmorras del Infierno",
                "Caverna del Dragón",
                "El Laberinto de los Vientos",
                "La Torre de Cristal"
            )

            if (nombresMazmorras.isEmpty()) {
                nombresMazmorras.addAll(nombresUsados)
                nombresUsados.clear()
            }

            val nombre = nombresMazmorras.random()

            nombresMazmorras.remove(nombre)
            nombresUsados.add(nombre)

            return nombre
        }

        /**
         * Determina el nivel de la mazmorra en función del número de rondas jugadas.
         *
         * @param rondas Número de rondas jugadas.
         * @return Nivel de la mazmorra correspondiente.
         */
        fun obtenerNivelMazmorra(rondas: Int): NivelMazmorra {
            return when {
                rondas <= 5 -> NivelMazmorra.FACIL
                rondas in 6..10 -> NivelMazmorra.MEDIO
                rondas in 11..15 -> NivelMazmorra.DIFICIL
                rondas in 16..49 -> NivelMazmorra.IMPOSIBLE
                else -> {
                    NivelMazmorra.GUNTER
                }
            }
        }
    }

    /**
     * Representación en forma de cadena de la mazmorra.
     *
     * @return Cadena con el nombre, nivel y lista de enemigos.
     */
    override fun toString(): String {
        return "Mazmorra $nombre:\n\tNivel: $nivel\n\tEnemigos: $enemigos"
    }
}
