package com.wilfredis.diariodedistas

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.wilfredis.diariodedistas.Models.AppDatabase

class Progreso : AppCompatActivity(),View.OnClickListener {
lateinit var db:AppDatabase
    lateinit var PesoA:TextView
    lateinit var PesoO:TextView
    lateinit var PesoR:TextView
    lateinit var Add:FloatingActionButton
    lateinit var lolipop:Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_progreso)

        PesoA = findViewById(R.id.TxtV_Original)
        PesoO = findViewById(R.id.Txtv_Objetivo)
        PesoR = findViewById(R.id.Txtv_Restante)
        Add = findViewById(R.id.Btn_Addx)
        lolipop = findViewById(R.id.Btnf_ADD)

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "Wilfredis"

        )
            .allowMainThreadQueries()
            .build()

        try {

            val ID_Uss2 = intent.getStringExtra("ID_User")
            Toast.makeText(this, ID_Uss2, Toast.LENGTH_SHORT).show()
            var info = db.abodesDab().findByName(ID_Uss2?.toInt())

            if (info != null){
                PesoA.text = info.Peso_Actual
                PesoO.text = info.Peso_Objetivo
                PesoR.text = (PesoA.text.toString().toInt() - PesoO.text.toString().toInt() ).toString()
            }else{
                PesoA.text = "0"
                PesoO.text = "0"
                PesoR.text = "0"
            }

        }catch (e: Exception) {
            Log.e("Data Load er", e.toString())
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var mensaje = when(item.itemId){
            R.id.Btn_Control -> {
                val intent1: Intent = Intent(this, Abordaje::class.java)

                val Name_Uss = intent.getStringExtra("Name_User")
                val ID_Uss = intent.getStringExtra("ID_User")

                intent1.putExtra("ID_User", ID_Uss.toString())
                intent1.putExtra("Name_User", Name_Uss)
                startActivity(intent1)
            }
            R.id.Btnf_Addprogress -> {
                val intent2: Intent = Intent(this, Rgistroprogresso::class.java)

                val Name_Uss = intent.getStringExtra("Name_User")
                val ID_Uss = intent.getStringExtra("ID_User")

                intent2.putExtra("ID_User", ID_Uss.toString())
                intent2.putExtra("Name_User", Name_Uss)
                startActivity(intent2)
            }
            else -> ""
        }


        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onClick(v: View?) {
/*
        if (v!!.id == R.id.Btnf_ADD) {
            try {
                val Progresx:Intent = Intent(this,Rgistroprogresso::class.java)
                startActivity(Progresx)
            }catch (e: Exception) {
                Log.e("Registro er", e.toString())
            }
        }*/
    }

}