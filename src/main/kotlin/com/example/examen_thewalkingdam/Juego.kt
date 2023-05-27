package com.example.examen_thewalkingdam

import BBDD.Conexion
import Escenario.Mapa
import Personaje.Personaje
import Utilidades.Datos
import Zombies.Zombie
import kotlin.random.Random

class Juego {
    var mapa:Mapa = Mapa()
    var allZombies:ArrayList<Zombie> = ArrayList()
    var allPersonaje:ArrayList<Personaje> = ArrayList()
    var shophie:Personaje? = null
    var zombiesVivos:Int = 0
    var pausado:Boolean = false

    init {
        unirTodosLosZombies()
        shophie = Conexion.obtenerSophia()
    }


    override fun toString(): String {
        return "Juego(mapa=$mapa, allZombies=$allZombies, allPersonaje=$allPersonaje, shophie=$shophie, zombiesVivos=$zombiesVivos, pausado=$pausado)"
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
                println("cola vacia")
                allZombies = unirTodosLosZombies()
            }
        }while (!extraido)
        return zom
    }

    fun colocarZombie(){
        var posicion = posicionAzar()
        var fil = posicion[0]
        var col = posicion[1]
        var colocado = false
        var contador = 1024
        do {
            if(esPosicionValida(fil,col)){
                if (esPosicionLibre(fil,col)){
                    mapa.setPosicion(fil,col,popZombie()!!)
                    colocado = true
                    zombiesVivos++
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
            println("no hay huecos libres")
        }
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
                    zombiesVivos++
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
            println("no hay huecos libres")
        }
    }



    fun moverser():ArrayList<Int>{
        /**
         * 0-fila
         * 1-colummna
         */
        var posiciones = ArrayList<Int>()
        var fil = Random.nextInt(-1,1)
        posiciones.add(fil)
        var col = Random.nextInt(-1,1)
        posiciones.add(col)

        esPosicionLibre(fil,col)

        return  posiciones
    }

    fun posicionAzar():ArrayList<Int>{
        var posiciones = ArrayList<Int>()

        var fil = Random.nextInt(0,mapa.filas()+1)
        posiciones.add(fil)
        var col = Random.nextInt(0,mapa.columnas()+1)
        posiciones.add(col)

        return  posiciones
    }

    fun esPosicionValida(fil:Int, col:Int):Boolean{
        var esValida = false
        try{
            if (mapa.getPosicion(fil,col) == null || mapa.getPosicion(fil,col) != null){
                esValida = true
            }
        }catch (e:Exception){

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



    fun pelea(fil:Int, col:Int) {
        var z:Zombie? = null
        var p:Personaje? = null

        try {
             if (mapa.getPosicion(fil,col) is Zombie){
                 z = mapa.getPosicion(fil,col) as Zombie
             }
             else if(mapa.getPosicion(fil,col) is Personaje){
                 p = mapa.getPosicion(fil,col) as Personaje
                 }


        }catch (e:Exception){
            Datos.gestionErrores(e,"pelea")
        }

    }


}

