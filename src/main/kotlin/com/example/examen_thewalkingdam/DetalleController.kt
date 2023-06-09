package com.example.examen_thewalkingdam
import BBDD.Conexion
import Utilidades.Datos
import Utilidades.Mensaje
import Zombies.Zombie
import Zombies.ZombieNormal
import Zombies.ZombiePodrido
import Zombies.ZombiePupas
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.Node
import javafx.scene.control.*
import javafx.scene.layout.AnchorPane
import javafx.stage.Stage
import java.net.URL
import java.util.*

class DetalleController:Initializable {

    var hayCambios:Boolean = false

    @FXML
    private lateinit var capInfecField: TextField

    @FXML
    private lateinit var checkSePuedenMover: CheckBox

    @FXML
    private lateinit var datosComunesPane: AnchorPane

    @FXML
    private lateinit var noRadio: RadioButton

    @FXML
    private lateinit var normalPane: AnchorPane

    @FXML
    private lateinit var podridosPane: AnchorPane

    @FXML
    private lateinit var pupasGroup: ToggleGroup

    @FXML
    private lateinit var pupasPane: AnchorPane

    @FXML
    private lateinit var siRadio: RadioButton

    @FXML
    private lateinit var tiempoInfecField: TextField

    @FXML
    private lateinit var velocidadField: TextField

    override fun initialize(p0: URL?, p1: ResourceBundle?) {
        if(Datos.tipo == 0){
            noRadio.isSelected = true
            velocidadField.text = Datos.zom!!.velocidad.toString()
            capInfecField.text = Datos.zom!!.capacidad.toString()
            tiempoInfecField.text = Datos.zom!!.tiempo.toString()
            if (Datos.zom is ZombiePodrido){
                if ((Datos.zom as ZombiePodrido).sePuedeMover == 1){
                    checkSePuedenMover.isSelected = true
                }else{
                    checkSePuedenMover.isSelected = false
                }
            }else if (Datos.zom is ZombiePupas){
                siRadio.isSelected = true
            }else if (Datos.zom is ZombieNormal){
               println("Es un zombie normal")
            }

        }else{
            println("has seleccionado un personaje")
        }

        capInfecField.textProperty().addListener { _, _, _ -> cambios() }
        tiempoInfecField.textProperty().addListener { _, _, _ -> cambios() }
        velocidadField.textProperty().addListener { _, _, _ -> cambios() }

        // Listener para el checkbox
        checkSePuedenMover.selectedProperty().addListener { _, _, _ -> cambios() }

        // Listener para los radio buttons
        noRadio.selectedProperty().addListener { _, _, _ -> cambios() }
        siRadio.selectedProperty().addListener { _, _, _ -> cambios() }
    }


    fun cambios(){
        hayCambios = true
    }


    @FXML
    fun guardarButton(event: ActionEvent) {
        guardarCambios()
    }


    @FXML
    fun volverButton(event: ActionEvent) {
        if (hayCambios == true){
            if(Mensaje.alerta("Ha habido cambios","¿Quieres salir sin sin guardar?") == ButtonType.YES){
                val source: Node = event!!.source as Node
                val stage = source.scene.window as Stage
                stage.close()
            }else{
                guardarCambios()
            }
        }else{
            val source: Node = event!!.source as Node
            val stage = source.scene.window as Stage
            stage.close()
        }
    }

    fun guardarCambios(){
        var zom:Zombie? = Datos.zom

        if (hayCambios == false){
            Mensaje.informacion("No se han realizado cambios","Pulse para continuar")
        }else{
            if (zom is ZombieNormal){
                zom.velocidad = velocidadField.text.toInt()
                zom.capacidad = capInfecField.text.toInt()
                zom.tiempo = tiempoInfecField.text.toInt()
            }
            else if (zom is ZombiePupas){
                zom.velocidad = velocidadField.text.toInt()
                zom.capacidad = capInfecField.text.toInt()
                zom.tiempo = tiempoInfecField.text.toInt()
            }
            else if (zom is ZombiePodrido){
                zom.velocidad = velocidadField.text.toInt()
                zom.capacidad = capInfecField.text.toInt()
                zom.tiempo = tiempoInfecField.text.toInt()

                if (checkSePuedenMover.isSelected){
                    zom.sePuedeMover = 1
                }else{
                    zom.sePuedeMover = 0
                }
            }
            Datos.zom = zom
            Conexion.actualiarZombieAuto(Datos.zom!!)
            Mensaje.informacion("Cambios realizados","pulse para continuar")
            hayCambios = false
        }
    }



}
