package BBDD

import Personaje.Personaje
import Utilidades.Datos
import Zombies.Zombie
import Zombies.ZombieNormal
import Zombies.ZombiePodrido
import Zombies.ZombiePupas
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Statement
import kotlin.random.Random

object Conexion {
    // ********************* Atributos *************************
    var conexion: Connection? = null

    // Atributo a través del cual hacemos la conexión física.
    var sentenciaSQL: Statement? = null

    // Atributo que nos permite ejecutar una sentencia SQL
    var registros: ResultSet? = null

    var mensaje = ""
    var cod = 0


    fun abrirConexion(): Int {
        var cod = 0
        try {
            // Cargar el driver/controlador JDBC de MySql
            val controlador = "com.mysql.cj.jdbc.Driver"
            val URL_BD = "jdbc:mysql://" + Constantes.servidor + ":" + Constantes.puerto + "/" + Constantes.bbdd
            Class.forName(controlador)

            // Realizamos la conexión a una BD con un usuario y una clave.
            conexion = DriverManager.getConnection(URL_BD, Constantes.usuario, Constantes.passwd)
            sentenciaSQL = conexion!!.createStatement()
            println("Conexion realizada con éxito")
        } catch (e: Exception) {
            Datos.gestionErrores(e,"Fallo en abrir conexión")
        }
        return cod
    }

    fun cerrarConexion(): Int {
        var cod = 0
        try {
            conexion!!.close()

        } catch (e: Exception) {
            Datos.gestionErrores(e,"Fallo en cerrar conexión")
        }
        return cod
    }

    fun obtenerZombiesNormales():ArrayList<Zombie>?{
        var todos:ArrayList<Zombie> = ArrayList()
        var zom:Zombie? = null
        var sentencia = "Select zombies.id_zombie,velocidad, capacidad, tiempo from zombies join normales on normales.id_zombie = zombies.id_zombie"
        try {

            abrirConexion()
            var pstmt = conexion!!.prepareStatement(sentencia)
            registros = pstmt.executeQuery()
            while(registros!!.next()){
                zom = ZombieNormal(
                    registros!!.getString(1),
                    registros!!.getInt(2),
                    registros!!.getInt(3),
                    registros!!.getInt(4),
                )
                todos.add(zom)
            }
            cerrarConexion()
        }catch (e:Exception){
            Datos.gestionErrores(e,sentencia)
        }
        return todos
    }
    fun obtenerZombiesPupas():ArrayList<Zombie>?{
        var todos:ArrayList<Zombie> = ArrayList()
        var zom:Zombie? = null
        var sentencia = "Select zombies.id_zombie,velocidad, capacidad, tiempo, completos from zombies join pupas on pupas.id_zombie = zombies.id_zombie"
        try {
            abrirConexion()
            var pstmt = conexion!!.prepareStatement(sentencia)
            registros = pstmt.executeQuery()
            while(registros!!.next()){
                zom = ZombiePupas(
                    registros!!.getString(1),
                    registros!!.getInt(2),
                    registros!!.getInt(3),
                    registros!!.getInt(4),
                    registros!!.getInt(5)
                )
                todos.add(zom)
            }
            cerrarConexion()
        }catch (e:Exception){
            Datos.gestionErrores(e,sentencia)
        }
        return todos
    }
    fun obtenerZombiesPodridos():ArrayList<Zombie>?{
        var todos:ArrayList<Zombie> = ArrayList()
        var zom:Zombie? = null
        var sentencia = "Select zombies.id_zombie,velocidad, capacidad, tiempo, movilidad from zombies join podridos on podridos.id_zombie = zombies.id_zombie"
        try {
            abrirConexion()
            var pstmt = conexion!!.prepareStatement(sentencia)
            registros = pstmt.executeQuery()
            while(registros!!.next()){
                zom = ZombiePodrido(
                    registros!!.getString(1),
                    registros!!.getInt(2),
                    registros!!.getInt(3),
                    registros!!.getInt(4),
                    registros!!.getInt(5)
                )
                todos.add(zom)
            }
            cerrarConexion()
        }catch (e:Exception){
            Datos.gestionErrores(e,sentencia)
        }
        return todos
    }


    fun obtenerPersonajes():ArrayList<Personaje>{
        var allPersonajes = ArrayList<Personaje>()
        var per: Personaje? = null
        var sentencia = "select * from personajes"
        try {
            abrirConexion()
            var pstmt = conexion!!.prepareStatement(sentencia)
            registros = pstmt.executeQuery()
            while(registros!!.next()){
                per = Personaje(
                    registros!!.getString(1),
                    registros!!.getInt(2),
                    registros!!.getInt(3),
                    registros!!.getString(4)
                )
                allPersonajes.add(per)
            }
            cerrarConexion()
        }catch (e:Exception){
            Datos.gestionErrores(e,sentencia)
        }
        return allPersonajes
    }

    fun obtenerSophia():Personaje?{
        var so:Personaje? = null
        var sentencia = "select * from personajes where id_personaje = 8"
        try {
            abrirConexion()
            var pstmt = conexion!!.prepareStatement(sentencia)
            registros = pstmt.executeQuery()
            if (registros!!.next()){
                so = Personaje(
                    registros!!.getString(1),
                    registros!!.getInt(2),
                    registros!!.getInt(3),
                    registros!!.getString(4)
                )
            }
            cerrarConexion()
        }catch (e:Exception){
            Datos.gestionErrores(e,sentencia)
        }
        return so
    }








}