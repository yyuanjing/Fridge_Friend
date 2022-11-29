package com.fridgefriend

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class HistoryCardAdapter (
    ): RecyclerView.Adapter<HistoryCardAdapter.FoodCardViewHolder>() {

        private val data = DataSource.expired

        class FoodCardViewHolder(view: View?): RecyclerView.ViewHolder(view!!) {
            val foodImage : ImageView = view!!.findViewById(R.id.food_image)
            val foodName : TextView = view!!.findViewById(R.id.food_name_input)
            val foodQuantity : TextView = view!!.findViewById(R.id.food_quantity)
            val foodExpiration : TextView = view!!.findViewById(R.id.food_expiration)
            val qtyInput: TextView = view!!.findViewById(R.id.food_quantity_input)
            val expInput: TextView = view!!.findViewById(R.id.food_expiration_input)
            val expired : ImageView = view!!.findViewById(R.id.expired_image)
            val viewButton: Button = view!!.findViewById(R.id.view_button)
            val deleteButton: ImageButton = view!!.findViewById(R.id.delete_button)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodCardViewHolder {
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
            holder.viewButton.visibility = View.INVISIBLE
            holder.deleteButton.visibility = View.INVISIBLE
            holder.qtyInput.text = ""
            holder.expInput.text = ""
            if (thisFood.expired){
                holder.expired.alpha = 1f
            }
        }
}