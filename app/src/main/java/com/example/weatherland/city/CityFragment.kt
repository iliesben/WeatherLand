package com.example.weatherland.city

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherland.App
import com.example.weatherland.App.Companion.database
import com.example.weatherland.Database
import com.example.weatherland.R

class CityFragment : Fragment(), CityAdapter.CityItemListener {

    private lateinit var cities: MutableList<City>
    private lateinit var database : Database
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CityAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = App.database
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
            val view = inflater.inflate(R.layout.fragment_city, container, false)
            recyclerView = view.findViewById(R.id.citiesRecyclerView)
            recyclerView.layoutManager = LinearLayoutManager(context)

            return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cities = database.getAllCities()
        adapter = CityAdapter(cities, this)
        recyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.fragment_city, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.actionCreateCity -> {
                showCreateCityDialog()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showCreateCityDialog() {
        val createCityFragment = CreateCityDialogFragment()
        createCityFragment.listener = object: CreateCityDialogFragment.CreateCityDialogListener{
            override fun onDialogPositiveClick(cityName: String) {
                saveCity(City(cityName))
            }
            override fun onDialogNegativeClick(){}
        }

        fragmentManager?.let { createCityFragment.show(it, "CreateCityDialogFragment") }
    }

    private fun saveCity(city: City) {
        if(database.createCity(city)) {
            cities.add(city)
            adapter.notifyDataSetChanged()
        }else{
            Toast.makeText(
                    context,
                    "Could not create city",
                    Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCitySelected(city: City) {

    }

    override fun onCityDeleted(city: City) {
        TODO("Not yet implemented")
    }
}