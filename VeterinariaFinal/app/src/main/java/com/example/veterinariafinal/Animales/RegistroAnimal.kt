package com.example.veterinariafinal.Animales


import android.app.DatePickerDialog
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AppCompatActivity
import com.example.veterinariafinal.*
import com.example.veterinariafinal.databinding.ActivityRegistroAnimalBinding
import com.example.veterinariafinal.pkAnimales.DatePickerFragmentLimit
import java.io.ByteArrayOutputStream


class RegistroAnimal : AppCompatActivity() {
    private lateinit var binding: ActivityRegistroAnimalBinding
    var img: ImageView? =null  //variable para cargar la imagen de la galeria.
    var inputData: ByteArray? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegistroAnimalBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        img = findViewById(R.id.imgID)

        val spinnerSexo= findViewById<Spinner>(R.id.txtSexo)
        val spinnerEspecie= findViewById<Spinner>(R.id.spnEspecie)
        val spinnerRaza= findViewById<Spinner>(R.id.spnRaza)
        val listaSexo= listOf("Seleccione Sexo", "Macho", "Hembra")
        val listaEspecie= listOf("Seleccione Especie", "Perro", "Gato", "Conejo", "Otros")
        //la raza esta vacia, a la espera de la Especie.
        var listaRaza= listOf("Seleccione Raza")

        //Convertimos la lista en un adapter
        val adaptador= ArrayAdapter(this, android.R.layout.simple_spinner_item, listaSexo)
        spinnerSexo.adapter=adaptador
        val adaptador2= ArrayAdapter(this, android.R.layout.simple_spinner_item, listaEspecie)
        spinnerEspecie.adapter=adaptador2
        var adaptador3= ArrayAdapter(this, android.R.layout.simple_spinner_item, listaRaza)
        spinnerRaza.adapter=adaptador3

        //Selector de Raza segun especie Seleccionada.
        binding.spnEspecie.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                //Sirve para obtener el valor del spinne
                val especie: String = binding.spnEspecie.getItemAtPosition(position).toString()

                if (especie.equals("Perro")) {
                    listaRaza = listOf("Seleccione Raza", "Golden Retriever", "Pastor Aleman", "Pinscher", "Caniche", "Husky Siberiano", "Rottweiler", "Pomerania", "Galgo", "YorkShire", "Otros")
                    adaptador3 = ArrayAdapter(this@RegistroAnimal, android.R.layout.simple_spinner_item, listaRaza)
                    spinnerRaza.adapter = adaptador3
                } else if (especie.equals("Gato")) {
                    listaRaza = listOf("Seleccione Raza", "Whippet", "Siamés", "Bull", "Sphynx", "Sable", "Otros")
                    adaptador3 = ArrayAdapter(this@RegistroAnimal, android.R.layout.simple_spinner_item, listaRaza)
                    spinnerRaza.adapter = adaptador3
                } else if (especie.equals("Conejo")) {
                    listaRaza = listOf("Seleccione Raza", "Belier", "Neerlandes Enano", "Cabeza de león", "Mini Lop", "Mini Rex", "Angora", "Otros")
                    adaptador3 = ArrayAdapter(this@RegistroAnimal, android.R.layout.simple_spinner_item, listaRaza)
                    spinnerRaza.adapter = adaptador3
                } else if (especie.equals("Otros")) {
                    listaRaza = listOf("Sin Especificar")
                    adaptador3 = ArrayAdapter(this@RegistroAnimal, android.R.layout.simple_spinner_item, listaRaza)
                    spinnerRaza.adapter = adaptador3
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })//finSelection

        //*****************Seleccionar nacimiento*************
        binding.etSelectDate2.setOnClickListener{ showDatePickerDialog()}


        //************Registro animal****************
        binding.bRegistroAnimal.setOnClickListener() {
            newAnimal()
        }


    }//onCreate


        private fun showDatePickerDialog() {
            val newFragment = DatePickerFragmentLimit.newInstance(DatePickerDialog.OnDateSetListener { _, year, month, day ->
                // +1 because January is zero
                val selectedDate = day.toString() + " / " + (month + 1) + " / " + year
                binding.etSelectDate2.setText(selectedDate)
            })
            newFragment.show(supportFragmentManager, "datePicker")
        }

        //Escritura de datos en la activity Registro
        fun newAnimal() {
            if (binding.txtChip.text.isEmpty()|| binding.spnEspecie.selectedItem.toString()=="Seleccione Especie"|| binding.etSelectDate2.text.isEmpty()||
                binding.txtNombreAnimal.text.isEmpty()|| binding.spnRaza.selectedItem.toString()=="Seleccione Raza"|| binding.txtSexo.selectedItem.toString()=="Seleccione Sexo"){
                    Toast.makeText(this, "Rellene todos los campos", Toast.LENGTH_SHORT).show()
            }else{
                val cursor = AdminSQLiteOpenHelper(this, "dbveterinaria", null, 1)
                val bd = cursor.writableDatabase
                val registro = ContentValues()

                registro.put("chip", binding.txtChip.text.toString());                  registro.put("nombre", binding.txtNombreAnimal.text.toString())
                registro.put("nacimiento", binding.etSelectDate2.text.toString());      registro.put("sexo", binding.txtSexo.selectedItem.toString())
                registro.put("especie", binding.spnEspecie.selectedItem.toString());            registro.put("raza", binding.spnRaza.selectedItem.toString())
                registro.put("id_usuario", idUserCompartido);                           registro.put("propietario", nombreUserCompartido)
                /*Si la IMG esta vacia cogemos una por defecto, la convertirmos a BITMAP y
                posteriormente llamamos al metodo que lo convierte a Bytearray a la BD*/
                if (inputData==null){
                    val bitMap:Bitmap= BitmapFactory.decodeResource(resources, R.drawable.animalvacio)
                    img?.setImageBitmap(bitMap)
                    registro.put("img", bitmapToByteArray(bitMap))
                    //Y si no esta vacia la cargamos de la funcion cargarIMG.
                }else
                    registro.put("img", inputData)

                bd.insert("tblanimales", null, registro)
                bd.close()
                //Borrar campos
                binding.txtNombreAnimal.setText("");        binding.etSelectDate2.setText("");
                binding.txtChip.setText("");    binding.imgID.setImageURI(null)
                startActivity(Intent(this, MisAnimales::class.java))
                finish()
            }
        }//fun

    //Funcion convertir Bitmap to Bytearray para almacenar una imagen en la BBDD
    fun bitmapToByteArray(bitmap: Bitmap):ByteArray{
        ByteArrayOutputStream().apply {
            bitmap.compress(Bitmap.CompressFormat.PNG, 10, this)
            return toByteArray()
        }
    }

    fun onclick(view: View) {
        cargarimg()
    }

    private fun cargarimg() {
        //le indicamos de donde vamos a coger la imagen
        var intent= Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        intent.setType("image/")
        startActivityForResult(android.content.Intent.createChooser(intent, "Seleccione la app"), 10)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode== RESULT_OK){
            var path:Uri?=data?.data
            img?.setImageURI(path)

            //La convertimos a Bytearray para almacenarla en la DB
            inputData = path?.let { contentResolver.openInputStream(it)?.readBytes() }
            println(inputData)
        }

    }
}
