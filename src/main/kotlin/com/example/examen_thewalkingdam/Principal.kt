package com.example.examen_thewalkingdam

import javafx.fxml.FXML
import javafx.scene.control.ComboBox
import javafx.scene.control.ProgressBar
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.input.MouseEvent

class Principal {

    @FXML
    private lateinit var c1: TableColumn<Any, Any>

    @FXML
    private lateinit var c2: TableColumn<Any, Any>

    @FXML
    private lateinit var c3: TableColumn<Any, Any>

    @FXML
    private lateinit var c4: TableColumn<Any, Any>

    @FXML
    private lateinit var comboTipoCopia: ComboBox<Any>

    @FXML
    private lateinit var progressBar: ProgressBar

    @FXML
    private lateinit var tablaJuego: TableView<Any>

    @FXML
    fun guardarButton(event: MouseEvent) {

    }

    @FXML
    fun verInfoButton(event: MouseEvent) {

    }

}
