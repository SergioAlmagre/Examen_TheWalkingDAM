package Zombies

class ZombiePodrido:Zombie {
    var sePuedeMover:Int

    constructor(id: String, velocidad: Int, capacidad: Int, tiempo: Int, sePuedeMover: Int) : super(
        id,
        velocidad,
        capacidad,
        tiempo
    ) {
        this.sePuedeMover = sePuedeMover
    }

    override fun toString(): String {
        return "${super.toString()} podrido"
    }


}