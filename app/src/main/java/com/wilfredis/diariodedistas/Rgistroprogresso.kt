package com.wilfredis.diariodedistas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.*


class Rgistroprogresso : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rgistroprogresso)
    }
}

public data class Resow(
         @PrimaryKey(autoGenerate = true) val uid: Int,
         @ColumnInfo(name = "ID_Uss") val ID_Uss: Int?,
         @ColumnInfo(name = "Fella") val Fella: String?,
         @ColumnInfo(name = "Peso") val Peso: String?
)
@Dao
 public interface ResowDao {
    @Query("SELECT * FROM Resow")
    fun getAll(): List<Resow>

    @Query("SELECT * FROM Rpesos WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<Resow>

    @Insert
    fun insertAll(vararg users: Resow)

    @Delete
    fun delete(user: Resow)

}

