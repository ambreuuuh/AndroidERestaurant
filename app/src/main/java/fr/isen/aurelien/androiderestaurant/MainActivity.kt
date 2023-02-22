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

        Log.d("lifeCycle", "MainActivity onCreate")
    }

    override fun onStart(){
        super.onStart()
        Log.d("lifeCycle", "MainActivity onStart")
    }
    override fun onResume(){
        super.onResume()
        Log.d("lifeCycle", "MainActivity onResume")
    }
    override fun onPause(){
        super.onPause()
        Log.d("lifeCycle", "MainActivity onPause")
    }
    override fun onDestroy(){
        super.onDestroy()
        Log.d("lifeCycle", "MainActivity onDestroy")
    }
    private fun buttonsListener() {
        binding.starterbutton.setOnClickListener {
            showCategory(Category.STARTER)
        }
        binding.mainbutton.setOnClickListener {
            showCategory(Category.MAIN)
        }
        binding.endbutton.setOnClickListener {
            showCategory(Category.END)
        }
    }

    private fun showCategory(category: Category) {
        val intent = Intent(this, MenuActivity::class.java)
        intent.putExtra(MenuActivity.extraKey, category)
        startActivity(intent)

    }
}




