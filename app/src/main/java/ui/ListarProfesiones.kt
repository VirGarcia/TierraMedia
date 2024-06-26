package ui

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TableRow
import android.widget.TextView
import bbdd.HabitantesSQLite
import com.elsda.tierramedia_pac.R

class ListarProfesiones : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar_profesiones)
        //TODO crear toda la lógica para que se muestre bien la información en el listado de profesiones

        //Recuperamos el valor de la profesion pasado por el Intent
        val profesion = intent.getStringExtra("PROFESION")
        Log.d("ListarProfesionesActivity", "Profesion: Sprofesion")

        //Recuperamos el listado de la Base de Datos
        val db = HabitantesSQLite(this)
        val listadoHabitantes = profesion?.let { db.getListadoPorProfesion(it)}
        //Asignamos los elementos de la UI
        val nombreProfesion = findViewById<TextView>(R.id.txtNombreProfesion)
        val btnAtras = findViewById<ImageButton>(R.id.btnAtrasListadoProfesion)
        val tableLayout = findViewById<LinearLayout>(R.id.layoutListadoProfesion)

        //Actualizamos los datos que necesitamos mostrar en el listado
        when (profesion) {
            "Caballero" -> nombreProfesion.text = "Caballero"
            "Arquero" -> nombreProfesion.text = "Arquero"
            "Herrero" -> nombreProfesion.text = "Herrero"
            "Juglar" -> nombreProfesion.text = "Juglar"
            "Campesino" -> nombreProfesion.text = "Campesino"
            "Alquimista" -> nombreProfesion.text = "Alquimista"
            "Escriba" -> nombreProfesion.text = "Escriba"
            "Mercader" -> nombreProfesion.text = "Mercader"
            "Monje" -> nombreProfesion.text = "Monje"
            "Carpintero" -> nombreProfesion.text = "Carpintero"
            else -> nombreProfesion.text = "Profesion desconocida: $profesion"
        }

        // Por cada habitante, crea una nueva TableRow y añádela al LinearLayout
        if (listadoHabitantes != null && listadoHabitantes.size > 0) {
            listadoHabitantes.forEach { habitante ->
                val fila = TableRow(this@ListarProfesiones)
                // Llama a insertarTextoEnTabla para cada detalle del habitante
                insertarTextoEnTabla(habitante.nombre, fila)
                insertarTextoEnTabla(habitante.apellidos, fila)
                insertarTextoEnTabla(habitante.raza, fila)
                insertarTextoEnTabla(habitante.ubicacion, fila)
                // Añade la fila al TableLayour después de añadir todos los TextViews
                tableLayout.addView(fila) // Asegúrate de tener una referencia a tu TableLayout
            }
        }
        //Añadimos el listener para volver a la Activity anterior
        btnAtras.setOnClickListener {
            //Cierra la Activity actual => cierra esta Activity y vuelva a la anterior en la pila
            finish()
        }
    }

    /**
     * Método para insertar texto en la tabla
     * @param texto cadena de texto a añadir en la fila
     * @param fila fila de la tabla donde añadiremos el texto
     */
    private fun insertarTextoEnTabla(texto: String, fila: TableRow) {
        val textView = TextView(this@ListarProfesiones).apply {
            text = texto
            // Ajusta estos layoutParams dependiendo de tu contenedor final
            layoutParams = TableRow.LayoutParams(
                0, // Usar 0 para el ancho en TableRow con layout_weight
                TableRow.LayoutParams.WRAP_CONTENT,
                1f // Peso para distribución equitativa en TableRow
            )
            textSize = 11f
            setBackgroundColor(Color.WHITE)
            setTextColor(Color.BLACK)
        }

        fila.addView(textView)
    }
}