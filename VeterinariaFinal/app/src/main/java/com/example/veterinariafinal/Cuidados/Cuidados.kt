package com.example.veterinariafinal.Cuidados

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.example.veterinariafinal.R
import com.example.veterinariafinal.databinding.ActivityCuidadosBinding


class Cuidados : AppCompatActivity() {
    private lateinit var binding: ActivityCuidadosBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        this.setTitle(R.string.cuidados);
        binding= ActivityCuidadosBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val spinner= findViewById<Spinner>(R.id.spinnerCuidados)
        val lista= listOf("Seleccione mascota", "Golden Retriever", "Husky Siberiano")
        //Convertimos la lista en un adapter
        val adaptador= ArrayAdapter(this,android.R.layout.simple_spinner_item,lista)
        spinner.adapter= adaptador

        //Segun lo que seleccionemos en el spinner
        spinner.setOnItemSelectedListener(object : OnItemSelectedListener {

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {       //Cogemos la posicion numerica y segun lo que asignemos colocamos una imagen u otra.

                    1 -> binding.imageView5.setImageResource(R.drawable.goldenicon)
                    2 -> binding.imageView5.setImageResource(R.drawable.huskyicon)

                    else -> {

                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })


    }//onCreate
}