package ui

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import android.widget.LinearLayout
import android.widget.TableRow
import bbdd.HabitantesSQLite
import com.elsda.tierramedia_pac.R

class ListarFestivos: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar_festivos)

        // Recuperamos el festivo por el Intent
        val festivo = intent.getStringExtra("FESTIVO")
        Log.d("ListarFestivosActivity", "Festivo: $festivo")

        // Recuperamos el listado de la Base de Datos
        val db = HabitantesSQLite(this)
        val listadoHabitantes = festivo?.let { db.getListadoPorFestivo(it) }
        // Asignamos los elementos de la UI
        val nombreFestivo = findViewById<TextView>(R.id.txtNombreFestivo)
        val btnAtras = findViewById<ImageButton>(R.id.btnAtrasListadoFestivo)
        val tableLayout = findViewById<LinearLayout>(R.id.layoutListadoFestivo)

        // Actualizamos los datos que necesitamos mostrar en el listado
        when (festivo) {
            "2 Yule"-> nombreFestivo.text = "2 Yule"
            "1 Agil"-> nombreFestivo.text = "1 Agil"
            "Sobrelithe"-> nombreFestivo.text = "Sobrelithe"
            "2 Agil"-> nombreFestivo.text = "2 Agil"
            "1 Yule"-> nombreFestivo.text = "1 Yule"
            "SanIsildur"-> nombreFestivo.text = "SanIsildur"
            "AlmudenasDay"-> nombreFestivo.text = "AlmudenasDay"
            "BandoHuerta"-> nombreFestivo.text = "BandoHuerta"
            "Jarramplas"-> nombreFestivo.text = "Jarramplas"
            "DiaLunar"-> nombreFestivo.text = "DiaLunar"
            else -> nombreFestivo.text = "Festivo desconocido: $festivo"

        }

        // Por cada habitante, crea una nueva TableRow y añádela al LinearLayout
        if (listadoHabitantes != null && listadoHabitantes.size > 0) {
            listadoHabitantes.forEach { habitante ->
                val fila = TableRow(this@ListarFestivos)
                // Llama a insertarTextoEnTabla para cada detalle del habitante
                insertarTextoEnTabla(habitante.nombre, fila)
                insertarTextoEnTabla(habitante.apellidos, fila)
                insertarTextoEnTabla(habitante.edad.toString(), fila)
                insertarTextoEnTabla(habitante.ubicacion, fila)
                // Añade la fila al TableLayout después de añadir todos los TextViews
                tableLayout.addView(fila) // Asegúrate de tener una referencia a tu TableLayout
            }
        }

        // Añadimos el listener para volver a la Activity anterior
        btnAtras.setOnClickListener {
            // Cierra la Activity actual => cierra esta Activity y vuelva a la anterior en la pila
            finish()
        }
    }

    /**
     * Método para insertar texto en la tabla
     * @param texto cadena de texto a añadir en la fila
     * @param fila fila de la tabla donde añadiremos el texto
     */
    private fun insertarTextoEnTabla(texto: String, fila: TableRow) {
        val textView = TextView(this@ListarFestivos).apply {
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