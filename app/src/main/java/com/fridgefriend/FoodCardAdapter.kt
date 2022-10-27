package com.fridgefriend

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
/** not sure if the bottom references are correct */
import com.example.cs371m_lab3.R
import com.example.cs371m_lab3.FridgeLayout
import com.example.cs371m_lab3.DataSource

class FoodCardAdapter (
    ): RecyclerView.Adapter<FoodCardAdapter.FoodCardViewHolder>() {

        private val data = DataSource.foods

        class FoodCardViewHolder(view: View?): RecyclerView.ViewHolder(view!!) {
            val foodImage : ImageView = view!!.findViewById(R.id.food_image)
            val foodName : TextView = view!!.findViewById(R.id.food_name)
            val foodQuantity : TextView = view!!.findViewById(R.id.food_quantity)
            val foodExpiration : TextView = view!!.findViewById(R.id.food_expiration)

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodCardViewHolder {

//            val adapterLayout = when(layout){
//                Layout.GRID -> {
//                    LayoutInflater.from(parent.context).inflate(R.layout.grid_list_item, parent, false)
//                }
//                else -> {
//                    LayoutInflater.from(parent.context).inflate(R.layout.vertical_horizontal_list_item, parent, false)
//                }
//            }
            val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.card_view_layout, parent, false)

            return FoodCardViewHolder(adapterLayout)
        }

        override fun getItemCount(): Int = data.size

        override fun onBindViewHolder(holder: FoodCardViewHolder, position: Int) {
            val thisFood = data[position]
            holder.foodImage.setImageResource(thisFood.imageResourceId)
            holder.foodName.text = thisFood.name
            holder.foodQuantity.text = thisFood.quantity
            holder.foodExpiration.text = thisFood.expiration_date

        }
}