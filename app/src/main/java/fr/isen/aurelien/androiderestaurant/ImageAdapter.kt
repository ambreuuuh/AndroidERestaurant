package fr.isen.aurelien.androiderestaurant

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.squareup.picasso.Picasso

class ImageAdapter(private val detailActivity: DetailActivity, private val images: List<String>):PagerAdapter(){
    override fun getCount(): Int =images.size

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`

    override fun instantiateItem(container : ViewGroup, position: Int): Any{
       val imageView= ImageView(detailActivity)
       if(images[position].isNotEmpty()){
           Picasso.get().load(images[position]).into(imageView)
       }
        container.addView(imageView)
        return imageView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}