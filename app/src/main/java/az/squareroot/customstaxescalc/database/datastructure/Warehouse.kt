package az.squareroot.customstaxescalc.database.datastructure

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "warehouses", foreignKeys = [ForeignKey(entity = Carrier::class, parentColumns = ["id"], childColumns = ["carrier_id"], onDelete = ForeignKey.CASCADE)])
class Warehouse(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name: String = "",
    @ColumnInfo(name = "carrier_id")
    var carrierId: Int = 0
) {
    override fun toString(): String {
        return this.name
    }
}