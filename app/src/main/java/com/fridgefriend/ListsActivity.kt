package com.fridgefriend

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import android.view.MenuInflater
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import com.fridgefriend.databinding.ActivityMainBinding
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*
import java.util.Locale.filter
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import kotlin.collections.ArrayList


class ListsActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private val data = DataSource.foods
    private lateinit var adapter: FoodCardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_lists)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        navController = navHostFragment.navController

        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.fridgeFragment, R.id.shoppingFragment, R.id.historyFragment)
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNav.setupWithNavController(navController)


        // TODO: implement search functionality
        //if (Intent.ACTION_SEARCH == intent.action) {
        //    val query = intent.getStringExtra(SearchManager.QUERY)
        //    //use the query to search your data somehow
        //}

    }

     override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)

//     Associate searchable configuration with the SearchView
//        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
//        if (menu != null) {
//            val searchView = menu.findItem(R.id.search).actionView as SearchView
//            searchView.queryHint = "Type here to search"
//     searchView.apply {
//        setSearchableInfo(searchManager.getSearchableInfo(componentName))
//    }
//            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//
//            })
//        }

        return true
    }

    // calling on create option menu
    // layout to inflate our menu file.
    /*override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // below line is to get our inflater
        val inflater = menuInflater

        // inside inflater we are inflating our menu file.
        inflater.inflate(R.menu.options_menu, menu)


        // below line is to get our menu item.
        val searchItem = menu.findItem(R.id.search)

        // getting search view of our item.
        val searchView = searchItem.actionView as SearchView

        // below line is to call set on query text listener method.
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                // inside on query text change method we are
                // calling a method to filter our recycler view.
                val fragment = supportFragmentManager.findFragmentById(R.id.fridgeFragment)
                fragment.
                FridgeFragment.filter(newText)
                fragment.filter(newText)
                filter(newText)
                return false
            }
        })
        return true
    }*/


}