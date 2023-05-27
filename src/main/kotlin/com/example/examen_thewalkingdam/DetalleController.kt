package com.example.examen_thewalkingdam
import Utilidades.Datos
import Zombies.ZombieNormal
import Zombies.ZombiePodrido
import Zombies.ZombiePupas
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.Node
import javafx.scene.control.CheckBox
import javafx.scene.control.RadioButton
import javafx.scene.control.TextField
import javafx.scene.control.ToggleGroup
import javafx.scene.layout.AnchorPane
import javafx.stage.Stage
import java.net.URL
import java.util.*

class DetalleController:Initializable {

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





    }

    @FXML
    fun guardarButton(event: ActionEvent) {


    }

    @FXML
    fun volverButton(event: ActionEvent) {
        val source: Node = event!!.source as Node
        val stage = source.scene.window as Stage
        stage.close()
    }



}
