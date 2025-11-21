package dominio.entidades

import dominio.enums.NivelEnemigo
import dominio.enums.TipoEnemigo
import servicios.Combatiente

/**
 * Clase que representa un enemigo dentro del juego.
 * Un enemigo tiene diferentes características según su tipo y nivel.
 *
 * @param nombre Nombre del enemigo.
 * @param esBoss Indica si el enemigo es un jefe (boss).
 * @param tipoEnemigo Tipo del enemigo (Goblin, Esqueleto, Orco, Alto Orco).
 * @param nivelEnemigo Nivel del enemigo (Común, Intermedio, Élite, Jefe, Gunter).
 */
class Enemigo(nombre: String,
              val esBoss: Boolean,
              tipoEnemigo: TipoEnemigo,
              nivelEnemigo: NivelEnemigo
              ): Entidad(nombre, 0, 0, 0, 0,0), Combatiente {

    init {
        this.experiencia = 0

        when (tipoEnemigo) {
            TipoEnemigo.GOBLIN -> {
                vidaMaxima = 20
                ajustarVida()
                ataque = 5
                armadura = 3
                experiencia = 10
            }
            TipoEnemigo.ESQUELETO -> {
                vidaMaxima = 40
                ajustarVida()
                ataque = 10
                armadura = 2
                experiencia = 15
            }
            TipoEnemigo.ORCO -> {
                vidaMaxima = 70
                ajustarVida()
                ataque = 15
                armadura = 8
                experiencia = 20
            }
            TipoEnemigo.ALTO_ORCO -> {
                vidaMaxima = 100
                ajustarVida()
                ataque = 20
                armadura = 10
                experiencia = 40
            }
        }

        when (nivelEnemigo) {
            NivelEnemigo.COMUN -> {
                vidaMaxima += 5
                ajustarVida()
                ataque += 2
                armadura += 1
                experiencia += 5
            }
            NivelEnemigo.INTERMEDIO -> {
                vidaMaxima += 15
                ajustarVida()
                ataque += 5
                armadura += 3
                experiencia += 10
            }
            NivelEnemigo.ELITE -> {
                vidaMaxima += 30
                ajustarVida()
                ataque += 10
                armadura += 5
                experiencia += 15
            }
            NivelEnemigo.JEFE -> {
                vidaMaxima += 50
                ajustarVida()
                ataque += 15
                armadura += 10
                experiencia += 20
            }
            NivelEnemigo.GUNTER -> {
                vidaMaxima += 70
                ajustarVida()
                ataque += 20
                armadura += 15
                experiencia += 50
            }
        }
    }

    /**
     * Método que permite al enemigo atacar a un objetivo de una lista de entidades.
     *
     * @param enemigos Lista de entidades enemigas.
     * @return La entidad que fue atacada.
     */
    override fun atacar(enemigos: List<Entidad>): Entidad? {
        val objetivo = elegirEnemigo(enemigos,true)

        if (objetivo != null) {
            val danioEfectivo = objetivo.recibirAtaque(ataque)

            println("\n$nombre a atacado a ${objetivo.nombre} le ha infligido $danioEfectivo de daño.")
            println("${objetivo.nombre} ha recibido $danioEfectivo de daño. Vida restante: ${objetivo.vida}")
        }
        return objetivo
    }

    /**
     * Método que determina si el enemigo deja caer un objeto al ser derrotado.
     * La probabilidad de drop varía según un número aleatorio.
     *
     * @return Un objeto que puede ser una poción, una espada rota, un escudo viejo, o una poción grande.
     *         Puede devolver `null` si el enemigo no deja caer nada.
     */
    fun dropObjeto(): Objeto? {
        val probabilidad = (1..100).random()
        return when {
            probabilidad <= 60 -> null
            probabilidad <= 80 -> Pocion()
            probabilidad <= 90 -> EspadaRota()
            probabilidad <= 99 -> EscudoViejo()
            else -> PocionGrande()
        }
    }
}