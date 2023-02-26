package fr.isen.aurelien.androiderestaurant

import android.R
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import fr.isen.aurelien.androiderestaurant.databinding.ActivityBasketBinding
import fr.isen.aurelien.androiderestaurant.databinding.ActivityMainBinding
import org.json.JSONArray
import java.io.File


class BasketActivity : AppCompatActivity(){

    lateinit var binding: ActivityBasketBinding
    private val TOTAL = "Total order"
    var total = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBasketBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences =
            getSharedPreferences("MyPreferencesApplication", Context.MODE_PRIVATE)
        val basketListString = sharedPreferences.getString("basketList", "[]")
        val basketList = JSONArray(basketListString)
        val basketStringBuilder = java.lang.StringBuilder()
        var articleCount = 0

        for (i in 0 until basketList.length()) {
            if (articleCount >= 10) {
                break
            }

            val item = basketList.getJSONObject(i)
            val countValue = item.getInt("countValue")
            val nameValue = item.getString("nameValue")
            val totalValue = item.getString("totalValue")

            if(countValue != 0){
                basketStringBuilder.append("$countValue $nameValue for $totalValue €\n")
                binding.textViewBasket.text = basketStringBuilder.toString()
                total += totalValue.toDouble()
                articleCount++
            }

            binding.buttonCommand.isEnabled =binding.textViewBasket.text != "Basket is empty"

            binding.buttonCommand.setOnClickListener{
                clearBasket()

                //val intent = Intent(this, MenuActivity::class.java)
                //startActivity(intent)
            }
            binding.textViewSup.setOnClickListener{
                clearBasket()
                total = 0.0
                val formatTotal = "%.1f €".format(total)
                binding.textViewTotal.text= TOTAL + formatTotal
                binding.buttonCommand.isEnabled = false
            }
        }

        val formatTotal = "%.1f €".format(total)
        binding.textViewTotal.text= TOTAL + formatTotal
    }

    fun clearBasket(){
        val sharedPreferences = getSharedPreferences("MyPreferencesApplication", Context.MODE_PRIVATE)
        val editor= sharedPreferences.edit()
        editor.clear()
        editor.apply()
        val file = File(applicationContext.filesDir, "basket.json")
        file.delete()
        binding.textViewBasket.text = "Your basket is empty"
    }
}

