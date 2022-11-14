package com.fridgefriend

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import kotlin.coroutines.coroutineContext


class FoodCardAdapter (
    private val recyclerViewInterface: RecyclerViewInterface
    ): RecyclerView.Adapter<FoodCardAdapter.FoodCardViewHolder>() {

        private val data = DataSource.foods

        class FoodCardViewHolder(view: View?): RecyclerView.ViewHolder(view!!) {
            val foodImage : ImageView = view!!.findViewById(R.id.food_image)
            val foodName : TextView = view!!.findViewById(R.id.food_name)
            val foodQuantity : TextView = view!!.findViewById(R.id.food_quantity)
            val foodExpiration : TextView = view!!.findViewById(R.id.food_expiration)
            val foodDateAdded : TextView = view!!.findViewById(R.id.date_added)
            val expired : ImageView = view!!.findViewById(R.id.expired_image)
            val view_button : Button = view!!.findViewById(R.id.view_button)

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodCardViewHolder {
            val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.card_view_layout, parent, false)
            return FoodCardViewHolder(adapterLayout)
        }

        override fun getItemCount(): Int = data.size

        override fun onBindViewHolder(holder: FoodCardViewHolder, position: Int) {
            val thisFood = data[position]
            bindHelperMethod(holder, thisFood, position, recyclerViewInterface)
        }

        private fun bindHelperMethod(holder: FoodCardViewHolder, thisFood: Food, position: Int, recyclerViewInterface: RecyclerViewInterface){
            holder.foodImage.setImageResource(thisFood.imageResourceId)
            holder.foodName.text = thisFood.name
            holder.foodQuantity.text = thisFood.quantity
            holder.foodExpiration.text = thisFood.expiration_date
            holder.foodDateAdded.text = thisFood.adding_date
            if (thisFood.expired){
                holder.expired.alpha = 1f
            }
            holder.view_button.setOnClickListener{recyclerViewInterface.onItemClick(position)}
        }
}