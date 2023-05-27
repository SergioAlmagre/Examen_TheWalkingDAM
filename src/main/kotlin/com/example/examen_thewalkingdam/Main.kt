package com.example.examen_thewalkingdam

import BBDD.Conexion
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage

class HelloApplication : Application() {
    override fun start(stage: Stage) {
        val fxmlLoader = FXMLLoader(HelloApplication::class.java.getResource("Principal-view.fxml"))
        val scene = Scene(fxmlLoader.load())
        stage.title = "The walwing DAM 1"
        stage.scene = scene
        stage.show()
    }
}

fun main() {
    Application.launch(HelloApplication::class.java)
}