package com.example.examen_thewalkingdam

import Personaje.Personaje
import Utilidades.Datos
import Zombies.Zombie
import javafx.application.Platform
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.Scene
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.input.MouseEvent
import javafx.stage.FileChooser
import javafx.stage.Modality
import javafx.stage.Stage
import javafx.event.ActionEvent
import javafx.scene.control.*
import java.awt.event.ActionListener
import java.io.FileWriter
import java.net.URL
import java.time.LocalDate
import java.time.LocalTime
import java.util.*
import javax.swing.Timer
import kotlin.random.Random

class Principal:Initializable {

    var tiempo = 1
    var contador = 300
    var partida = Juego()
    var historial = ""
    val posicionClick = Array<Int>(2){0}


    companion object {
        lateinit var temporizador: Timer
    }

    @FXML
    private lateinit var c1: TableColumn<Fila, Any>

    @FXML
    private lateinit var c2: TableColumn<Fila, Any>

    @FXML
    private lateinit var c3: TableColumn<Fila, Any>

    @FXML
    private lateinit var c4: TableColumn<Fila, Any>

    @FXML
    private lateinit var comboTipoCopia: ComboBox<Any>

    @FXML
    private lateinit var progressBar: ProgressBar

    @FXML
    private lateinit var tablaJuego: TableView<Fila>

    @FXML
    private lateinit var infoPartidaField: TextField

    override fun initialize(p0: URL?, p1: ResourceBundle?) {
        try {
            c1.cellValueFactory = PropertyValueFactory("c1")
            c2.cellValueFactory = PropertyValueFactory("c2")
            c3.cellValueFactory = PropertyValueFactory("c3")
            c4.cellValueFactory = PropertyValueFactory("c4")

            var tipoCopia = arrayOf("De texto", "Base de datos")
            comboTipoCopia.promptText = "Seleccione tipo"
            comboTipoCopia.items.addAll(*tipoCopia)
// COLOCAR A LA PERSONAJE ESTRELLA
            partida.colocarObjeto(partida.shophie!!)
//  AÑADIR A 3 PERSONAJES VALIENTES
            for (i in 0..2){
                val p:Personaje? = partida.objenerPersonaje()
                partida.colocarObjeto(p!!)
                partida.personajesElegidos.add(p)
            }

            temporizador = javax.swing.Timer(1000, object : ActionListener {
                override fun actionPerformed(e: java.awt.event.ActionEvent?) {
                    var fechaYHora = LocalDate.now().toString() + " - "+LocalTime.now().toString().substring(0,8) + " - "
                    progressBar.progress = contador.toDouble() / 60
                    Platform.runLater{
                        if (partida.encontrada == false) {
//  AÑADIR ENTRE 2 Y 3 ZOMBIES A LA PARTIDA
                            var num = Random.nextInt(2,3)
                            for (i in 0..num){
                                partida.colocarObjeto(partida.popZombie()!!)
                            }
//  MOSTRAR ACTUALIZACIONES Y GUARDAR EL HISTORIAL DE EVENTOS EN EL JUEGO
                                partida.mover()
                                infoPartidaField.text = partida.mensajesJuego
                                historial = historial + fechaYHora + infoPartidaField.text + "\n"
//  BORRAR TABLA, AÑADIR A LA CLASE FILA EL CONTENIDO DE NUESTRA MATRIZ FILA POR FILA
                                tablaJuego.items.clear()
                                for (i in 0..  partida.mapa.filas()-1){
                                    val fila = Fila(partida.mapa.getPosicion(i,0)?.toString() ?: "",
                                                    partida.mapa.getPosicion(i,1)?.toString() ?: "",
                                                    partida.mapa.getPosicion(i,2)?.toString() ?: "",
                                                    partida.mapa.getPosicion(i,3)?.toString() ?: "")
                                    tablaJuego.items.add(fila)
                                }
                        }else{
//  FANTASIA CUANDO SE HA ENCONTRADO AL PERSONAJE ELEGIDO
                            tablaJuego.items.clear()
                            for (i in 0..  partida.mapa.filas()-1){
                                val fila = Fila("!","!","!","!")
                                tablaJuego.items.add(fila)
//                                temporizador.stop()
                            }
                        }
                    }
//  RESTAR VIDA A PERSONAJES ELEGIDOS SIN MUNICION Y SI NO LE QUEDA, CONVERTIRLO A ZOMBIE
                    if (tiempo % 10 == 0) {
                        for (e in partida.personajesElegidos){
                            if (e.municion <= 0){
                                e.vida - 10
                                if (e.vida <= 0){
                                    partida.convertirPersonajeAZombie(e.id)
                                    infoPartidaField.text = "Personaje ${e.nombre} convertido en zombie\n"
                                    historial = historial + fechaYHora + infoPartidaField.text
                                }
                            }
                        }
                    }
                    if (tiempo % 300 == 0){
                        infoPartidaField.text = "Fin de partida por tiempo agotado\n"
                        historial = historial + fechaYHora + infoPartidaField.text
                        tablaJuego.items.clear()
//                        temporizador.stop()
                    }

                    tiempo++
                    contador--
                }
            })
            temporizador.start()

        } catch (e: Exception) {
            Datos.gestionErrores(e, "Fallo en Inicialicer principal")
        }
        tablaJuego.selectionModel.cellSelectionEnabledProperty().set(true)
        tablaJuego.selectionModel.selectionMode = SelectionMode.SINGLE
    }

