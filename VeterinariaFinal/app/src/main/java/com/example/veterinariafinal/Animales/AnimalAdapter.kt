package com.example.veterinariafinal.Animales

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View.VISIBLE
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.example.veterinariafinal.AdminSQLiteOpenHelper
import com.example.veterinariafinal.databinding.ItemrecyclerBinding


class AnimalAdapter(private val misAnimales: List<DataAnimales>): RecyclerView.Adapter<AnimalAdapter.ItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {

        val binding = ItemrecyclerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
        )
        return ItemHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.render(misAnimales[position])

    }
    override fun getItemCount(): Int = misAnimales.size




    class ItemHolder(private val binding: ItemrecyclerBinding): RecyclerView.ViewHolder(binding.root) {

        //***********************Borrado de mascota*******************************
        fun deleteAnimal(itemAnimales: DataAnimales){
            val db = AdminSQLiteOpenHelper(binding.item.context, "dbveterinaria", null, 1)
            val cursor2 = db.writableDatabase
            val registro= cursor2.delete("tblanimales", "id='${itemAnimales.id}' and nombre='${binding.itemNombre.text}'", null)
            //Volvemos a cargar la activity
            binding.item.context.startActivity(Intent(binding.item.context, MisAnimales::class.java))
            (binding.item.context as Activity).finish()
        }



        fun render(itemAnimales: DataAnimales) {
            binding.itemIMG.setImageBitmap(itemAnimales.img)
            binding.itemChip.setText(itemAnimales.chip)
            binding.itemNombre.setText(itemAnimales.nombre)
            //Cuando pulsemos el boton borrar mascota, lo ponemos visible.
            binding.bBorrarMascota.setOnClickListener(){
                binding.bConfirmardelete.visibility= VISIBLE
                binding.bConfirmardelete.isEnabled=true
            }
            //una vez esto, podemos borrar.
            binding.bConfirmardelete.setOnClickListener(){
                deleteAnimal(itemAnimales)
            }

            binding.itemIMG.setOnClickListener(){
                val intent= Intent(binding.item.context, HistorialAnimal::class.java)
                    .putExtra("nombre", itemAnimales.nombre)
                binding.item.context.startActivity(intent)

            }


        }
    }
}





