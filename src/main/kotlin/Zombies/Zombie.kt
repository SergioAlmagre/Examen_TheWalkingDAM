package Zombies

import Interfaces.HabilidadesZombies

open class Zombie:HabilidadesZombies {
    var id:String
    var velocidad:Int
    var capacidad:Int
    var tiempo:Int

    constructor(id: String, velocidad: Int, capacidad: Int, tiempo: Int) {
        this.id = id
        this.velocidad = velocidad
        this.capacidad = capacidad
        this.tiempo = tiempo
    }

    override fun toString(): String {
        return "Z."
    }


}