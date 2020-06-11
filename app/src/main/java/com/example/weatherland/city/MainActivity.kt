package com.example.weatherland.city

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.weatherland.R
import com.example.weatherland.weather.WeatherActivity
import com.example.weatherland.weather.WeatherFragment
import com.google.android.gms.location.*
import java.io.IOException
import java.util.*

class MainActivity : AppCompatActivity(), CityFragment.CityFragmentListener {

    private lateinit var cityFragment: CityFragment
    private var currentCity: City? = null

    //private var latidute: City? = null
    //private var longitude: City? = null

    private val LOCATION_REQUEST_CODE = 1
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var geofencingClient: GeofencingClient

    private val locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            if(locationResult != null) {
                val location = locationResult.lastLocation
                Log.i("myLocation", " kourouna like $location" )
            }
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cityFragment = supportFragmentManager.findFragmentById(R.id.cityFragment) as CityFragment
        cityFragment.listener = this


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        geofencingClient = LocationServices.getGeofencingClient(this)

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), LOCATION_REQUEST_CODE)
        }
        else{
            this.requestLocationUpdate()
        }

       this.geocoding()


    }

    private fun geocoding() {
        val geocoder = Geocoder(this, Locale.getDefault())

        var addresses = emptyList<Address>()
        try {
            addresses = geocoder.getFromLocation(48.8583894, 2.2945836, 1)
            Log.i("address", "$addresses")
            for (address in addresses) {
                val maxAddressLine = address.maxAddressLineIndex

                for (i in 0 until maxAddressLine) {
                    val lineAddress = address.getAddressLine(i)
                    Log.d("address", lineAddress)
                }
            }
        } catch (e: IOException) {
            Log.i("address", "erreur de récupération d'adresse")
        }

    }


    private fun requestLocationUpdate() {
        fusedLocationClient.lastLocation.addOnSuccessListener{ location ->
           Log.i("myLocation", " kourouna like ${location.latitude}  ${location.longitude}" )
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_REQUEST_CODE) { if (permissions.isNotEmpty() &&
            permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED ){
                this.requestLocationUpdate()
            }
        } else {
            Toast.makeText(this, getString(R.string.permissionNotAllowed), Toast.LENGTH_LONG).show()
        }
    }

    private fun startWeatherActivity(city: City) {
        val intent = Intent(this, WeatherActivity::class.java)
        intent.putExtra(WeatherFragment.EXTRA_CITY_NAME, city.name)
        startActivity(intent)
    }

    override fun onCitySelected(city: City) {
        currentCity = city
        startWeatherActivity(city)
    }

    override fun onEmptyCities() {}

    override fun onSelectionCleared() {
        cityFragment.selectFirstCity()
    }

}
