package az.squareroot.customstaxescalc.database.datastructure

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "prices", foreignKeys = [ForeignKey(entity = Carrier::class, parentColumns = ["id"], childColumns = ["carrier_id"], onDelete = ForeignKey.CASCADE)])
class Price(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var price: Double = 0.0,
    var startWeight: Double = 0.0,
    var endWeight: Double = 0.0,
    @ColumnInfo(name = "carrier_id")
    var carrierId: Int = 0
)