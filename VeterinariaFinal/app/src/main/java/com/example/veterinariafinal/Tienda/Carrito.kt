package com.example.veterinariafinal.Tienda

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.veterinariafinal.Menu
import com.example.veterinariafinal.R
import com.example.veterinariafinal.databinding.ActivityCarritoBinding


class Carrito : AppCompatActivity() {
    private lateinit var binding: ActivityCarritoBinding
    var titulo:String="carrito"

    override fun onBackPressed() {
        startActivity(Intent(this, Tienda::class.java))
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        this.setTitle(R.string.carrito);

        binding = ActivityCarritoBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initRecycler()
        binding.textCount.text="Total: "+ countCarrito.toString()+ "€"

        //Borramos carrito
        binding.bBorrarCarrito.setOnClickListener(){
            startActivity(Intent(this,Tienda::class.java))
            objeto1=mutableListOf()
            countCarrito=0
        }
    }//onCreate
    fun initRecycler() {
        binding.recyclercarrito.layoutManager = LinearLayoutManager(this)
        val adapter = CarritoAdapter(objeto1)
        binding.recyclercarrito.adapter = adapter
    }
}