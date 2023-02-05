package fr.isen.aurelien.androiderestaurant
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.aurelien.androiderestaurant.databinding.CellCustomBinding
import fr.isen.aurelien.androiderestaurant.network.Plate


class CustomAdapter(val items: List<Plate>, val clickListener: (Int) -> Unit): RecyclerView.Adapter<CustomAdapter.CellViewHolder>(){
    class CellViewHolder(binding: CellCustomBinding) : RecyclerView.ViewHolder(binding.root){
        val textView = binding.textview
        val price = binding.textView6
        val image= binding.imageView7
        val root: ConstraintLayout = binding.root


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CellViewHolder {
        val binding = CellCustomBinding.inflate(LayoutInflater.from(parent.context))
        return CellViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CellViewHolder, position: Int) {
       holder.textView.text = items[position].name
        holder.price.text = items[position].price.toString()
        if(items[position].image[0] != ""){
            Picasso.get().load(items[position].image[0]).into(holder.image)
        }
        holder.root.setOnClickListener (View.OnClickListener {
            Log.d("button", "Click sur cell")
            clickListener(position)
        })
    }

    override fun getItemCount(): Int{
       return  items.count()
    }


}