package com.example.laboratorio1

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {

    private val fileName = "registro.txt"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var nombreEditText = findViewById<EditText>(R.id.edNombre)
        var apellidosEditText = findViewById<EditText>(R.id.edApellido)
        var correoEditText = findViewById<EditText>(R.id.edCorreo)
        var telefonoEditText = findViewById<EditText>(R.id.edTelefono)
        var grupoSanguineoEditText = findViewById<EditText>(R.id.edGrupoS)

        var btnRegistrar = findViewById<Button>(R.id.btnRegistrar)
        var btnMostrar = findViewById<Button>(R.id.btnMostrar)

        btnRegistrar.setOnClickListener {
            val nombre = nombreEditText.text.toString()
            val apellidos = apellidosEditText.text.toString()
            val correo = correoEditText.text.toString()
            val telefono = telefonoEditText.text.toString()
            val grupoSanguineo = grupoSanguineoEditText.text.toString()

            try {
                val fileOutputStream = openFileOutput(fileName, MODE_APPEND)
                val data = "$nombre, $apellidos, $correo, $telefono, $grupoSanguineo\n"
                fileOutputStream.write(data.toByteArray())
                fileOutputStream.close()
                Log.d("Info", "Datos guardados correctamente")
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        btnMostrar.setOnClickListener {
            try {
                val fileInputStream = openFileInput(fileName)
                val inputStreamReader = InputStreamReader(fileInputStream)
                val bufferedReader = BufferedReader(inputStreamReader)
                var line: String?

                while (bufferedReader.readLine().also { line = it } != null) {
                    Log.i("Registro", line!!)
                }

                bufferedReader.close()
            } catch (e: FileNotFoundException) {
                Log.e("Error", "Archivo no encontrado")
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}