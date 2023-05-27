package Escenario

class Mapa {
     var mapa = Array(4){Array<Casilla?>(4){ Casilla() }}

    fun filas():Int{
        var cantidad = mapa.size-1
        return cantidad
    }

    fun columnas():Int{
        var cantidad = mapa[0].size-1
        return cantidad
    }

//    fun getPosicion(fil:Int, col:Int):Any?{
//        var objeto: Any? = null
//        try {
//            objeto = mapa[fil][col]!!.casilla[0]
//        }catch (e:Exception){
//            objeto = null
//        }
//        return objeto
//    }

    fun getPosicion(fil:Int, col:Int):Any?{
        return mapa[fil][col]!!.objeto
    }

    fun setPosicion(fil:Int, col:Int,objeto:Any){
        mapa[fil][col]!!.objeto = objeto
    }


    override fun toString(): String {
        var cad = ""
        var f = 0
        var c = 0
        for (f in 0..filas()){

            for (c in 0..columnas()){
                cad = cad + "Fila $f - Columna $c" +" - "
                cad = cad + getPosicion(f,c).toString() + "\t\n"
            }
        }
        return  cad
    }

}