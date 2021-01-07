package az.squareroot.customstaxescalc

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import az.squareroot.customstaxescalc.database.Carriers
import com.google.android.gms.ads.*
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var bottomNavView: BottomNavigationView
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editPreferences: SharedPreferences.Editor

    private lateinit var adView : AdView

    private val navigationListener = NavController.OnDestinationChangedListener { _, destination, _ ->
        if (destination.id == R.id.navigation_settings) {
            bottomNavView.visibility = View.GONE
        } else {
            bottomNavView.visibility = View.VISIBLE
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Carriers.setDB(this.application)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        editPreferences = sharedPreferences.edit()
        val hey = sharedPreferences.getBoolean("inserted", false)
        if (!sharedPreferences.getBoolean("inserted", false)) {
            try {
                Carriers.insert()
                Log.i("MainActivity", "Inserted.")
            } catch (e: Exception) {
                Log.i("MainActivity", "Error on insert: " + e.message)
            }
            editPreferences.putBoolean("inserted", true)
            editPreferences.apply()
        } else {
            Carriers.startFetching()
        }
        Log.i("MainActivity", "inserted: $hey")
        MobileAds.initialize(this) {}
        adView = findViewById(R.id.adview_footer)
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
        bottomNavView = findViewById(R.id.bottom_nav_view)
        navController = findNavController(R.id.nav_host_fragment)
        bottomNavView.setupWithNavController(navController)

        adView.adListener = object: AdListener() {
            override fun onAdLoaded() {
                Log.i("MainActivity-Ad", "Ad Loaded")
            }

            override fun onAdFailedToLoad(adError : LoadAdError) {
                Log.i("MainActivity-Ad", "Ad Failed to Load ${adError.message}")
            }

            override fun onAdOpened() {
                Log.i("MainActivity-Ad", "Ad opened")
            }

            override fun onAdClicked() {
                Log.i("MainActivity-Ad", "Ad clicked")
            }

            override fun onAdLeftApplication() {
                Log.i("MainActivity-Ad", "Ad left")
            }

            override fun onAdClosed() {
                Log.i("MainActivity-Ad", "Ad closed")
            }
        }
    }

    override fun onResume() {
        super.onResume()
        navController.addOnDestinationChangedListener(navigationListener)
    }

    override fun onPause() {
        super.onPause()
        navController.removeOnDestinationChangedListener(navigationListener)
    }

    override fun onStop() {
        super.onStop()
        when (sharedPreferences.getBoolean("save_carrier", true)) {
            false -> {
                editPreferences.putInt("selected_carrier", -1)
                editPreferences.commit()
            }
        }
    }
}