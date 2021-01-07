package az.squareroot.customstaxescalc.database

import android.app.Application
import android.util.Log
import az.squareroot.customstaxescalc.*
import az.squareroot.customstaxescalc.database.datastructure.Carrier
import az.squareroot.customstaxescalc.database.datastructure.Price
import az.squareroot.customstaxescalc.database.datastructure.Warehouse
import kotlinx.coroutines.*

object Carriers {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private lateinit var application: Application
    private lateinit var database: CarriersDatabase

    @Volatile
    var INSTANCE: ArrayList<Carrier> = ArrayList()

    fun startFetching() {
        synchronized(this) {
            Log.i("Carriers", "Fetching started.")
            uiScope.launch {
                val carriers = getCarriers()
                for (carrier in carriers) {
                    val prices = ArrayList<Price>()
                    for (price in getPrices(carrier.id)) {
                        prices.add(price)
                    }
                    val warehouses = ArrayList<String>()
                    for (warehouse in getWarehouses(carrier.id)) {
                        warehouses.add(warehouse.toString())
                    }
                    carrier.prices = prices
                    carrier.warehouses = warehouses
                }
                INSTANCE = carriers as ArrayList<Carrier>
                Log.i("Carriers", "Fetching ended.")
            }
        }
    }

    private suspend fun getCarriers(): List<Carrier> {
        return withContext(Dispatchers.IO) {
            database.carrierDao.getAll()
        }
    }

    private suspend fun getPrices(carrierId: Int): List<Price> {
        return withContext(Dispatchers.IO) {
            database.priceDao.findAll(carrierId)
        }
    }

    private suspend fun getWarehouses(carrierId: Int): List<Warehouse> {
        return withContext(Dispatchers.IO) {
            database.warehouseDao.findAll(carrierId)
        }
    }

    fun setDB(application: Application) {
        Carriers.application = application
        database = CarriersDatabase.getInstance(application)
    }

