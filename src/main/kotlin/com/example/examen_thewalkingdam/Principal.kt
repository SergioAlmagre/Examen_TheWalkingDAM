package com.example.examen_thewalkingdam

import BBDD.Constantes
import Personaje.Personaje
import Utilidades.Datos
import Utilidades.Mensaje
import Zombies.Zombie
import javafx.application.Platform
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.input.MouseEvent
import javafx.stage.FileChooser
import javafx.stage.Modality
import javafx.stage.Stage
import java.awt.event.ActionListener
import java.io.File
import java.io.FileWriter
import java.net.URL
import java.sql.DriverManager
import java.time.LocalDate
import java.time.LocalTime
import java.util.*
import javax.swing.Timer
import kotlin.random.Random

class Principal:Initializable {

    var tiempo = 1
    var contador = 60
    var partida = Juego()
    var historial = ""
    val posicionClick = Array<Int>(2){0}
    val textoFinal = mensajeFinal()


    companion object {
        lateinit var temporizador: Timer
    }

    @FXML
    private lateinit var continuarIDButton: Button

    @FXML
    private lateinit var pausaIDButton: Button

    @FXML
    private lateinit var reiniciarIDButton: Button

    @FXML
    private lateinit var verInfoIdButton: Button

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

            inicializarPartida()

