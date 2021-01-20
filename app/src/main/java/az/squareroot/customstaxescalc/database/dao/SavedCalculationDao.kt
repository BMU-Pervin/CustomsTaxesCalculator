package az.squareroot.customstaxescalc.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import az.squareroot.customstaxescalc.database.datastructure.SavedCalculation

@Dao
interface SavedCalculationDao {

    @Insert
    fun insert(calculation: SavedCalculation)

    @Query("DELETE FROM saved_calculation WHERE id=:id")
    fun delete(id: Int)

    @Query("SELECT * FROM saved_calculation")
    fun getAll(): List<SavedCalculation>

    @Query("DELETE FROM saved_calculation")
    fun clear()
}