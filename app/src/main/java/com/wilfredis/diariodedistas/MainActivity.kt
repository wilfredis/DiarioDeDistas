package com.wilfredis.diariodedistas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.room.*
import com.wilfredis.diariodedistas.Models.AppDatabase

class MainActivity : AppCompatActivity(),View.OnClickListener {
    lateinit var Nuevo: TextView
    lateinit var Logearse: Button

    //campos de entrada login
    lateinit var Useerx:EditText
    lateinit var Passx:EditText
    //fin

    lateinit var db:AppDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

         db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "Wilfredis"
        )
             .allowMainThreadQueries()
             .build()

        Nuevo = findViewById(R.id.Txt_Crear)
        Nuevo.setOnClickListener(this)

        Logearse = findViewById(R.id.Btn_Login)
        Logearse.setOnClickListener(this)

        //llenando esos comp
        Useerx = findViewById(R.id.Txt_Usserxx)
        Passx = findViewById(R.id.Txt_Passwordx)
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.Txt_Crear) {
            try {
                val intent:Intent = Intent(this,Registrarse::class.java)
                startActivity(intent)
            }catch (e: Exception) {
                Log.e("Registro er", e.toString())
            }
        }

        if (v?.id == R.id.Btn_Login) {
            try {

                var dao = db.userDao().buscarUsuario(Useerx.text.toString(),Passx.text.toString())

                if (dao != null){

                    val intent:Intent = Intent(this,Progreso::class.java)
                    intent.putExtra("ID_User", dao.uid.toString())
                    intent.putExtra("Name_User", dao.Nombres)
                    startActivity(intent)
                }

            }catch (e: Exception) {
                Log.e("Login er", e.toString())
            }
        }
    }
}

@Entity
public data class User(
        @PrimaryKey(autoGenerate = true) val uid: Int,
        @ColumnInfo(name = "Nombres") val Nombres: String?,
        @ColumnInfo(name = "Apellidos") val Apellidos: String?,
        @ColumnInfo(name = "Email") val Email: String?,
        @ColumnInfo(name = "Pass") val Pass: String?
)
@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<User>

    @Query("SELECT * FROM user WHERE Nombres LIKE :Nombres AND " +
            "Apellidos LIKE :Apellidos LIMIT 1")
    fun findByName(Nombres: String, Apellidos: String): User

    @Insert
    fun insertAll(vararg users: User)

    @Delete
    fun delete(user: User)

    @Query("Select * FROM user WHERE (Nombres = :Nombres or Email = :Nombres) and Pass = :Pass")
    fun buscarUsuario(Nombres: String, Pass: String?): User
}



