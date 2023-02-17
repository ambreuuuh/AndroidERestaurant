package fr.isen.aurelien.androiderestaurant

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import fr.isen.aurelien.androiderestaurant.databinding.ActivityContentBinding
import fr.isen.aurelien.androiderestaurant.network.Item
import fr.isen.aurelien.androiderestaurant.network.Plate
import java.io.File

class ContentActivity :AppCompatActivity() {
    lateinit var binding: ActivityContentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content)

        binding = ActivityContentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        refreshPannier()
        reloadLayout()

        binding.valider.setOnClickListener {
            val file = File(this.filesDir, "cart.json")
            if (file.exists()) {
                val json = file.readText()
                val cart= Gson().fromJson(json, Array<Item>::class.java)
                if (cart.isNotEmpty()) {
                    Snackbar.make(binding.root, "Commande passée", Snackbar.LENGTH_SHORT).show()
                    file.delete()
                    refreshPannier()
                    reloadLayout()
                }
            }
        }
    }

    private fun reloadLayout(){
        binding.list.layoutManager = LinearLayoutManager(null)
        val file = File(this.filesDir, "cart.json")
        if(file.exists()){
            val json = file.readText()
            val cart= Gson().fromJson(json, Array<Item>::class.java)
            binding.list.adapter = ContentAdapter(cart) { target ->
                File(this.filesDir,"cart.json").writeText(Gson().toJson(cart.filter { it !== target }.toTypedArray()))
                refreshPannier()
                reloadLayout()
            }
        }else{
            binding.list.adapter = null
        }
    }
    private fun refreshPannier() {
        val file= File(this.filesDir, "cart.json")
        if (file.exists()){
            val json = file.readText()
            val cart = Gson().fromJson(json, Array<Item>:: class.java)
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