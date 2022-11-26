package com.fridgefriend

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fridgefriend.databinding.FragmentFridgeBinding
import java.util.*
import java.util.Locale.filter
import kotlin.collections.ArrayList


class FridgeFragment : Fragment(), RecyclerViewInterface {
    private var _binding: FragmentFridgeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: FoodCardAdapter
    private val data = DataSource.foods

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFridgeBinding.inflate(inflater, container, false)
        val view = binding.root

        // Update the title to reflect this fragment's title
        (activity as AppCompatActivity).supportActionBar?.title = "Your Fridge"

        // getting the recyclerview by its id
        val recyclerview = view.findViewById<RecyclerView>(R.id.recyclerview)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(context)

        // This will pass the ArrayList to our Adapter
        adapter = FoodCardAdapter(this)

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter

        // Set listener for fab to add item if requested
        val fab = view.findViewById<View>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(context, AddItemActivity::class.java)
            startActivity(intent)
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(position: Int) {
        val intent = Intent(context, ViewItemActivity::class.java)
        intent.putExtra("position", position)
        startActivity(intent)
    }

    override fun onItemClickDelete(position: Int) {
        val alertBuilder = AlertDialog.Builder(requireContext())
        alertBuilder.setTitle("Deleting a food item")
        alertBuilder.setMessage("Are you sure you want to delete this item? This action will not " +
                "move this item to history. If you wish to move this item to history click remove " +
                "at the bottom of the item page when clicking \"View\"")
        alertBuilder.setPositiveButton("Delete item") { dialog, which ->
            deleteItem(position)
        }
        alertBuilder.setNegativeButton("Go back") { dialog, which -> }
        alertBuilder.show()
    }

    // Helper function to delete specified food item and update adapter of data change
    private fun deleteItem(position: Int){
        Toast.makeText(requireContext(), "Item deleted", Toast.LENGTH_SHORT).show()
        data.removeAt(position)
        adapter.notifyItemRemoved(position)
        adapter.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
        (activity as AppCompatActivity).supportActionBar?.title = "Your Fridge"
    }


    //TODO: implementation for search functionality, all methods below are in attempt for that
    fun filter(text: String) {
        // creating a new array list to filter our data.
        val filteredList = ArrayList<Food>()

        // running a for loop to compare elements.
        for (item in data) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.name.lowercase().contains(text.lowercase(Locale.getDefault()))) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredList.add(item)
            }
        }
        if (filteredList.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            //Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show()
        } else {
            // at last we are passing that filtered
            // list to our adapter class.

            adapter.filterList(filteredList)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.search -> {
                // getting search view of our item.
                val searchView = item.actionView as SearchView

                // below line is to call set on query text listener method.
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(newText: String): Boolean {
                        // inside on query text change method we are
                        // calling a method to filter our recycler view.
                        filter(newText)
                        return false
                    }
                })
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}