package com.example.veterinariafinal.Animales

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TableRow
import androidx.recyclerview.widget.GridLayoutManager
import com.example.veterinariafinal.AdminSQLiteOpenHelper
import com.example.veterinariafinal.databinding.ActivityHistorialAnimalBinding
import com.example.veterinariafinal.idUserCompartido


class HistorialAnimal : AppCompatActivity() {
    var nombre:String=""
    var itemsCitas: MutableList<DataCitas> = mutableListOf()
    private lateinit var binding: ActivityHistorialAnimalBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityHistorialAnimalBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        //**********Intents**********
        val objectIntent: Intent = intent
        nombre = objectIntent.getStringExtra("nombre").toString()

        binding.txtDatos.setText("Nombre: "+ nombre)

        //Leemos los datos de la BBDD
        val db = AdminSQLiteOpenHelper(this, "dbveterinaria", null, 1)
        var cursor= db.readableDatabase.rawQuery("SELECT * from tblanimales where id_usuario=${"'"+ idUserCompartido +"'"} and nombre=${"'"+nombre+"'"} ",null)

        //Cogemos los datos del historial del animal.
        if (cursor.moveToFirst()) {

            var img: ByteArray = cursor.getBlob(cursor.getColumnIndex("img"))
            var nombre: String = cursor.getString(cursor.getColumnIndex("nombre"))
            var chip: String = cursor.getString(cursor.getColumnIndex("chip"))
            var nacimiento: String = cursor.getString(cursor.getColumnIndex("nacimiento"))
            var sexo: String = cursor.getString(cursor.getColumnIndex("sexo"))
            var especie: String = cursor.getString(cursor.getColumnIndex("especie"))
            var raza: String = cursor.getString(cursor.getColumnIndex("raza"))
            var propietario: String = cursor.getString(cursor.getColumnIndex("propietario"))
            //Los asignamos al area de texto.
            binding.imageButton.setImageBitmap(convertirIMG(img))
            binding.txtDatos.setText("Nombre: " + nombre + "\n Chip: " + chip + "\n Nacimiento: " + nacimiento + "\n Sexo: " + sexo + "\n Especie: " + especie + "\n Raza: " + raza + "\n Propietario: " + propietario)




        }
        //Cogemos los datos de las citas
        //Leemos los datos de la BBDD
        var cursor2= db.readableDatabase.rawQuery("SELECT * from tblcitas where id_usuario=${"'"+ idUserCompartido +"'"} and mascota=${"'"+nombre+"'"} order by fecha ",null)
        //Cogemos los datos del historial del animal.
        if (cursor2.moveToFirst()) {
            var contador:Int=1
            do {
                //asignamos los valores a cada campo

                var fecha:String= cursor2.getString(cursor2.getColumnIndex("fecha"))
                var hora:String= cursor2.getString(cursor2.getColumnIndex("hora"))
                var doctor:String= cursor2.getString(cursor2.getColumnIndex("doctor"))
                var motivo:String= cursor2.getString(cursor2.getColumnIndex("motivo"))

                //Los añadimos a nuestra lista de items y a su vez al dataclas
                var objeto1= DataCitas(fecha, hora, doctor, motivo)
                itemsCitas.add(objeto1)
                contador++
            }while (cursor2.moveToNext())
        }
        initRecycler()
    }

    fun initRecycler() {
        binding.recyclercitas.layoutManager = GridLayoutManager(this, 1)
        val adapter = CitasAdapter(itemsCitas)
        binding.recyclercitas.adapter = adapter
    }

    //Funcion para devolver la imagen de la base de datos y cargarla en el dataclass
    fun convertirIMG(img:ByteArray):Bitmap{
        var bitmap= BitmapFactory.decodeByteArray(img , 0, img.size)
        return bitmap
    }
}