    @FXML
    fun guardarButton(event: MouseEvent) {
        try {
            var ventanaGuardar = FileChooser()
            ventanaGuardar.title = "Guardar como..."

            ventanaGuardar.extensionFilters.addAll(
                FileChooser.ExtensionFilter("TXT", "*.txt"),
                FileChooser.ExtensionFilter("LOG", "*.log"),
            )
            val nombreArchivo = ventanaGuardar.showSaveDialog(null)
            var archivo = FileWriter(nombreArchivo, false)
            archivo.write(historial)
            archivo.close()
        }catch (e:Exception){
            Datos.gestionErrores(e,"guardarButton")
        }
    }

    @FXML
    fun verInfoButton(event: ActionEvent) {
        var objeto:Any? = partida.mapa.getPosicion(posicionClick[0],posicionClick[1])
        if (objeto is Zombie){
            Datos.zom = objeto
            Datos.tipo = 0
        }
        if (objeto is Personaje){
            Datos.per = objeto
            Datos.tipo = 1
        }

        val fxmlLoader  = FXMLLoader(HelloApplication::class.java.getResource("Detalle-view.fxml"))
        val scene = Scene(fxmlLoader.load())
        val stage = Stage()
        stage.title = "Detalles"
        stage.scene = scene
        stage.initModality(Modality.APPLICATION_MODAL)
        stage.showAndWait()
    }


    @FXML
    fun pausarButton(event: ActionEvent) {
        temporizador.stop()
    }

    @FXML
    fun reiniciarButton(event: ActionEvent) {
//        temporizador.stop()
    var mensaje = "Reiniciando partida"
        infoPartidaField.text = mensaje
        for (i in 0..10){
            infoPartidaField.text = infoPartidaField.text + "."
            Thread.sleep(100)
        }
        historial = historial + infoPartidaField.text + "\n"


        tablaJuego.items.clear()
        partida.personajesElegidos.clear()
        partida.allPersonaje.clear()
        partida.allZombies.clear()
        partida.zombiesVivos = 0
        partida.encontrada = false
        contador = 300
        tiempo = 1
        progressBar.progress = contador.toDouble()
        partida.mensajesJuego = ""
        infoPartidaField.text = ""
        historial = ""

    }

    @FXML
    fun continuarButton(event: ActionEvent) {
        temporizador.start()
    }

    @FXML
    fun onClickTable(event: MouseEvent) {

        println(tablaJuego.selectionModel.selectedIndex)
        if (this.tablaJuego.selectionModel.selectedIndex != -1){
            val objeto: Fila? = this.tablaJuego.selectionModel.selectedItem
            val posSeleccionada: String = java.lang.String.valueOf(this.tablaJuego.selectionModel.selectedIndex)
            val celdaSeleccionada = this.tablaJuego.selectionModel.selectedCells.firstOrNull()
            if (celdaSeleccionada != null) {
                val filSeleccionada = celdaSeleccionada.row
                val colSeleccionada = celdaSeleccionada.column
                posicionClick[0] = filSeleccionada
                posicionClick[1] = colSeleccionada
                val celda = this.tablaJuego.getVisibleLeafColumn(colSeleccionada).getCellObservableValue(filSeleccionada).value
                val valorCelda = celda?.toString()
                println("Valor de la celda seleccionada: $valorCelda")
                println(Arrays.toString(posicionClick))
            }




//    mensaje        mostrarInformacion("Información seleccionada", "Información de la fila seleccionada: $posSeleccionada", p.toString())
        } else {
//      mensaje      mostrarError("Error","Seleccione una fila","Compruebe que tiene datos en la tabla y que hay una fila seleccionada")
        }
    }


}

