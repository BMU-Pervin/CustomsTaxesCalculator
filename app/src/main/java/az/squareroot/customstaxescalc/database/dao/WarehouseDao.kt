package az.squareroot.customstaxescalc.database.dao

import androidx.room.*
import az.squareroot.customstaxescalc.database.datastructure.Warehouse

@Dao
interface WarehouseDao {

    @Insert
    fun insert(warehouse: Warehouse)

    @Update
    fun update(warehouse: Warehouse)

    @Delete
    fun delete(warehouse: Warehouse)

    @Query("SELECT * FROM warehouses WHERE carrier_id = :carrierId")
    fun findAll(carrierId: Int): List<Warehouse>
}