package com.example.veterinariafinal.pkCitas

import android.app.DatePickerDialog
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.veterinariafinal.*
import com.example.veterinariafinal.Animales.MisAnimales
import com.example.veterinariafinal.databinding.ActivityRegistroCitaBinding


class RegistroCita : AppCompatActivity() {

    private lateinit var binding: ActivityRegistroCitaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityRegistroCitaBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //Sacamos las mascotas de la BD para el Spinner3
        //iniciamos la db donde comprobaremos cada animal
        val db = AdminSQLiteOpenHelper(this, "dbveterinaria", null, 1)
        val cursor = db.readableDatabase.rawQuery("SELECT * from tblanimales where id_usuario=$idUserCompartido ", null)
        val lista3= arrayListOf<String>("Seleccione Mascota")
        if (cursor.moveToFirst()) {
            do {
                //Cogemos los datos que necesitamos de la BBDD
                var nombre: String = cursor.getString(cursor.getColumnIndex("nombre"))

                //Los añadimos a nuestra lista)
                lista3.add(nombre)
                println(lista3)

            } while (cursor.moveToNext())
        }//Cursorfin

        val spinnerHoras= findViewById<Spinner>(R.id.spnElementos)
        val spinnerDoctores= findViewById<Spinner>(R.id.spnElementos2)
        val spinnerAnimales= findViewById<Spinner>(R.id.spnElementos3)
        val lista= listOf("Seleccione Hora", "09:00-09:30","09:30-10:00","10:00-10:30","10:30-11:00","11:00-11:30","11:30-12:00","12:30-13:00","13:00-13:30","13:30-14:00")
        val lista2= listOf("Seleccione Doctor", "Dr Manuel Garcia","Dra Maria Nuñez","Dr David Esparza","Dr Daniel Collado","Dra Yaiza Johnson ")


        //Convertimos la lista en un adapter
        val adaptador= ArrayAdapter(this,android.R.layout.simple_spinner_item,lista)
        val adaptador2= ArrayAdapter(this,android.R.layout.simple_spinner_item,lista2)
        val adaptador3=ArrayAdapter(this,android.R.layout.simple_spinner_item,lista3)
        //Seleccionador de Hora y doctor
        spinnerHoras.adapter= adaptador
        spinnerDoctores.adapter=adaptador2
        spinnerAnimales.adapter=adaptador3

        //setOnClickListener SELECCIONAR FECHA
        binding.etSelectDate.setOnClickListener{ showDatePickerDialog()}

        //validar campos
        binding.bValidarCita.setOnClickListener(){
            registroCita()
        }

    }

    private fun showDatePickerDialog() {
        val newFragment = DatePickerFragment.newInstance(DatePickerDialog.OnDateSetListener { _, year, month, day ->
            // +1 because January is zero
            val selectedDate = day.toString() + " / " + (month + 1) + " / " + year
            binding.etSelectDate.setText(selectedDate)
        })
        newFragment.show(supportFragmentManager, "datePicker")
    }

    fun registroCita() {
        if (binding.etSelectDate.text.isEmpty() || binding.spnElementos.selectedItem.toString()=="Seleccione Hora" || binding.etmotivo.text.isEmpty() ||
                binding.spnElementos2.selectedItem.toString()=="Seleccione Doctor" || binding.spnElementos3.selectedItem.toString()=="Seleccione Mascota") {
            Toast.makeText(this, "Rellene todos los campos", Toast.LENGTH_SHORT).show()
        } else {
            val cursor = AdminSQLiteOpenHelper(this, "dbveterinaria", null, 1)
            val bd = cursor.writableDatabase
            val registro = ContentValues()

            registro.put("fecha", binding.etSelectDate.text.toString()); registro.put("hora", binding.spnElementos.selectedItem.toString())
            registro.put("doctor", binding.spnElementos2.selectedItem.toString()); registro.put("mascota",binding.spnElementos3.selectedItem.toString())
            registro.put("id_usuario", idUserCompartido); registro.put("motivo",binding.etmotivo.text.toString())


            bd.insert("tblcitas", null, registro)
            bd.close()
            //Borrar campos
            binding.etSelectDate.setText(""); binding.etmotivo.setText(""); binding.spnElementos.setSelection(1);
            binding.spnElementos2.setSelection(1); binding.spnElementos3.setSelection(1);
            startActivity(Intent(this, Menu::class.java))
            finish()
        }
    }
}