package fr.isen.aurelien.androiderestaurant
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import fr.isen.aurelien.androiderestaurant.databinding.ActivityDetailBinding
import fr.isen.aurelien.androiderestaurant.network.Plate
import java.lang.Exception


class CustomAdapter(val items: List<Plate>, val clickListener: (Plate) -> Unit): RecyclerView.Adapter<CustomAdapter.CellViewHolder>(){
    class CellViewHolder(binding: ActivityDetailBinding) : RecyclerView.ViewHolder(binding.root) {
        val id: TextView = binding.name
        val ing: TextView = binding.ingredients
        val price: TextView = binding.textViewprice
        val image = binding.viewPager
        val root: ConstraintLayout = binding.root

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CellViewHolder {
        val binding = ActivityDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CellViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: CellViewHolder, position: Int) {
        val plate = items[position]
        holder.id.text = plate.name
        holder.price.text = plate.prices[0].price + '€'
        //Picasso.get().load(getThumbnail(plate)).placeholder(R.drawable.noimage).into(holder.image)
        holder.root.setOnClickListener {
            Log.d("click", "Click on $plate")
            clickListener(plate)
        }
    }

    private fun getThumbnail(plate: Plate): String? {
        return if (plate.image.isNotEmpty() && plate.image.firstOrNull()?.isNotEmpty() == true) {
            plate.image.firstOrNull()
        } else {
            null
        }
    }

    fun onSuccess() {
        Log.d("picasso", "sucess")
    }

    fun onError(e: Exception?) {
        Log.e("picasso", e.toString())
    }
}

