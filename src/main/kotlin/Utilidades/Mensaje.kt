package Utilidades

import javafx.animation.KeyFrame
import javafx.animation.PauseTransition
import javafx.animation.Timeline
import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import javafx.stage.Modality
import javafx.stage.Stage
import java.awt.Button
import javafx.util.Duration

object Mensaje {

    fun alerta (cabecera:String,contenido:String):ButtonType{
        var alerta = Alert(Alert.AlertType.WARNING)
        alerta.title = "Atención!"
        alerta.headerText = cabecera
        alerta.contentText = contenido

        alerta.buttonTypes.remove(ButtonType.OK)
        alerta.buttonTypes.remove(ButtonType.CANCEL)

        alerta.buttonTypes.add(ButtonType.YES)
        alerta.buttonTypes.add(ButtonType.NO)

        return alerta.showAndWait().orElse(ButtonType.YES)
    }

    fun informacion (cabecera:String,contenido:String){
        var alerta = Alert(Alert.AlertType.INFORMATION)
        alerta.title = "Continuamos!"
        alerta.headerText = cabecera
        alerta.contentText = contenido

        alerta.showAndWait()
    }










}