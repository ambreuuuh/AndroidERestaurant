package fr.isen.aurelien.androiderestaurant

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import fr.isen.aurelien.androiderestaurant.databinding.ActivityDetailBinding
import fr.isen.aurelien.androiderestaurant.network.Plate
import java.io.File

class DetailActivity: AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var plat: Plate
    private var quantity: Int= 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        plat = intent.getSerializableExtra("item") as Plate
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.title.text = "Detail"
        binding.toolbar.pannier.setOnClickListener {
            startActivity(Intent(this, ContentActivity::class.java))
        }
        refreshPannier()
        binding.viewPager.adapter = ImageAdapter(this, plat.image)
        binding.name.text = plat.name
        binding.ingredients.text = plat.ingredients.joinToString(",") { it.ingredients} //revoir avec name_fr

        plat.prices.forEach { price ->
            val button = Button(this)
            button.text = "${price} : ${(price.price.toDouble() * quantity).toString().replace(".", "€")}0"
            button.setOnClickListener {
                addInJson(price.price)
                refreshPannier()
                Snackbar.make(binding.root, "Ajouter au panier", Snackbar.LENGTH_SHORT).show()
            }

            binding.addToCart.addView(button)
        }
        val bouton = binding.addCart
        binding.minus.setOnClickListener() {
            if (binding.quantity.text.toString().toInt() > 1) {
                quantity--
                changePrice(bouton)
                binding.quantity.text = quantity.toString()
            }
        }
        binding.plus.setOnClickListener {
            quantity++
            changePrice(bouton)
            binding.quantity.text = quantity.toString()
        }
    }

    private fun addInJson(price: String){
        val file = File(this.filesDir, "cart.json")
        if(!file.exists()){
            file.createNewFile()
            file.writeText("[{ \"id\": ${plat.name}, \"price\": \"${plat.prices}\",\"quantity\": $quantity, \"image\": \"${plat.image[0]}]")
        }else{
            val json = file.readText()
            if(json == "[]"){
                file.writeText(json.substring(0, json.length -1) + ",{\"id\": ${plat.name}, \"price\": \"${plat.prices}\",\"quantity\": $quantity, \"image\": \"${plat.image[0]}]")
            }else{
                file.writeText(json.substring(0, json.length -1) + ",{ \"id\": ${plat.name}, \"price\": \"${plat.prices}\",\"quantity\": $quantity, \"image\": \"${plat.image[0]}]")
            }
        }
    }

    private fun changePrice(bouton : Button){
        plat.prices.forEach {
            bouton.text = "${it} : ${(it.price.toDouble() * quantity).toString().replace(".","€")}0"
        }
    }

    private fun refreshPannier(){
        val file= File(this.filesDir, "cart.json")
        if (file.exists()){
            val json = file.readText()
            val cart = Gson().fromJson(json, Array<Plate>:: class.java)
            if (cart.isNotEmpty()){
                binding.toolbar.pastille.visibility = View.VISIBLE
            }else{
                binding.toolbar.pastille.visibility = View.GONE
            }
            binding.toolbar.pastille.text = cart.size.toString()
        }else{
            binding.toolbar.pastille.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        refreshPannier()
    }

}
