package fr.isen.aurelien.androiderestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
    }

    override fun onStart() {
        super.onStart()
        Log.d("LifeCycle", "MenuActivity onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("LifeCycle", "MenuActivity onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("LifeCycle", "MenuActivity onPause")
    }

    override fun onDestroy() {
        Log.d("LifeCycle", "MenuActivity onDestroy")
        super.onDestroy()
    }

}