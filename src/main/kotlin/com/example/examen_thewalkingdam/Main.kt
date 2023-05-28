package com.example.examen_thewalkingdam

import BBDD.Conexion
import Escenario.Mapa
import Zombies.Zombie
import Zombies.ZombiePupas
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import javafx.stage.Stage
import javafx.stage.WindowEvent

class HelloApplication : Application() {
    override fun start(stage: Stage) {
        val fxmlLoader = FXMLLoader(HelloApplication::class.java.getResource("Principal-view.fxml"))
        val scene = Scene(fxmlLoader.load())
        stage.title = "The walwing DAM 1"
        stage.scene = scene
        stage.setOnCloseRequest { e -> cerrarAplicacion(e) }
        stage.show()
    }
}


fun main() {
    Application.launch(HelloApplication::class.java)
}

fun cerrarAplicacion(e : WindowEvent) {
    var alerta = Alert(Alert.AlertType.CONFIRMATION)
    alerta.title = "Salir de la aplicación"
    alerta.headerText = "¿Desea salir de la aplicación?"
    alerta.contentText = "Cualquier cambio no guardado se perderá."

    alerta.buttonTypes.remove(ButtonType.OK)
    alerta.buttonTypes.remove(ButtonType.CANCEL)

    alerta.buttonTypes.add(ButtonType.YES)
    alerta.buttonTypes.add(ButtonType.NO)

    val res = alerta.showAndWait()
    if(res.isPresent) {
        if(res.get() == ButtonType.NO) {
            e.consume()
        } else {
            Conexion.cerrarConexion()
        }
    }
}