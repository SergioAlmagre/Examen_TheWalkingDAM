package com.example.examen_thewalkingdam

import BBDD.Conexion
import Escenario.Mapa
import Personaje.Personaje
import Utilidades.Datos
import Zombies.Zombie
import kotlin.random.Random

class Juego {
    var mapa: Mapa = Mapa()
    var allZombies:ArrayList<Zombie> = ArrayList()
    var allPersonaje:ArrayList<Personaje> = Conexion.obtenerPersonajes()
    var sophie:Personaje? = null
    var nombreEstrella = "Sophie"
    var zombiesVivos:Int = 0
    var pausado:Boolean = false
    var encontrada:Boolean = false
    var personajesElegidos:ArrayList<Personaje> = ArrayList()
    var mensajesJuego = ""

    init {
        unirTodosLosZombies()
        sophie = Conexion.obtenerSophia()
    }

    override fun toString(): String {
        return "Juego(mapa=$mapa, allZombies=$allZombies, allPersonaje=$allPersonaje, shophie=$sophie, zombiesVivos=$zombiesVivos, pausado=$pausado)"
    }

    fun unirTodosLosZombies():ArrayList<Zombie>{
        allZombies.addAll(Conexion.obtenerZombiesNormales()!!)
        allZombies.addAll(Conexion.obtenerZombiesPupas()!!)
        allZombies.addAll(Conexion.obtenerZombiesPodridos()!!)
        var todos = allZombies
        return todos
    }

    fun popZombie():Zombie?{
        var zom: Zombie? = null
        var extraido:Boolean = false
        do {
            if (allZombies!!.isNotEmpty()) {
                zom = allZombies!!.get(0)
                extraido = true
                allZombies!!.removeFirst()
            } else {
//                println("cola vacia")
                allZombies = unirTodosLosZombies()
            }
        }while (!extraido)
        return zom
    }

    fun objenerPersonaje():Personaje?{
        var p: Personaje? = null
        var extraido:Boolean = false
        var max = Conexion.totalPersonajes() -1
        try {
            do {
                var num = Random.nextInt(0, max)
                if (allPersonaje!!.isNotEmpty()) {
                    p = allPersonaje!!.get(num)
                    if (p.nombre != nombreEstrella) {
                        extraido = true
                    }
                } else {
//                    println("cola vacia")
                    allPersonaje = Conexion.obtenerPersonajes()
                }
            } while (!extraido)
        }catch (e:Exception){
            Datos.gestionErrores(e,"fallo en obtenerPersonaje")
        }
        return p
    }


    fun colocarObjeto(objeto:Any){
        var posicion = posicionAzar()
        var fil = posicion[0]
        var col = posicion[1]
        var colocado = false
        var contador = 1024
        do {
            if(esPosicionValida(fil,col)){
                if (esPosicionLibre(fil,col)){
                    mapa.setPosicion(fil,col,objeto!!)
                    colocado = true
                    if(objeto is Zombie){
                        zombiesVivos++
                    }
                }else{
                    posicion = posicionAzar()
                }
            }else{
                posicion = posicionAzar()
            }
            fil = posicion[0]
            col = posicion[1]
            contador--
        }while(!colocado && contador != 0)
        if (contador == 0){
//            println("no hay supuestos huecos libres")
        }
    }

// MOVER TAMBIÉN IMPLEMENTA LAS FUNCIONES DE RECONOCIMIENTO DE CASILLAS CONTIGUAS
    fun mover(){
        var objetoA: Any? = null
        var objetoB: Any? = null
        var esValida = false
        var nFil = 0
        var nCol = 0

        try {
            for (f in 0..mapa.filas()-1) {
                for (c in 0..mapa.columnas()-1) {

                    objetoA = mapa.getPosicion(f, c)

                    if (objetoA is Personaje && objetoA.nombre != nombreEstrella) {
                        do {
                            var esIgual = false
                            var nPosiciones = obtenerPosicionMovimiento()
                            nFil = nPosiciones[0] + f
                            nCol = nPosiciones[1] + c
                            esValida = esPosicionValida(nFil, nCol)
                            if (nFil == f && nCol == c) {
                                esIgual = true
                            }
                        } while (!esValida || esIgual)

                        objetoB = mapa.getPosicion(nFil, nCol)

                        if (objetoA != null) {
                            if (objetoB == null) {
                                mapa.setPosicion(nFil, nCol, objetoA)
                                mapa.setPosicion(f, c, null)
                            } else {
                                if (objetoB is Zombie){
                                    if (objetoA.municion > 0){
                                        mapa.setPosicion(nFil,nCol,objetoA)
                                        mapa.setPosicion(f,c,null)
                                        zombiesVivos--
                                        objetoA.municion--
                                        mensajesJuego =  "${objetoA.nombre} elimina a $objetoB \n"
                                    }else{
                                        mensajesJuego = "$objetoA no tiene munción y un zombie le ha soplado la oreja"
                                    }
                                }else if(objetoB is Personaje){
                                    if (objetoB.nombre == nombreEstrella){
                                        mensajesJuego = "${objetoA.nombre} ha encontrado a $nombreEstrella"
                                        encontrada = true
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }catch (e:Exception){
            Datos.gestionErrores(e,"mover")
        }
    }


    fun obtenerPosicionMovimiento():ArrayList<Int>{
        var posiciones = ArrayList<Int>()

        var fil = Random.nextInt(-1,2)
        var col = Random.nextInt(-1,2)

        posiciones.add(fil)
        posiciones.add(col)

        return  posiciones
    }

    fun posicionAzar():ArrayList<Int>{
        var posiciones = ArrayList<Int>()

        var fil = Random.nextInt(0,mapa.filas())
        var col = Random.nextInt(0,mapa.columnas())

        posiciones.add(fil)
        posiciones.add(col)

        return  posiciones
    }

    fun esPosicionValida(fil:Int, col:Int):Boolean{
        var esValida = false
        if(fil >= 0 && fil <= mapa.filas()-1 && col >= 0 && col <= mapa.columnas()-1){
                esValida = true
        }
        return esValida
    }

    fun esPosicionLibre(fil:Int, col:Int):Boolean {
        var esLibre: Boolean = false
            if (mapa.getPosicion(fil, col) == null) {
                esLibre = true
            }
            return esLibre
    }

    fun convertirPersonajeAZombie(idPersonaje:String){
        for (f in 0..mapa.filas()-1){
            for (c in 0..mapa.columnas()-1){
                if (mapa.getPosicion(f,c) is Personaje){
                    if ((mapa.getPosicion(f,c) as Personaje).id == idPersonaje){
                        mapa.setPosicion(f,c,colocarObjeto(popZombie()!!))
                    }
                }
            }
        }

    }



}

