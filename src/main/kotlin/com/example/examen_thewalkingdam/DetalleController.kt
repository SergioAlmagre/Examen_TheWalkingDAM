package com.example.examen_thewalkingdam
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.Node
import javafx.scene.control.CheckBox
import javafx.scene.control.RadioButton
import javafx.scene.control.TextField
import javafx.scene.control.ToggleGroup
import javafx.scene.layout.AnchorPane
import javafx.stage.Stage

class DetalleController {

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
