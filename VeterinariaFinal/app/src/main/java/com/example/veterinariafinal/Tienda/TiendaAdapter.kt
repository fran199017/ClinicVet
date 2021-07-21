package com.example.veterinariafinal.Tienda


import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.veterinariafinal.databinding.TiendarecyclerBinding

//variable global del carrito.
var countCarrito:Int=0
var objeto1:MutableList<DataCarrito> = mutableListOf()
class TiendaAdapter(private val misProductos: List<DataTienda>): RecyclerView.Adapter<TiendaAdapter.ItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {

        val binding = TiendarecyclerBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.render(misProductos[position])

    }
    override fun getItemCount(): Int = misProductos.size

    class ItemHolder(private val binding: TiendarecyclerBinding): RecyclerView.ViewHolder(binding.root) {

        fun render(itemsTienda: DataTienda) {
            var carrito:MutableList<DataCarrito> = mutableListOf()
            binding.labelTitulo2.setText(itemsTienda.titulo)
            binding.imgProducto.setImageBitmap(itemsTienda.img)
            binding.textDescripcion.setText(itemsTienda.descripcion)
            binding.labelPrecio.setText(itemsTienda.precio)

            //Añadimos el item que pulsemos a un DataCarrito para visualizarlo en el carrito.
            binding.itemtienda.setOnClickListener(){

                //Los añadimos a nuestra lista de items y a su vez al dataclass (La imagen la convertirmos a bitmap)
                var carrito= DataCarrito(itemsTienda.img,itemsTienda.titulo,itemsTienda.precio)
                objeto1.add(carrito)
                countCarrito+= itemsTienda.precio.toInt()
                Toast.makeText(binding.itemtienda.context, "Ha seleccionado ${itemsTienda.titulo}", Toast.LENGTH_SHORT).show()

            }



        }

    }
}