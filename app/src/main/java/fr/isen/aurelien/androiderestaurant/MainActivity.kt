package fr.isen.aurelien.androiderestaurant

import android.content.Intent
import android.os.Bundle
import android.util.Log

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.navigateUp
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import fr.isen.aurelien.androiderestaurant.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        buttonsListener()

    }
    private fun buttonsListener() {
        binding.starterbutton.setOnClickListener {
            Log.d("button", "Click sur le button entrees")
            Toast.makeText(this,"Entrees",Toast.LENGTH_SHORT).show()
            val intent= Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }
        binding.mainbutton.setOnClickListener {
            Log.d("button", "Click sur le button plats")
            Toast.makeText(this,"Plats",Toast.LENGTH_SHORT).show()
            val intent= Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }
        binding.endbutton.setOnClickListener {
            Log.d("button", "Click sur le button desserts")
            Toast.makeText(this,"Desserts",Toast.LENGTH_SHORT).show()
            val intent= Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }
    }
}