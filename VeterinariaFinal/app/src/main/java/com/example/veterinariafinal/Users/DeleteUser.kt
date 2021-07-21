package com.example.veterinariafinal.Users

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.veterinariafinal.AdminSQLiteOpenHelper
import com.example.veterinariafinal.MainActivity
import com.example.veterinariafinal.Menu
import com.example.veterinariafinal.databinding.ActivityDeleteUserBinding

class DeleteUser : AppCompatActivity() {
    private lateinit var binding: ActivityDeleteUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityDeleteUserBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val db = AdminSQLiteOpenHelper(this, "dbveterinaria", null, 1)
        val cursor = db.writableDatabase

//        *************boton Confirmar borrado******
        binding.bConfirmar.setOnClickListener(){
            deleteUser()
        }
    }


    //****************Funciones***************
    fun deleteUser(){
        //Leemos los datos de la BBDD
        val db = AdminSQLiteOpenHelper(this, "dbveterinaria", null, 1)
        var cursor2= db.readableDatabase.rawQuery("SELECT * from tblusuarios",null)

        if (cursor2.moveToFirst()) {
            do {
                var user: String = cursor2.getString(cursor2.getColumnIndex("usuario"))
                var pass: String = cursor2.getString(cursor2.getColumnIndex("contraseña"))
                //Si es correcto, borramelo
                if (user == binding.txtUser.text.toString()&& pass == binding.txtPass.text.toString()) {
                    val bd = AdminSQLiteOpenHelper(this, "dbveterinaria", null, 1)
                    val cursor = bd.writableDatabase
                    val registro= cursor.delete("tblusuarios", "usuario='${binding.txtUser.text}'and contraseña='${binding.txtPass.text}'", null)
                    Toast.makeText(this, "Usuario borrado con exito", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    //Si no lo encuentras iniciame la actividad de nuevo y escribeme un mensaje
                }
            } while (cursor2.moveToNext())
            Toast.makeText(this, "Usuario no encontrado", Toast.LENGTH_SHORT).show()
        }
    }

    fun dropTable(){
        val db = AdminSQLiteOpenHelper(this, "dbveterinaria", null, 1)
        val cursor = db.writableDatabase
        cursor.execSQL("drop table if exists tblusuarios")
    }

}