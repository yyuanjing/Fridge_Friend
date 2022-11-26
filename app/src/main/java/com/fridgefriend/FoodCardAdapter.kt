package com.fridgefriend

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import kotlin.coroutines.coroutineContext


class FoodCardAdapter(
    private val recyclerViewInterface: RecyclerViewInterface
) : RecyclerView.Adapter<FoodCardAdapter.FoodCardViewHolder>() {

    private var data = DataSource.foods

    class FoodCardViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {
        val foodImage: ImageView = view!!.findViewById(R.id.food_image)
        val foodName: TextView = view!!.findViewById(R.id.food_name_input)
        val foodQuantity: TextView = view!!.findViewById(R.id.food_quantity_input)
        val foodExpiration: TextView = view!!.findViewById(R.id.food_expiration_input)
        val expired: ImageView = view!!.findViewById(R.id.expired_image)
        val viewButton: Button = view!!.findViewById(R.id.view_button)
        val deleteButton: ImageButton = view!!.findViewById(R.id.delete_button)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodCardViewHolder {
        val adapterLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.card_view_layout, parent, false)
        return FoodCardViewHolder(adapterLayout)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: FoodCardViewHolder, position: Int) {
        val thisFood = data[position]
        bindHelperMethod(holder, thisFood, position, recyclerViewInterface)
    }

    private fun bindHelperMethod(
        holder: FoodCardViewHolder,
        thisFood: Food,
        position: Int,
        recyclerViewInterface: RecyclerViewInterface
    ) {
        holder.foodImage.setImageResource(thisFood.imageResourceId)
        holder.foodName.text = thisFood.name
        holder.foodQuantity.text = thisFood.quantity
        holder.foodExpiration.text = thisFood.expiration_date
        if (thisFood.expired) {
            holder.expired.alpha = 1f
        }
        holder.viewButton.setOnClickListener { recyclerViewInterface.onItemClick(position) }
        holder.deleteButton.setOnClickListener { recyclerViewInterface.onItemClickDelete(position) }
    }

    fun filterList(filterlist: ArrayList<Food>) {
        // updating the data displayed
        data = filterlist
        // notify adapter of data change
        notifyDataSetChanged()
    }
}