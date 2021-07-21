package com.example.veterinariafinal.Tienda

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.veterinariafinal.databinding.ItemCarritoBinding

class CarritoAdapter(private val myItems: MutableList<DataCarrito>) : RecyclerView.Adapter<CarritoAdapter.ItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val binding = ItemCarritoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.render(myItems[position])
    }

    override fun getItemCount(): Int = myItems.size

    class ItemHolder(private val binding: ItemCarritoBinding): RecyclerView.ViewHolder(binding.root) {

        fun render(item: DataCarrito) {
            var precioFinal:String = "Precio: ${item.precio.toString()}€"
            binding.titulo.text=item.titulo
            binding.precio.text=item.precio+"€"
            binding.img.setImageBitmap(item.img)



        }
    }
}