package Zombies

class ZombiePupas:Zombie {
    var estaIntacto:Int

    constructor(id: String, velocidad: Int, capacidad: Int, tiempo: Int, completo: Int) : super(
        id,
        velocidad,
        capacidad,
        tiempo
    ) {
        this.estaIntacto = completo
    }

    override fun toString(): String {
        return "${super.toString()} pupas"
    }


}