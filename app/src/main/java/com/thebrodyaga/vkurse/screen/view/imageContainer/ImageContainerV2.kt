package com.thebrodyaga.vkurse.screen.view.imageContainer

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.thebrodyaga.vkurse.application.App
import com.thebrodyaga.vkurse.common.debugLogging
import com.thebrodyaga.vkurse.domain.entities.ui.ImageDto
import com.thebrodyaga.vkurse.screen.utils.loadImage
import com.thebrodyaga.vkurse.screen.utils.loadInGrid
import org.jetbrains.anko.linearLayout

/**
 * Created by admin
 *         on 11/12/2018.
 */
class ImageContainerV2 : LinearLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private val picasso = App.appComponent.getPicasso()

    init {
        orientation = LinearLayout.VERTICAL
    }

    fun setImages(imageList: List<ImageDto>) {
        removeAllViewsInLayout()
        if (imageList.isEmpty()) return
        when (imageList.size) {
            1 -> addSingleImage(imageList.first())
            2 -> addTwoImage(imageList)
            3 -> addThreeImage(imageList)
            4 -> addFourImage(imageList)
            5 -> addFiveImage(imageList)
            6 -> addSixImage(imageList)
            7 -> addSevenImage(imageList)
            8 -> addEightImage(imageList)
            9 -> addNineImage(imageList)
            else -> addTenImage(imageList)
        }
    }

    private fun addTenImage(imageList: List<ImageDto>) {
        addView(addTripleLine(imageList[0], imageList[1], imageList[2]))
        addView(addTripleLine(imageList[3], imageList[4], imageList[5]))
        addView(addQuadrupleLine(imageList[6], imageList[7], imageList[8], imageList[9]))
        layoutParams.height = THREE_LINE_HEIGHT
    }

    private fun addNineImage(imageList: List<ImageDto>) {
        addView(addTwiceLine(imageList[0], imageList[1]))
        addView(addTripleLine(imageList[2], imageList[3], imageList[4]))
        addView(addQuadrupleLine(imageList[5], imageList[6], imageList[7], imageList[8]))
        layoutParams.height = THREE_LINE_HEIGHT
    }

    private fun addEightImage(imageList: List<ImageDto>) {
        addView(addTwiceLine(imageList[0], imageList[1]))
        addView(addTripleLine(imageList[2], imageList[3], imageList[4]))
        addView(addTripleLine(imageList[5], imageList[6], imageList[7]))
        layoutParams.height = THREE_LINE_HEIGHT
    }

    private fun addSevenImage(imageList: List<ImageDto>) {
        addView(addTwiceLine(imageList[0], imageList[1]))
        addView(addTwiceLine(imageList[2], imageList[3]))
        addView(addTripleLine(imageList[4], imageList[5], imageList[6]))
        layoutParams.height = THREE_LINE_HEIGHT
    }

    private fun addSixImage(imageList: List<ImageDto>) {
        addView(addTwiceLine(imageList[0], imageList[1]))
        addView(addTwiceLine(imageList[2], imageList[3]))
        addView(addTwiceLine(imageList[4], imageList[5]))
        layoutParams.height = THREE_LINE_HEIGHT
    }

    private fun addFiveImage(imageList: List<ImageDto>) {
        addView(addTwiceLine(imageList[0], imageList[1]))
        addView(addTripleLine(imageList[2], imageList[3], imageList[4]))
        layoutParams.height = TWO_LINE_HEIGHT
    }

    private fun addFourImage(imageList: List<ImageDto>) {
        addView(addTwiceLine(imageList[0], imageList[1]))
        addView(addTwiceLine(imageList[2], imageList[3]))
        layoutParams.height = TWO_LINE_HEIGHT
    }

    private fun addThreeImage(imageList: List<ImageDto>) {
        addView(addSingleLine(imageList[0]))
        addView(addTwiceLine(imageList[1], imageList[2]))
        layoutParams.height = TWO_LINE_HEIGHT
    }

    private fun addTwoImage(imageList: List<ImageDto>) {
        addView(addTwiceLine(imageList[0], imageList[1]))
        layoutParams.height = ONE_LINE_HEIGHT
    }

    private fun addSingleImage(first: ImageDto) {
        val image = addImageView(first)
        addView(image)
        layoutParams.height = first.height
        picasso.loadInGrid(first.url, image)
    }

    private fun addQuadrupleLine(firstImageDto: ImageDto, secondImageDto: ImageDto,
                                 thirdImageDto: ImageDto, fourImageDto: ImageDto): LinearLayout {
        val firstImage = addImageView(firstImageDto)
        val secondImage = addImageView(secondImageDto)
        val thirdImage = addImageView(thirdImageDto)
        val fourImage = addImageView(fourImageDto)
        return addLineLayout(arrayOf(firstImage, secondImage, thirdImage, fourImage))
    }

    private fun addTripleLine(firstImageDto: ImageDto, secondImageDto: ImageDto,
                              thirdImageDto: ImageDto): LinearLayout {
        val firstImage = addImageView(firstImageDto)
        val secondImage = addImageView(secondImageDto)
        val thirdImage = addImageView(thirdImageDto)
        return addLineLayout(arrayOf(firstImage, secondImage, thirdImage))
    }

    private fun addTwiceLine(firstImageDto: ImageDto, secondImageDto: ImageDto): LinearLayout {
        val firstImage = addImageView(firstImageDto)
        val secondImage = addImageView(secondImageDto)
        return addLineLayout(arrayOf(firstImage, secondImage))
    }

    private fun addSingleLine(firstImageDto: ImageDto): LinearLayout {
        val imageView = addImageView(firstImageDto)
        return addLineLayout(arrayOf(imageView))
    }

    private fun addLineLayout(imageList: Array<ImageView>): LinearLayout {
        return LinearLayout(context).apply {
            layoutParams = LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT, 1f)
            orientation = HORIZONTAL
            imageList.forEach { addView(it) }
        }
    }

    private fun addImageView(image: ImageDto): ImageView {
        val imageView = ImageView(context).apply {
            id = View.generateViewId()
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1f)
        }
        imageView.loadImage(image.url)
        imageView.setOnClickListener { debugLogging(image.toString()) }
        return imageView
    }

    companion object {
        private const val ONE_LINE_HEIGHT = 300
        private const val TWO_LINE_HEIGHT = 400
        private const val THREE_LINE_HEIGHT = 500
    }
}