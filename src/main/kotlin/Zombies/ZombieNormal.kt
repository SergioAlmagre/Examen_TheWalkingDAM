package Zombies

class ZombieNormal:Zombie {
    constructor(id: String, velocidad: Int, capacidad: Int, tiempo: Int) : super(id, velocidad, capacidad, tiempo)

    override fun toString(): String {
        return "${super.toString()} normal"
    }


}