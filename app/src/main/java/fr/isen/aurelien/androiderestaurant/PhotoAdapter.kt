package fr.isen.aurelien.androiderestaurant

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.squareup.picasso.Picasso

class PhotoAdapter (private val detailActivity: DetailActivity, private val images: Array<String>): PagerAdapter(){
    override fun getCount(): Int=images.size

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`

    override fun instantiateItem(container: ViewGroup, position: Int):Any{
        val imageView= ImageView(detailActivity)
        if(images[position].isNotEmpty()){
            Picasso.get().load(images[position]).into(imageView)
        }
        container.addView(imageView)
        return imageView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any){
        container.removeView(`object` as View)
    }

}