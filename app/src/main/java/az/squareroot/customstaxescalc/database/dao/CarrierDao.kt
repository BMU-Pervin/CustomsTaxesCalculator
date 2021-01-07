package az.squareroot.customstaxescalc.database.dao

import androidx.room.*
import az.squareroot.customstaxescalc.database.datastructure.Carrier

@Dao
interface CarrierDao {

    @Insert
    fun insert(carrier: Carrier)

    @Update
    fun update(carrier: Carrier)

    @Delete
    fun delete(carrier: Carrier)

    @Query("SELECT * FROM carriers")
    fun getAll(): List<Carrier>

    @Query("DELETE FROM carriers")
    fun clear()
}