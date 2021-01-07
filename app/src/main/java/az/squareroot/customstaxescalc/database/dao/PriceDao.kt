package az.squareroot.customstaxescalc.database.dao

import androidx.room.*
import az.squareroot.customstaxescalc.database.datastructure.Price

@Dao
interface PriceDao {

    @Insert
    fun insert(price: Price)

    @Update
    fun update(price: Price)

    @Delete
    fun delete(price: Price)

    @Query("SELECT * FROM prices WHERE carrier_id = :carrierId")
    fun findAll(carrierId: Int): List<Price>

    @Query("DELETE FROM prices")
    fun clear()
}