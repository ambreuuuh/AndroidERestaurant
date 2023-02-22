package fr.isen.aurelien.androiderestaurant

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.PagerAdapter
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import fr.isen.aurelien.androiderestaurant.databinding.ActivityDetailBinding
import fr.isen.aurelien.androiderestaurant.network.Plate
import java.io.File

class DetailActivity: AppCompatActivity() {
    companion object {
        val PLATE_EXTRA = "PLATE_EXTRA"
    }

    lateinit var binding: ActivityDetailBinding
    var plate: Plate? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        plate = intent.getSerializableExtra(PLATE_EXTRA) as? Plate

        val ingredients = plate?.ingredients?.map { it.name }?.joinToString(",") ?: ""
        binding.ingredients.text = ingredients

        plate?.let {
            binding.viewPager2.adapter = PhotoAdapter(it.images, this)
        }
    }
}