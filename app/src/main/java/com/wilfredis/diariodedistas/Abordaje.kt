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
import androidx.room.*
import com.wilfredis.diariodedistas.Models.AppDatabase

class Abordaje : AppCompatActivity(), View.OnClickListener {

    lateinit var db:AppDatabase

    lateinit var Nombre:TextView
    lateinit var PesoA:EditText
    lateinit var PesoO:EditText
    lateinit var Save:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_abordaje)

        val Name_Uss = intent.getStringExtra("Name_User")

        Nombre = findViewById(R.id.Txt_Bienvenida)
        Nombre.text ="Hola, $Name_Uss "
        PesoA = findViewById(R.id.Txt_PesoA)
        PesoO = findViewById(R.id.Txt_PesoO)

        Save = findViewById(R.id.Btn_GuardarPeso)
        Save.setOnClickListener(this)

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "Wilfredis"
        )
            .allowMainThreadQueries()
            .build()
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.Btn_GuardarPeso) {

            try {
                val ID_Uss = intent.getStringExtra("ID_User")
                val Abo = Abodes(0,  ID_Uss?.toInt(), PesoA.text.toString(), PesoO.text.toString())
                db.abodesDab().insertAll(Abo)
                Toast.makeText(this, "Guardado", Toast.LENGTH_SHORT).show()
                val back: Intent = Intent(this,Progreso::class.java)
                back.putExtra("ID_User", ID_Uss.toString())
                startActivity(back)
            }catch (e: Exception) {
                Log.e("insert er", e.toString())
            }

        }
    }
}

@Entity
public data class Abodes(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "ID_Useerx") val ID_Useerx: Int?,
    @ColumnInfo(name = "Peso_Actual") val Peso_Actual: String?,
    @ColumnInfo(name = "Peso_Objetivo") val Peso_Objetivo: String?
)
@Dao
public interface AbodesDab {
    @Query("SELECT * FROM Abodes")
    fun getAll(): List<Abodes>

    @Query("SELECT * FROM Abodes WHERE uid IN (:AbordajesIds)")
    fun loadAllByIds(AbordajesIds: IntArray): List<Abodes>

    @Query("SELECT * FROM Abodes WHERE ID_Useerx LIKE :ID_Useerx")
    fun findByName(ID_Useerx: Int?): Abodes

    @Insert
    fun insertAll(vararg abordaje: Abodes)

    @Delete
    fun delete(abordaje: Abodes)

}