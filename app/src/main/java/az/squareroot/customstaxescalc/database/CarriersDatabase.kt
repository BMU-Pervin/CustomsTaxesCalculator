package az.squareroot.customstaxescalc.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import az.squareroot.customstaxescalc.database.dao.CarrierDao
import az.squareroot.customstaxescalc.database.dao.PriceDao
import az.squareroot.customstaxescalc.database.dao.SavedCalculationDao
import az.squareroot.customstaxescalc.database.dao.WarehouseDao
import az.squareroot.customstaxescalc.database.datastructure.Carrier
import az.squareroot.customstaxescalc.database.datastructure.Price
import az.squareroot.customstaxescalc.database.datastructure.SavedCalculation
import az.squareroot.customstaxescalc.database.datastructure.Warehouse

@Database(entities = [Carrier::class, Price::class, Warehouse::class, SavedCalculation::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class CarriersDatabase: RoomDatabase() {

    abstract val carrierDao: CarrierDao
    abstract val priceDao: PriceDao
    abstract val warehouseDao: WarehouseDao
    abstract val savedCalculationDao: SavedCalculationDao

    companion object {

        @Volatile
        private var INSTANCE: CarriersDatabase? = null

        fun getInstance(context: Context): CarriersDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CarriersDatabase::class.java,
                        "carriers_database"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}