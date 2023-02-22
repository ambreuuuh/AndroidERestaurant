package fr.isen.aurelien.androiderestaurant

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.squareup.picasso.Picasso

class PhotoAdapter(val images: List<String>, activity: AppCompatActivity):FragmentStateAdapter(activity){
    override fun getItemCount(): Int{
        return images.count()
    }

    override fun createFragment(position: Int): Fragment {
        return PhotoFragment.newInstance(images[position])
    }
}