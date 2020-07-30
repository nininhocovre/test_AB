package com.ab.exerciseabinbev.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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
        var productId: Int = 0
        // TODO: fetch image from server
        val image: ImageView
        val name: TextView
        val size: TextView
        val packageSize: TextView
        val price: TextView
        val quantity: EditText
        val plusButton: ImageView
        val minusButton: ImageView
        val addButton: Button

        init {
            image = itemView.findViewById<ImageView>(R.id.product_image)
            name = itemView.findViewById<TextView>(R.id.product_name_text)
            size = itemView.findViewById<TextView>(R.id.size_text)
            packageSize = itemView.findViewById<TextView>(R.id.package_size_text)
            price = itemView.findViewById<TextView>(R.id.price_text)
            quantity = itemView.findViewById<EditText>(R.id.quantity_edit)
            plusButton = itemView.findViewById<ImageView>(R.id.add_image)
            minusButton = itemView.findViewById<ImageView>(R.id.sub_image)
            addButton = itemView.findViewById<Button>(R.id.add_button)
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
        val thisOrder = orders[position]
        holder.productId = thisOrder.product.id
        holder.name.text = thisOrder.product.name
        holder.size.text = thisOrder.product.size
        holder.packageSize.text = thisOrder.product.packageSize
        holder.price.text = String.format("$%.2f", thisOrder.product.value)
        holder.quantity.setText(thisOrder.order.quantity.toString())
        holder.quantity.doAfterTextChanged {
            val order = getOrder(holder.productId)
            if (order != null) {
                val newValue: Int = it?.toString()?.toInt() ?: order.order.quantity
                viewModel.quantityChanged(order, newValue)
            }
        }
        holder.plusButton.setOnClickListener {
            val order = getOrder(holder.productId)
            if (order != null) {
                viewModel.adjustQuantity(order, 1)
            }
        }
        holder.minusButton.setOnClickListener {
            val order = getOrder(holder.productId)
            if (order != null) {
                viewModel.adjustQuantity(order, -1)
            }
        }
        if (thisOrder.order.purchased) {
            holder.addButton.text = ""
            holder.addButton.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.baseline_check_24, 0, 0, 0)
        } else {
            holder.addButton.text = holder.addButton.resources.getText(R.string.add_button_text)
            holder.addButton.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0)
        }
        holder.addButton.setOnClickListener {
            val order = getOrder(holder.productId)
            if (order != null) {
                viewModel.onAddButtonClicked(order)
            }
        }
    }

    private fun getOrder(productId: Int) : OrderWithProduct? {
        orders.forEach {
            if (it.product.id == productId) {
                return it
            }
        }
        return null
    }

    fun setOrder(orderList: List<OrderWithProduct>) {
        if (this.orders != orderList) {
            this.orders = orderList
            notifyDataSetChanged()
        }
    }
}
