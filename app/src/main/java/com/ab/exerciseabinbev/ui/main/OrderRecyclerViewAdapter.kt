package com.ab.exerciseabinbev.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ab.exerciseabinbev.R
import com.ab.exerciseabinbev.data.OrderWithProduct
import java.util.ArrayList

class OrderRecyclerViewAdapter : RecyclerView.Adapter<OrderRecyclerViewAdapter.ViewHolder>() {

    private var orders: List<OrderWithProduct> = ArrayList()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView
        val name: TextView
        val size: TextView
        val packageSize: TextView
        val price: TextView

        init {
            image = itemView.findViewById<ImageView>(R.id.product_image)
            name = itemView.findViewById<TextView>(R.id.product_name_text)
            size = itemView.findViewById<TextView>(R.id.size_text)
            packageSize = itemView.findViewById<TextView>(R.id.package_size_text)
            price = itemView.findViewById<TextView>(R.id.price_text)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return orders.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        orders[position].products[0].let {
            holder.name.text = it.name
            holder.size.text = it.size
            holder.packageSize.text = it.packageSize
            holder.price.text = String.format("$%.2f", it.value)
        }
    }

    fun setOrder(orderList: List<OrderWithProduct>) {
        this.orders = orderList
    }
}
