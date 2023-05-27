package Personaje

class Personaje {

    var id:String
    var vida:Int
    var municion:Int
    var nombre:String

    constructor(id: String, vida: Int, municion: Int, nombre: String) {
        this.id = id
        this.vida = vida
        this.municion = municion
        this.nombre = nombre
    }

    override fun toString(): String {
            return "P.$nombre"
    }


}