package com.ab.exerciseabinbev.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.ab.exerciseabinbev.R
import com.ab.exerciseabinbev.data.OrderWithProduct
import java.util.ArrayList

class OrderRecyclerViewAdapter(val viewModel: MainViewModel) : RecyclerView.Adapter<OrderRecyclerViewAdapter.ViewHolder>() {

    private var orders: List<OrderWithProduct> = ArrayList()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var order: OrderWithProduct? = null

        // TODO: fetch image from server
        val image: ImageView
        val name: TextView
        val size: TextView
        val packageSize: TextView
        val price: TextView
        val quantity: EditText
        val plusButton: ImageView
        val minusButton: ImageView

        init {
            image = itemView.findViewById<ImageView>(R.id.product_image)
            name = itemView.findViewById<TextView>(R.id.product_name_text)
            size = itemView.findViewById<TextView>(R.id.size_text)
            packageSize = itemView.findViewById<TextView>(R.id.package_size_text)
            price = itemView.findViewById<TextView>(R.id.price_text)
            quantity = itemView.findViewById<EditText>(R.id.quantity_edit)
            plusButton = itemView.findViewById<ImageView>(R.id.add_image)
            minusButton = itemView.findViewById<ImageView>(R.id.sub_image)
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
        holder.order = orders[position]
        holder.order?.let { order ->
            holder.name.text = order.product.name
            holder.size.text = order.product.size
            holder.packageSize.text = order.product.packageSize
            holder.price.text = String.format("$%.2f", order.product.value)
            holder.quantity.setText(order.order.quantity.toString())
            holder.quantity.doAfterTextChanged {
                val newValue: Int = it?.toString()?.toInt() ?: order.order.quantity
                viewModel.quantityChanged(order, newValue)
            }
            holder.plusButton.setOnClickListener {
                viewModel.adjustQuantity(order, 1)
            }
            holder.minusButton.setOnClickListener {
                viewModel.adjustQuantity(order, -1)
            }
        }
    }

    fun setOrder(orderList: List<OrderWithProduct>) {
        this.orders = orderList
        notifyDataSetChanged()
    }
}
