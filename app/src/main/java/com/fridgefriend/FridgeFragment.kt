package com.fridgefriend

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fridgefriend.databinding.FragmentFridgeBinding


class FridgeFragment : Fragment(), RecyclerViewInterface {
    private var _binding: FragmentFridgeBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private var isLinearLayoutManager = true
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

        (activity as AppCompatActivity).supportActionBar?.title = "Your Fridge"

        // getting the recyclerview by its id
        val recyclerview = view.findViewById<RecyclerView>(R.id.recyclerview)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(context)

        // This will pass the ArrayList to our Adapter
        val adapter = FoodCardAdapter(this)

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter

        val fab = view.findViewById<View>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(context, AddItemActivity::class.java)
            startActivity(intent)
        }

//        val web = view.findViewById<View>(R.id.weblaunchbutton)
//        web.setOnClickListener {
//            val webIntent: Intent = Uri.parse("https://www.bonappetit.com/recipes").let { webpage ->
//                Intent(Intent.ACTION_VIEW, webpage)
//            }
//        }

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
        data.removeAt(position)
        //TODO: update recycler view
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.title = "Your Fridge"
    }

}