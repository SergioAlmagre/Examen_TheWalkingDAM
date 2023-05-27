package Utilidades

import BBDD.Conexion
import Zombies.Zombie
import java.io.File
import java.io.FileWriter
import java.sql.Time
import java.time.LocalDate
import java.time.LocalTime
import java.util.Date

object Datos {

    var zombiesJugando = 0
    var zom:Zombie? = null


    fun gestionErrores(ex:Exception, info:String){
        var hora = LocalTime.now().toString().substring(0,8)
        var dia = LocalDate.now().toString()
        var mensaje = hora +" - "+ dia + " " + ex.toString() + " - " + info
        var archivo = FileWriter("Bitacora_de_errores.log",true)
        archivo.write(mensaje)
        archivo.close()
        Conexion.cod = -1
        println(mensaje)
    }





}