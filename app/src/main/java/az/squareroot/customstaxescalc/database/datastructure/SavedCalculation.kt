package az.squareroot.customstaxescalc.database.datastructure

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "saved_calculation")
class SavedCalculation (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name: String = "",
    var taxes: Double = 0.0,
    var cargoPrice: Double = 0.0,
    @ColumnInfo(name = "total_price")
    var totalPrice: Double = 0.0,
    val date: Date
)