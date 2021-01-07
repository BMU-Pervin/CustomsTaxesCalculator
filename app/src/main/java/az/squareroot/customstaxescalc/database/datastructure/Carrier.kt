package az.squareroot.customstaxescalc.database.datastructure

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "carriers")
class Carrier(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name: String = "",
    @Ignore
    var prices: List<Price> = ArrayList(),
    @ColumnInfo(name = "short_description")
    var shortDescription: String = "",
    @ColumnInfo(name = "image_id")
    var imageId: Int = 0,
    @ColumnInfo(name = "start_color")
    var startColor: String = "",
    @ColumnInfo(name = "end_color")
    var endColor: String = "",
    @Ignore
    var warehouses: List<String> = ArrayList()
) {
    constructor(name: String, prices: List<Price>, shortDescription: String, imageId: Int, startColor: String, endColor: String, warehouses: List<String>) : this() {
        this.name = name
        this.prices = prices
        this.shortDescription = shortDescription
        this.imageId = imageId
        this.startColor = startColor
        this.endColor = endColor
        this.warehouses = warehouses
    }
}