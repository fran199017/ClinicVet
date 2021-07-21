package com.example.veterinariafinal.Animales

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View.VISIBLE
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.example.veterinariafinal.AdminSQLiteOpenHelper

import com.example.veterinariafinal.databinding.CitarecyclerBinding
import com.example.veterinariafinal.databinding.ItemrecyclerBinding


class CitasAdapter(private val misCitas: List<DataCitas>): RecyclerView.Adapter<CitasAdapter.ItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {

        val binding = CitarecyclerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
        )
        return ItemHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.render(misCitas[position])

    }
    override fun getItemCount(): Int = misCitas.size








    class ItemHolder(private val binding: CitarecyclerBinding): RecyclerView.ViewHolder(binding.root) {

        //***********************Borrado de mascota*******************************
        fun deleteCita(itemsCitas: DataCitas){
            val db = AdminSQLiteOpenHelper(binding.itemcitas.context, "dbveterinaria", null, 1)
            val cursor2 = db.writableDatabase
            val registro= cursor2.delete("tblcitas", "motivo='${itemsCitas.motivo}'", null)
            //Volvemos a cargar la activity
            binding.itemcitas.context.startActivity(Intent(binding.itemcitas.context, MisAnimales::class.java))
            (binding.itemcitas.context as Activity).finish()
        }

        fun render(itemsCitas: DataCitas) {
            binding.fecha2.setText(itemsCitas.fecha)
            binding.hora2.setText(itemsCitas.hora)
            binding.doctor2.setText(itemsCitas.doctor)
            binding.motivo2.setText(itemsCitas.motivo)

            binding.bDelete.setOnClickListener(){
                binding.bConfirmardelete2.visibility= VISIBLE
                binding.bConfirmardelete2.isEnabled=true
            }
            //una vez esto, podemos borrar.
            binding.bConfirmardelete2.setOnClickListener(){
                deleteCita(itemsCitas)
            }

        }
    }
}



