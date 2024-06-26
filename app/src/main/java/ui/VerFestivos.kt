package ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.Spinner
import com.elsda.tierramedia_pac.R
import com.elsda.tierramedia_pac.habitantes.Festivo

class VerFestivos : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_festivos)

        val spinner: Spinner = findViewById(R.id.spinFestivos)
        val btnAtras = findViewById<ImageButton>(R.id.btnAtrasEligeFestivo)
        val btnListarFestivo = findViewById<Button>(R.id.btnListarFestivo)

        // Array con las opciones ordenadas alfabéticamente
        val opciones = Festivo().festivos.sortedArray()
        var festivoSeleccionado = opciones[0] // Inicializamos con el primer festivo

        // Crear un ArrayAdapter usando el array de strings y un layout de spinner predeterminado
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, opciones)
        // Especificar el layout a usar cuando la lista de opciones aparezca
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Aplicar el adapter al spinner
        spinner.adapter = adapter
        // Establecer un listener para cuando se seleccione un elemento
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                // Haz algo con el item seleccionado
                Log.d("Spinner", "Seleccionado: ${opciones[position]}")
                festivoSeleccionado = opciones[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Otra opción, por si no se selecciona nada
            }
        }

        // Añadimos el listener para ir al listado de Festivos
        btnListarFestivo.setOnClickListener {
            val intent = Intent(this, ListarFestivos::class.java).apply {
                putExtra("FESTIVO", festivoSeleccionado)
            }
            startActivity(intent)
        }

        // Añadimos el listener para volver a la Activity anterior
        btnAtras.setOnClickListener {
            // Cierra la Activity actual => cierra esta Activity y vuelva a la anterior en la pila
            finish()
        }

    }

}