    fun insert() {
        Log.i("Carriers", "Insert process started.")
        val carriers = ArrayList<Carrier>()

        val limakPrices: ArrayList<Price> = ArrayList()
        limakPrices.add(Price(0, 1.99, 0.0, 0.25, 1))
        limakPrices.add(Price(0, 3.99, 0.25, 0.5, 1))
        limakPrices.add(Price(0, 4.99, 0.5, 0.7, 1))
        limakPrices.add(Price(0, 5.99, 0.7, 100.0, 1))

        val moverPrices: ArrayList<Price> = ArrayList()
        moverPrices.add(Price(0, 3.5, 0.0, 0.1, 2))
        moverPrices.add(Price(0, 4.5, 0.1, 0.25, 2))
        moverPrices.add(Price(0, 5.5, 0.25, 0.5, 2))
        moverPrices.add(Price(0, 6.5, 0.5, 0.75, 2))
        moverPrices.add(Price(0, 7.5, 0.75, 100.0, 2))

        val camexPrices: ArrayList<Price> = ArrayList()
        camexPrices.add(Price(0, 2.45, 0.0, 0.25, 3))
        camexPrices.add(Price(0, 4.45, 0.25, 0.5, 3))
        camexPrices.add(Price(0, 5.95, 0.5, 0.75, 3))
        camexPrices.add(Price(0, 7.45, 0.75, 0.99, 3))
        camexPrices.add(Price(0, 7.95, 1.0, 100.0, 3))

        val starexPrices: ArrayList<Price> = ArrayList()
        starexPrices.add(Price(0, 2.5, 0.0, 0.25, 4))
        starexPrices.add(Price(0, 4.0, 0.25, 0.5, 4))
        starexPrices.add(Price(0, 5.5, 0.5, 0.75, 4))
        starexPrices.add(Price(0, 6.9, 0.75, 1.0, 4))
        starexPrices.add(Price(0, 6.5, 1.0, 5.0, 4))
        starexPrices.add(Price(0, 6.0, 5.0, 10.0, 4))
        starexPrices.add(Price(0, 5.5, 10.0, 100.0, 4))

        val baramiPrices: ArrayList<Price> = ArrayList()
        baramiPrices.add(Price(0, 4.0, 0.0, 0.5, 5))
        baramiPrices.add(Price(0, 7.9, 0.5, 1.0, 5))
        baramiPrices.add(Price(0, 7.5, 1.0, 30.0, 5))

        val vipexPrices: ArrayList<Price> = ArrayList()
        vipexPrices.add(Price(0, 2.0, 0.0, 0.1, 6))
        vipexPrices.add(Price(0, 2.5, 0.1, 0.25, 6))
        vipexPrices.add(Price(0, 4.0, 0.25, 0.5, 6))
        vipexPrices.add(Price(0, 5.0, 0.5, 0.75, 6))
        vipexPrices.add(Price(0, 6.5, 0.75, 100.0, 6))

        val safeExpressPrices: ArrayList<Price> = ArrayList()
        safeExpressPrices.add(Price(0, 2.37, 0.0, 0.25, 7))
        safeExpressPrices.add(Price(0, 3.37, 0.25, 0.5, 7))
        safeExpressPrices.add(Price(0, 4.37, 0.5, 0.7, 7))
        safeExpressPrices.add(Price(0, 4.47, 0.7, 100.0, 7))

        carriers.add(
            Carrier(
                1,
                "Limak",
                limakPrices,
                "Limak",
                R.drawable.logo_limak,
                "#FFA000",
                "#E65100",
                arrayOf(
                    application.baseContext.getString(R.string.warehouse_baku),
                    application.baseContext.getString(R.string.warehouse_ganja),
                    application.baseContext.getString(R.string.warehouse_sumgayit),
                    application.baseContext.getString(R.string.warehouse_zagatala)
                ).asList()
            )
        )
        carriers.add(
            Carrier(
                2,
                "Mover",
                moverPrices,
                "Limak",
                R.drawable.logo_mover,
                "#ED213A",
                "#93291E",
                arrayOf(
                    application.baseContext.getString(R.string.warehouse_baku),
                    application.baseContext.getString(R.string.warehouse_ganja),
                    application.baseContext.getString(R.string.warehouse_sumgayit)
                ).asList()
            )
        )
        carriers.add(
            Carrier(
                3,
                "Camex",
                camexPrices,
                "Limak",
                R.drawable.logo_camex,
                "#0575E6",
                "#021B79",
                arrayOf(
                    application.baseContext.getString(R.string.warehouse_baku),
                    application.baseContext.getString(R.string.warehouse_ganja),
                    application.baseContext.getString(R.string.warehouse_sumgayit)
                ).asList()
            )
        )
        carriers.add(
            Carrier(
                4,
                "Starex",
                starexPrices,
                "Limak",
                R.drawable.logo_starex,
                "#ffa751",
                "#ffe259",
                arrayOf(
                    application.baseContext.getString(R.string.warehouse_baku),
                    application.baseContext.getString(R.string.warehouse_ganja),
                    application.baseContext.getString(R.string.warehouse_sumgayit)
                ).asList()
            )
        )

        carriers.add(
            Carrier(
                5,
                "Barami",
                baramiPrices,
                "Limak",
                R.drawable.logo_barami,
                "#0072FF",
                "#00C6FF",
                arrayOf(
                    application.baseContext.getString(R.string.warehouse_baku)
                ).asList()
            )
        )

        carriers.add(
            Carrier(
                6,
                "Vipex",
                vipexPrices,
                "Limak",
                R.drawable.logo_vipex,
                "#19547B",
                "#FFD89B",
                arrayOf(
                    application.baseContext.getString(R.string.warehouse_baku)
                ).asList()
            )
        )

        carriers.add(
            Carrier(
                7,
                "SafeExpress",
                safeExpressPrices,
                "Limak",
                R.drawable.logo_safex,
                "#26D0CE",
                "#1A2980",
                arrayOf(
                    application.baseContext.getString(R.string.warehouse_baku),
                    application.baseContext.getString(R.string.warehouse_ganja)
                ).asList()
            )
        )

        insert(carriers)
        //viewModel.clear()
    }

    private fun insert(carriers: ArrayList<Carrier>) {
        uiScope.launch {
            Log.i("Carriers", "carriers.size() = " + carriers.size)
            for (carrier in carriers) {
                Log.i("Carriers", "Inside carrier for")
                insertCarrier(carrier)
                for (price in carrier.prices) {
                    Log.i("Carriers", "Inside price for")
                    insertPrice(price)
                }
                for (warehouse in carrier.warehouses) {
                    Log.i("Carriers", "Inside warehouse for")
                    Log.i("Carriers", "Warehouses:" + "\n\t" + warehouse + "\t${carrier.id}")
                    insertWarehouse(Warehouse(name = warehouse, carrierId = carrier.id))
                }
            }
            startFetching()
        }
    }

    private suspend fun insertPrice(price: Price) {
        withContext(Dispatchers.IO) {
            database.priceDao.insert(price)
            Log.i("Carriers", "Inserted price " + price.carrierId)
        }
    }

    private suspend fun insertWarehouse(warehouse: Warehouse) {
        withContext(Dispatchers.IO) {
            try {
                database.warehouseDao.insert(warehouse)
                Log.i("Carriers", "Inserted warehouse " + warehouse.carrierId)
            } catch (e: Exception) {
                Log.i("Carriers", "Error warehouse " + e.message)
            }
        }
    }

    private suspend fun insertCarrier(carrier: Carrier) {
        withContext(Dispatchers.IO) {
            database.carrierDao.insert(carrier)
            Log.i("Carriers", "Inserted carrier " + carrier.id)
        }
    }

    fun clear() {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                database.carrierDao.clear()
                database.priceDao.clear()
            }
        }
    }

    fun delete(carrier: Carrier) {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                database.carrierDao.delete(carrier)
            }
        }
    }
}