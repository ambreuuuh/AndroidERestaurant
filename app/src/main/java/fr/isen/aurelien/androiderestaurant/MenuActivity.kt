package fr.isen.aurelien.androiderestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder

import fr.isen.aurelien.androiderestaurant.databinding.ActivityMenuBinding
import fr.isen.aurelien.androiderestaurant.network.MenuResult
import fr.isen.aurelien.androiderestaurant.network.NetworkConstants
import org.json.JSONObject

enum class Category {STARTER, MAIN, END}
class MenuActivity : AppCompatActivity() {

    companion object{
        val extraKey = "extraKey"
    }

    private lateinit var binding: ActivityMenuBinding
    private lateinit var currentcategory : Category

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView= binding.recyclerView
        val category = intent.getSerializableExtra(extraKey) as? Category   //as optionnel, essaye de le caster en Category
        currentcategory=category ?: Category.STARTER
        supportActionBar?.title = categoryName()
        // if category == null {category = STARTER }

        recyclerView.adapter = CustomAdapter(arrayListOf()){
            val intent = Intent(this, BasketActivity::class.java)
            intent.putExtra("item", it)
            startActivity(intent)
        }

        makeRequest()
    }

    private fun makeRequest(){
        val queue = Volley.newRequestQueue(this)
        val params = JSONObject()
        params.put(NetworkConstants.idShopKey, 1)
        val request = JsonObjectRequest(
            Request.Method.POST,
            NetworkConstants.url,
            params,
            {result ->
                //Sucess of request
                Log.d("request", result.toString(2))
                parseData(result.toString())
            },
            {error ->
                //Error when request
                Log.e("request",error.toString())

            }
        )
        queue.add(request)
        //show datas
    }

    private fun parseData(data: String){
        val result = GsonBuilder().create().fromJson(data, MenuResult::class.java)
        val category = result.data.first {
            it.name == categoryFilterKey()
        }
        showDatas(category)
    }
    private fun showDatas(category: fr.isen.aurelien.androiderestaurant.network.Category){
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = CustomAdapter(category.items) {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.PLATE_EXTRA, it)
            startActivity(intent)

        }
    }

    private fun categoryName(): String{
        return when(currentcategory){
            Category.STARTER -> getString(R.string.starter)
            Category.MAIN -> getString(R.string.main)
            Category.END -> getString(R.string.end)
        }
    }
    private fun categoryFilterKey(): String{
        return when(currentcategory){
            Category.STARTER -> getString(R.string.starter)
            Category.MAIN -> getString(R.string.main)
            Category.END -> getString(R.string.end)
        }
    }


/*
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

*/

}