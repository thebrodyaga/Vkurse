package com.thebrodyaga.vkurse.screen.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.thebrodyaga.vkobjects.photos.Photo
import com.thebrodyaga.vkobjects.photos.PhotoAlbum
import com.thebrodyaga.vkobjects.video.Video
import com.thebrodyaga.vkurse.R
import com.thebrodyaga.vkurse.application.App
import kotlinx.android.synthetic.main.item_image_grid.view.*


/**
 * Created by admin
 * on 12/10/2018.
 */
class ImageGridAdapter(val content: MutableList<Any>) : RecyclerView.Adapter<ImageGridAdapter.ImageHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder =
            ImageHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_image_grid, parent, false))

    override fun getItemCount(): Int = content.size

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        val item = content[position]
        when (item) {
            is Photo -> holder.bind(item)
            is Video -> holder.bind(item)
            is PhotoAlbum -> holder.bind(item)
        }
    }


    class ImageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(photo: Photo) = with(itemView) {
            App.appComponent.getPicasso().load(photo.getMaxSize()).into(image)
            setupLayoutParams()
        }

        private fun setupLayoutParams() {
            val lp: ViewGroup.LayoutParams? = itemView.layoutParams
            /*if (lp is FlexboxLayoutManager.LayoutParams) {
                lp.flexGrow = 1.0f
                lp.alignSelf = AlignSelf.FLEX_END
            }*/
        }

        fun bind(video: Video) = with(itemView) {
            App.appComponent.getPicasso().load(video.photo800).into(image)
            setupLayoutParams()
        }

        fun bind(photoAlbum: PhotoAlbum) = with(itemView) {
            App.appComponent.getPicasso().load(photoAlbum.thumb.photo1280).into(image)
            setupLayoutParams()
        }
    }
}

private fun Photo.getMaxSize(): String {
    return photo2560 ?: photo1280 ?: photo807 ?: photo604 ?: photo130 ?: photo75
}
