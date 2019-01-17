package com.thebrodyaga.vkurse.screen.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.fivehundredpx.greedolayout.GreedoLayoutSizeCalculator
import com.thebrodyaga.vkobjects.photos.Photo
import com.thebrodyaga.vkobjects.photos.PhotoAlbum
import com.thebrodyaga.vkobjects.video.Video
import com.thebrodyaga.vkurse.R
import com.thebrodyaga.vkurse.application.App
import com.thebrodyaga.vkurse.common.getMaxSize
import com.thebrodyaga.vkurse.domain.entities.ui.ImageDto
import kotlinx.android.synthetic.main.item_image_grid.view.*


/**
 * Created by admin
 * on 12/10/2018.
 */
class ImageGridAdapter(var content: MutableList<ImageDto>) :
        RecyclerView.Adapter<ImageGridAdapter.ImageHolder>(), GreedoLayoutSizeCalculator.SizeCalculatorDelegate {
    override fun aspectRatioForIndex(p0: Int): Double {
        if (p0 >= itemCount) return 1.0
        return content[p0].widthRatio.toDouble() / content[p0].heightRatio.toDouble()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        val imageView = ImageView(parent.context)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP

        imageView.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        )
        return ImageHolder(imageView)
    }

    fun setData(content: MutableList<ImageDto>){
        this.content = content
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int = content.size

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        val item = content[position]
//        holder.bind(item)
        App.appComponent.getPicasso().load(item.url).into(holder.imageView)
        /*when (item) {
            is Photo -> holder.bind(item)
            is Video -> holder.bind(item)
            is PhotoAlbum -> holder.bind(item)
        }*/
    }


    class ImageHolder(val imageView: ImageView) : RecyclerView.ViewHolder(imageView) {

        fun bind(photo: Photo) = with(imageView) {
            App.appComponent.getPicasso().load(photo.getMaxSize()).into(imageView)
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

        fun bind(item: ImageDto) = with(itemView) {
            App.appComponent.getPicasso().load(item.url).into(image)
        }
    }
}
