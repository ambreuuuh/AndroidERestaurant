package fr.isen.aurelien.androiderestaurant


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.PagerAdapter
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import fr.isen.aurelien.androiderestaurant.databinding.ActivityDetailBinding
import fr.isen.aurelien.androiderestaurant.network.Plate
import fr.isen.aurelien.androiderestaurant.network.Price
import org.json.JSONArray
import org.json.JSONObject
import java.io.File

class DetailActivity: AppCompatActivity() {
    companion object {
        val PLATE_EXTRA = "PLATE_EXTRA"
    }

    lateinit var binding: ActivityDetailBinding
    var plate: Plate? = null
    private var count =0
    //private val price = plate?.prices?.map { it.price[0] }?.toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        plate = intent.getSerializableExtra(PLATE_EXTRA) as? Plate
        binding.titlemeal.text = plate?.name
        val price = plate?.prices?.map { it.price }?.joinToString(",") ?: ""
        val ingredients = plate?.ingredients?.map { it.name }?.joinToString(",") ?: ""
        binding.ingredients.text = ingredients


        plate?.let {
           // binding.viewPager2.adapter = PhotoAdapter(it.images, this)
            //val viewPager = binding.viewPager2
            //val adapter = PhotoAdapter(it.images)
            //viewPager.adapter= adapter
        }

        binding.minus.setOnClickListener{
            if (count>0){
                count--
                binding.quantity.text= count.toString()
                val newPrice = convertString(price)
                val total = calculateTotalPrice(newPrice,count)
                binding.addCart.text= ("Add Cart: $total €").toString()
            }
        }

        binding.plus.setOnClickListener{
            count++
            binding.quantity.text= count.toString()
            val newPrice = convertString(price)
            val total = calculateTotalPrice(newPrice,count)
            binding.addCart.text= ("Add Cart: $total €").toString()

        }

        binding.addCart.setOnClickListener{
            val newPrice = convertString(price)
            val total = calculateTotalPrice(newPrice,count)
            if (count !=0){
                val basketfile = mapOf("Nouvel ajout" to count.toString() + "" + plate?.name.toString() + " pour " + total.toString()+ "€")
                val basketJson = Gson().toJson(basketfile)
                val fileOutputStream = openFileOutput("basket.json", Context.MODE_APPEND)
                fileOutputStream.write(basketJson.toByteArray())
                fileOutputStream.close()

                val view = findViewById<View>(android.R.id.content)
                Snackbar.make(
                    view,
                    "You add " + (total.toString()) + "€ to the basket",
                    Snackbar.LENGTH_SHORT
                )
                    .show()
            }

            val sharedPreferences= getSharedPreferences("MyPreferencesApplication", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            val basketListString = sharedPreferences.getString("basketList", "[]")
            val basketList= JSONArray(basketListString)
            val newBasket= JSONObject()
            newBasket.put("countValue", count)
            newBasket.put("nameValue", plate?.name)
            newBasket.put("totalValue", total.toString())
            basketList.put(newBasket)

            editor.putString("basketList", basketList.toString())
            editor.apply()
        }


        binding.imgbasket.setOnClickListener{
            val newPrice= convertString(price)
            val total= calculateTotalPrice(newPrice,count)
            val basketfile = mapOf("Nouvel ajout" to count.toString() + "" + plate?.name.toString() + " pour " + total.toString()+ "€")
            val basketJson = Gson().toJson(basketfile)
            val intent= Intent(this, BasketActivity::class.java)

            intent.putExtra("basketJson", basketJson)
            startActivity(intent)
        }
    }

    private fun convertString(price: String?): Int {
        if (price != null) {
            return price.toInt()
        }
        else{
            return 999
        }
    }


    private fun calculateTotalPrice(price: Int, count: Int): Int {
        val totalPrice = price * count
        return totalPrice
    }

}