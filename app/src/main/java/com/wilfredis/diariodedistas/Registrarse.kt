package com.wilfredis.diariodedistas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.room.Room
import com.google.android.material.snackbar.Snackbar
import com.wilfredis.diariodedistas.Models.AppDatabase
import org.w3c.dom.Text

class Registrarse : AppCompatActivity(), View.OnClickListener {

    lateinit var Iniciar: TextView
    lateinit var db: AppDatabase

    lateinit var Crear: Button
    lateinit var Name: EditText
    lateinit var Apellido: EditText
    lateinit var Correo: EditText
    lateinit var Passs: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrarse)

        Iniciar = findViewById(R.id.Txt_InicioSeccion)
        Iniciar.setOnClickListener(this)

        Name = findViewById(R.id.Txt_Nombre)
        Apellido = findViewById(R.id.Txt_Apellido)
        Correo = findViewById(R.id.Txt_Correo)
        Passs = findViewById(R.id.Txt_Passs)

        Crear = findViewById(R.id.Btn_CrearCuentas)
        Crear.setOnClickListener(this)

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "Wilfredis"

        )
            .allowMainThreadQueries()
            .build()

    }

    override fun onClick(v: View?) {

        if (v?.id == R.id.Txt_InicioSeccion) {
            val intent: Intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

        if (v?.id == R.id.Btn_CrearCuentas) {
           try {
               val user = User(0, Name.text.toString(), Apellido.text.toString(), Correo.text.toString(), Passs.text.toString())
               db.userDao().insertAll(user)
               Toast.makeText(this, "Guardado", Toast.LENGTH_SHORT).show()
               val intent: Intent = Intent(this,MainActivity::class.java)
               startActivity(intent)
           }catch (e: Exception) {
               Log.e("insert er", e.toString())
           }
        }


    }
}

