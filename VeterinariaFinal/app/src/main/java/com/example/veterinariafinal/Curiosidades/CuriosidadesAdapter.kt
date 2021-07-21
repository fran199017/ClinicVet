package com.example.veterinariafinal.Curiosidades

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.veterinariafinal.databinding.CuriosidadesrecyclerBinding

class CuriosidadesAdapter(private val misCuriosidades: List<DataCuriosidades>): RecyclerView.Adapter<CuriosidadesAdapter.ItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {

        val binding = CuriosidadesrecyclerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
        )
        return ItemHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.render(misCuriosidades[position])

    }
    override fun getItemCount(): Int = misCuriosidades.size




    class ItemHolder(private val binding: CuriosidadesrecyclerBinding): RecyclerView.ViewHolder(binding.root) {

        fun render(itemsCuriosidades: DataCuriosidades) {
            binding.imageView4.setImageBitmap(itemsCuriosidades.img)
            binding.labelTitulo.setText(itemsCuriosidades.titulo)
            binding.textCuriosidad.setText(itemsCuriosidades.curiosidad)
            binding.labelAnimal.setText(itemsCuriosidades.especie)


        }
    }
}