            temporizador = javax.swing.Timer(1000, object : ActionListener {
                override fun actionPerformed(e: java.awt.event.ActionEvent?) {

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
                                addInforme(infoPartidaField.text)
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
                            infoPartidaField.text = "Sophie ha sido rescatada!"
                            addInforme(infoPartidaField.text)
                            tablaJuego.items.clear()
                            for (i in 0..  partida.mapa.filas()-1){
                                val fila = Fila(
                                                textoFinal[i][0],
                                                textoFinal[i][1],
                                                textoFinal[i][2],
                                                textoFinal[i][3])
                                tablaJuego.items.add(fila)
                                temporizador.stop()
                            }
                        }
                    }
//  RESTAR VIDA A PERSONAJES ELEGIDOS SIN MUNICION Y SI NO LE QUEDA, CONVERTIRLO A ZOMBIE
                    if (tiempo % 10 == 0) {
                        for (e in partida.personajesElegidos){
                            if (e.municion <= 0){
                                e.vida - 10
                                infoPartidaField.text = "${e.nombre} pierde vida, le queda ${e.vida}"
                                addInforme(infoPartidaField.text)
                                if (e.vida <= 0){
                                    partida.convertirPersonajeAZombie(e.id)
                                    infoPartidaField.text = "Personaje ${e.nombre} convertido en zombie\n"
                                    addInforme(infoPartidaField.text)
                                }
                            }
                        }
                    }
                    if (tiempo % 60 == 0){
                        infoPartidaField.text = "Fin de partida por tiempo agotado\n"
                        addInforme(infoPartidaField.text)
                        tablaJuego.items.clear()
                        temporizador.stop()
                        reiniciarIDButton.requestFocus()
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
    fun inicializarPartida(){

// COLOCAR A LA PERSONAJE ESTRELLA
        partida.colocarObjeto(partida.sophie!!)
        infoPartidaField.text = "Sophie ha sido escondida"
        addInforme(infoPartidaField.text)
//  AÑADIR A 3 PERSONAJES VALIENTES
        for (i in 0..2){
            val p:Personaje? = partida.objenerPersonaje()
            partida.colocarObjeto(p!!)
            infoPartidaField.text = "Personaje ${p.nombre} elegido para la lucha!"
            addInforme(infoPartidaField.text)
            partida.personajesElegidos.add(p)
        }
    }

    @FXML
    fun guardarButton(event: MouseEvent) {
        var contenido = ""
        var baseDeDatos = "ahora veré como exportar la base de datos"

        if (comboTipoCopia.selectionModel.isEmpty){
            Mensaje.informacion("Tipo no seleccionado","Seleccione primero un tipo antes de exportar")
        }else {
            try {
                var seleccion = comboTipoCopia.selectionModel.selectedIndex

                for (e in partida.personajesElegidos) {
                    historial = historial + " - " + e.nombre + " - " + e.id + " - " + e.vida + " - " + e.municion + "\n"
                }
                var ventanaGuardar = FileChooser()
                ventanaGuardar.title = "Guardar como..."

                when (seleccion) {
                    0 -> {
                        ventanaGuardar.extensionFilters.addAll(
                            FileChooser.ExtensionFilter("TXT", "*.txt"))
                        contenido = historial
                    }
                    1 -> {
                        ventanaGuardar.extensionFilters.addAll(
                            FileChooser.ExtensionFilter("SQL", "*.sql"))
                        contenido = baseDeDatos
                    }
                }

                val nombreArchivo = ventanaGuardar.showSaveDialog(null)
                var archivo = FileWriter(nombreArchivo, false)

                archivo.write(contenido)
                archivo.close()

            } catch (e: Exception) {
                Datos.gestionErrores(e, "guardarButton")
            }
        }
    }

    @FXML
    fun verInfoButton(event: ActionEvent) {
        if (Datos.zom != null){
            if (Datos.tipo == 0){
                infoPartidaField.text = "Mostrando información sobre ${Datos.zom.toString()}"
                addInforme(infoPartidaField.text)
            }

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
        }else{
            Mensaje.informacion("No hay casilla seleccionada","Seleccione primero una casilla y vuelva a intarlo")
        }
    }


    @FXML
    fun pausarButton(event: ActionEvent) {
        infoPartidaField.text = "Partida pausada"
        addInforme(infoPartidaField.text)
        temporizador.stop()
        continuarIDButton.requestFocus()
    }

    @FXML
    fun reiniciarButton(event: ActionEvent) {
        if (Mensaje.alerta("Estas seguro?", "Este juego es adictivo") == ButtonType.YES) {
            var mensaje = "Reiniciando partida"
            try {
                infoPartidaField.text = mensaje
                for (i in 0..10) {
                    infoPartidaField.text = infoPartidaField.text + "."
                    Thread.sleep(100)
                }

                addInforme(infoPartidaField.text)
                tablaJuego.items.clear()
                partida = Juego()
                contador = 60
                tiempo = 1
                progressBar.progress = contador.toDouble()
                temporizador.start()
                inicializarPartida()
                infoPartidaField.requestFocus()
            } catch (e: Exception) {
                Datos.gestionErrores(e, "reiniciarPartida")
            }
        }
    }

    @FXML
    fun continuarButton(event: ActionEvent) {
        Mensaje.informacion("Preparate que seguimos","")
        infoPartidaField.text = "Reanudando partida"
        addInforme(infoPartidaField.text)
        temporizador.start()
        infoPartidaField.requestFocus()
    }

    @FXML
    fun onClickTable(event: MouseEvent) {
        println(tablaJuego.selectionModel.selectedIndex)
        if (this.tablaJuego.selectionModel.selectedIndex != -1) {
            val objeto: Fila? = this.tablaJuego.selectionModel.selectedItem
            val posSeleccionada: String = java.lang.String.valueOf(this.tablaJuego.selectionModel.selectedIndex)
            val celdaSeleccionada = this.tablaJuego.selectionModel.selectedCells.firstOrNull()
            if (celdaSeleccionada != null) {
                val filSeleccionada = celdaSeleccionada.row
                val colSeleccionada = celdaSeleccionada.column
                posicionClick[0] = filSeleccionada
                posicionClick[1] = colSeleccionada
                val celda =
                    this.tablaJuego.getVisibleLeafColumn(colSeleccionada).getCellObservableValue(filSeleccionada).value
                val valorCelda = celda?.toString()
                println("Valor de la celda seleccionada: $valorCelda")
                println(Arrays.toString(posicionClick))
            }
        }
    }

    fun addInforme(mensaje:String){
        val fechaYHora = LocalDate.now().toString() + " - "+LocalTime.now().toString().substring(0,8) + " - "
        val guion = " - "
        val salto = "\n"
        historial = historial + fechaYHora + guion + mensaje + salto
    }

    fun mensajeFinal():Array<Array<String>>{
        var cad = "PARTIDATERMINADA" // MAX 16 CARACTERES
        var ma = Array(4){Array<String>(4){""}}
        var cont = 1

            for (f in 0..ma.size-1){
                for (c in 0..ma[0].size-1){
                    ma[f][c] = cad.substring(cont-1,cont)
                    cont++
                }
            }
        return ma
    }

// INVESTIGANDO.....CREO QUE TIENE METODOS DEPRECADOS O NO SE IMPLEMENTARLO CORRECTAMENTE
    fun exportarBaseDeDatos(rutaArchivo: String):FileWriter {
        val url = "jdbc:mysql://${Constantes.servidor}:${Constantes.puerto}/${Constantes.bbdd}"
        val driver = "com.mysql.jdbc.Driver"
        var writer = FileWriter("Examen_TheWalkingDam")

        try {
            Class.forName(driver)
            val connection = DriverManager.getConnection(url, "${Constantes.usuario}", "${Constantes.passwd}")
            val metaData = connection.metaData

            // Obtener todas las tablas de la base de datos
            val tablas = metaData.getTables(null, null, "%", null)

            val archivo = File(rutaArchivo)
            writer = FileWriter(archivo)

            while (tablas.next()) {
                val nombreTabla = tablas.getString("TABLE_NAME")
                val statement = connection.createStatement()
                val resultSet = statement.executeQuery("SELECT * FROM $nombreTabla")

                // Generar el script SQL para cada tabla
                writer.write("-- Tabla: $nombreTabla\n")

                while (resultSet.next()) {
                    val resultSetMetaData = resultSet.metaData
                    val columnCount = resultSetMetaData.columnCount

                    // Generar el script SQL para cada fila de la tabla
                    val valores = StringBuilder()

                    for (i in 1..columnCount) {
                        val valor = resultSet.getString(i)
                        valores.append("'$valor', ")
                    }

                    writer.write("INSERT INTO $nombreTabla VALUES (${valores.removeSuffix(", ")})\n")
                }

                writer.write("\n")
            }

//            writer.close()
            connection.close()

            println("La base de datos se ha exportado correctamente al archivo: $rutaArchivo")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    return writer
    }



}

