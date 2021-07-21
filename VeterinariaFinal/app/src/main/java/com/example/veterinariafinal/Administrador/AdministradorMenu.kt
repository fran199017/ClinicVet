package com.example.veterinariafinal.Administrador

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.veterinariafinal.MainActivity
import com.example.veterinariafinal.Menu
import com.example.veterinariafinal.R
import com.example.veterinariafinal.databinding.ActivityAdministradorMenuBinding
import com.example.veterinariafinal.flagAdmin


class AdministradorMenu : AppCompatActivity() {
    private lateinit var binding: ActivityAdministradorMenuBinding

    override fun onBackPressed() {
        flagAdmin=false
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        this.setTitle(R.string.admin);
        binding = ActivityAdministradorMenuBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)



        //Publicaciones
        binding.bPublicar.setOnClickListener(){ startActivity(Intent(this, PublicarCuriosidades::class.java))}
        binding.bPublicar2.setOnClickListener(){ startActivity(Intent(this, PublicarProducto::class.java))}
    }
}