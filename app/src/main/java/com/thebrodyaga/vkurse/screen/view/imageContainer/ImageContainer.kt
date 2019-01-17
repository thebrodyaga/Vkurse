package com.thebrodyaga.vkurse.screen.view.imageContainer

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.thebrodyaga.vkurse.application.App
import com.thebrodyaga.vkurse.common.DEBUG_TAG
import com.thebrodyaga.vkurse.common.debugLogging
import com.thebrodyaga.vkurse.domain.entities.ui.ImageDto
import com.thebrodyaga.vkurse.screen.utils.loadInGrid
import org.jetbrains.anko.frameLayout
import org.jetbrains.anko.imageView


/**
 * Created by admin
 *         on 24/10/2018.
 */
class ImageContainer : ConstraintLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private val picasso = App.appComponent.getPicasso()

    fun setImages(imageList: List<ImageDto>) {
        removeAllViewsInLayout()
        if (imageList.isEmpty()) return
        val constraintSet = ConstraintSet()
        constraintSet.clone(this)
        when (imageList.size) {
            1 -> addSingleImage(imageList.first(), constraintSet)
            2 -> addTwoImage(imageList, constraintSet)
            3 -> addThreeImage(imageList, constraintSet)
            4 -> addFourImage(imageList, constraintSet)
            5 -> addFiveImage(imageList, constraintSet)
            6 -> addSixImage(imageList, constraintSet)
            7 -> addSevenImage(imageList, constraintSet)
            8 -> addEightImage(imageList, constraintSet)
            9 -> addNineImage(imageList, constraintSet)
            else -> addTenImage(imageList, constraintSet)
        }
        constraintSet.applyTo(this)
    }

    private fun addTenImage(imageList: List<ImageDto>, constraintSet: ConstraintSet) {
        val firstLineMass = addTripleLine(imageList[0], imageList[1], imageList[2], constraintSet)
        val secondLineMass = addTripleLine(imageList[3], imageList[4], imageList[5], constraintSet)
        val thirdLineMass = addQuadrupleLine(imageList[6], imageList[7], imageList[8], imageList[9], constraintSet)
        val firstImage = firstLineMass[0]
        val secondImage = firstLineMass[1]
        val thirdImage = firstLineMass[2]

        val fourImage = secondLineMass[0]
        val fiveImage = secondLineMass[1]
        val sixImage = secondLineMass[2]

        val sevenImage = thirdLineMass[0]
        val eightImage = thirdLineMass[1]
        val nineImage = thirdLineMass[2]
        val tenImage = thirdLineMass[3]

        val barrierFirst = addBarrier(constraintSet)
        val barrierSecond = addBarrier(constraintSet)
        constraintSet.apply {
            connect(firstImage.id, ConstraintSet.TOP, id, ConstraintSet.TOP)
            connect(firstImage.id, ConstraintSet.BOTTOM, barrierFirst.id, ConstraintSet.TOP)
            connect(secondImage.id, ConstraintSet.TOP, id, ConstraintSet.TOP)
            connect(thirdImage.id, ConstraintSet.TOP, id, ConstraintSet.TOP)
            connect(barrierFirst.id, ConstraintSet.TOP, firstImage.id, ConstraintSet.BOTTOM, GRID_SEPARATOR_SIZE)

            connect(fourImage.id, ConstraintSet.TOP, barrierFirst.id, ConstraintSet.BOTTOM)
            connect(fourImage.id, ConstraintSet.BOTTOM, fiveImage.id, ConstraintSet.TOP)
            connect(fiveImage.id, ConstraintSet.TOP, barrierFirst.id, ConstraintSet.BOTTOM)
            connect(sixImage.id, ConstraintSet.TOP, barrierSecond.id, ConstraintSet.BOTTOM)
            connect(barrierSecond.id, ConstraintSet.TOP, thirdImage.id, ConstraintSet.BOTTOM, GRID_SEPARATOR_SIZE)

            connect(sevenImage.id, ConstraintSet.BOTTOM, id, ConstraintSet.BOTTOM)
            connect(sevenImage.id, ConstraintSet.TOP, barrierSecond.id, ConstraintSet.BOTTOM)
            connect(eightImage.id, ConstraintSet.TOP, barrierSecond.id, ConstraintSet.BOTTOM)
            connect(nineImage.id, ConstraintSet.TOP, barrierSecond.id, ConstraintSet.BOTTOM)
            connect(tenImage.id, ConstraintSet.TOP, barrierSecond.id, ConstraintSet.BOTTOM)
        }
        picasso.loadInGrid(imageList[0].url, firstImage)
        picasso.loadInGrid(imageList[1].url, secondImage)
        picasso.loadInGrid(imageList[2].url, thirdImage)
        picasso.loadInGrid(imageList[3].url, fourImage)
        picasso.loadInGrid(imageList[4].url, fiveImage)
        picasso.loadInGrid(imageList[5].url, sixImage)
        picasso.loadInGrid(imageList[6].url, sevenImage)
        picasso.loadInGrid(imageList[7].url, eightImage)
        picasso.loadInGrid(imageList[8].url, nineImage)
        picasso.loadInGrid(imageList[9].url, tenImage)
    }

    private fun addNineImage(imageList: List<ImageDto>, constraintSet: ConstraintSet) {
        val firstLineMass = addTwiceLine(imageList[0], imageList[1], constraintSet)
        val secondLineMass = addTripleLine(imageList[2], imageList[3], imageList[4], constraintSet)
        val thirdLineMass = addQuadrupleLine(imageList[5], imageList[6], imageList[7], imageList[8], constraintSet)
        val firstImage = firstLineMass[0]
        val secondImage = firstLineMass[1]
        val thirdImage = secondLineMass[0]
        val fourImage = secondLineMass[1]
        val fiveImage = secondLineMass[2]
        val sixImage = thirdLineMass[0]
        val sevenImage = thirdLineMass[1]
        val eightImage = thirdLineMass[2]
        val nineImage = thirdLineMass[3]
        val barrierFirst = addBarrier(constraintSet)
        val barrierSecond = addBarrier(constraintSet)
        constraintSet.apply {
            connect(firstImage.id, ConstraintSet.TOP, id, ConstraintSet.TOP)
            connect(firstImage.id, ConstraintSet.BOTTOM, barrierFirst.id, ConstraintSet.TOP)
            connect(secondImage.id, ConstraintSet.TOP, id, ConstraintSet.TOP)
            connect(barrierFirst.id, ConstraintSet.TOP, firstImage.id, ConstraintSet.BOTTOM, GRID_SEPARATOR_SIZE)

            connect(thirdImage.id, ConstraintSet.TOP, barrierFirst.id, ConstraintSet.BOTTOM)
            connect(thirdImage.id, ConstraintSet.BOTTOM, fiveImage.id, ConstraintSet.TOP)
            connect(fourImage.id, ConstraintSet.TOP, barrierFirst.id, ConstraintSet.BOTTOM)
            connect(fiveImage.id, ConstraintSet.TOP, barrierSecond.id, ConstraintSet.BOTTOM)
            connect(barrierSecond.id, ConstraintSet.TOP, thirdImage.id, ConstraintSet.BOTTOM, GRID_SEPARATOR_SIZE)

            connect(sixImage.id, ConstraintSet.BOTTOM, id, ConstraintSet.BOTTOM)
            connect(sixImage.id, ConstraintSet.TOP, barrierSecond.id, ConstraintSet.BOTTOM)
            connect(sevenImage.id, ConstraintSet.TOP, barrierSecond.id, ConstraintSet.BOTTOM)
            connect(eightImage.id, ConstraintSet.TOP, barrierSecond.id, ConstraintSet.BOTTOM)
            connect(nineImage.id, ConstraintSet.TOP, barrierSecond.id, ConstraintSet.BOTTOM)
        }
        picasso.loadInGrid(imageList[0].url, firstImage)
        picasso.loadInGrid(imageList[1].url, secondImage)
        picasso.loadInGrid(imageList[2].url, thirdImage)
        picasso.loadInGrid(imageList[3].url, fourImage)
        picasso.loadInGrid(imageList[4].url, fiveImage)
        picasso.loadInGrid(imageList[5].url, sixImage)
        picasso.loadInGrid(imageList[6].url, sevenImage)
        picasso.loadInGrid(imageList[7].url, eightImage)
        picasso.loadInGrid(imageList[8].url, nineImage)
    }

    private fun addEightImage(imageList: List<ImageDto>, constraintSet: ConstraintSet) {
        val firstLineMass = addTwiceLine(imageList[0], imageList[1], constraintSet)
        val secondLineMass = addTripleLine(imageList[2], imageList[3], imageList[4], constraintSet)
        val thirdLineMass = addTripleLine(imageList[5], imageList[6], imageList[7], constraintSet)
        val firstImage = firstLineMass[0]
        val secondImage = firstLineMass[1]
        val thirdImage = secondLineMass[0]
        val fourImage = secondLineMass[1]
        val fiveImage = secondLineMass[2]
        val sixImage = thirdLineMass[0]
        val sevenImage = thirdLineMass[1]
        val eightImage = thirdLineMass[2]
        val barrierFirst = addBarrier(constraintSet)
        val barrierSecond = addBarrier(constraintSet)
        constraintSet.apply {
            connect(firstImage.id, ConstraintSet.TOP, id, ConstraintSet.TOP)
            connect(firstImage.id, ConstraintSet.BOTTOM, barrierFirst.id, ConstraintSet.TOP)
            connect(secondImage.id, ConstraintSet.TOP, id, ConstraintSet.TOP)
            connect(barrierFirst.id, ConstraintSet.TOP, firstImage.id, ConstraintSet.BOTTOM, GRID_SEPARATOR_SIZE)

            connect(thirdImage.id, ConstraintSet.TOP, barrierFirst.id, ConstraintSet.BOTTOM)
            connect(thirdImage.id, ConstraintSet.BOTTOM, fiveImage.id, ConstraintSet.TOP)
            connect(fourImage.id, ConstraintSet.TOP, barrierFirst.id, ConstraintSet.BOTTOM)
            connect(fiveImage.id, ConstraintSet.TOP, barrierSecond.id, ConstraintSet.BOTTOM)
            connect(barrierSecond.id, ConstraintSet.TOP, thirdImage.id, ConstraintSet.BOTTOM, GRID_SEPARATOR_SIZE)

            connect(sixImage.id, ConstraintSet.BOTTOM, id, ConstraintSet.BOTTOM)
            connect(sixImage.id, ConstraintSet.TOP, barrierSecond.id, ConstraintSet.BOTTOM)
            connect(sevenImage.id, ConstraintSet.TOP, barrierSecond.id, ConstraintSet.BOTTOM)
            connect(eightImage.id, ConstraintSet.TOP, barrierSecond.id, ConstraintSet.BOTTOM)
        }
        picasso.loadInGrid(imageList[0].url, firstImage)
        picasso.loadInGrid(imageList[1].url, secondImage)
        picasso.loadInGrid(imageList[2].url, thirdImage)
        picasso.loadInGrid(imageList[3].url, fourImage)
        picasso.loadInGrid(imageList[4].url, fiveImage)
        picasso.loadInGrid(imageList[5].url, sixImage)
        picasso.loadInGrid(imageList[6].url, sevenImage)
        picasso.loadInGrid(imageList[7].url, eightImage)
    }

    private fun addSevenImage(imageList: List<ImageDto>, constraintSet: ConstraintSet) {
        val firstLineMass = addTwiceLine(imageList[0], imageList[1], constraintSet)
        val secondLineMass = addTwiceLine(imageList[2], imageList[3], constraintSet)
        val thirdLineMass = addTripleLine(imageList[4], imageList[5], imageList[6], constraintSet)
        val firstImage = firstLineMass[0]
        val secondImage = firstLineMass[1]
        val thirdImage = secondLineMass[0]
        val fourImage = secondLineMass[1]
        val fiveImage = thirdLineMass[0]
        val sixImage = thirdLineMass[1]
        val sevenImage = thirdLineMass[2]
        val barrierFirst = addBarrier(constraintSet)
        val barrierSecond = addBarrier(constraintSet)
        constraintSet.apply {
            connect(firstImage.id, ConstraintSet.TOP, id, ConstraintSet.TOP)
            connect(firstImage.id, ConstraintSet.BOTTOM, barrierFirst.id, ConstraintSet.TOP)
            connect(secondImage.id, ConstraintSet.TOP, id, ConstraintSet.TOP)
            connect(barrierFirst.id, ConstraintSet.TOP, firstImage.id, ConstraintSet.BOTTOM, GRID_SEPARATOR_SIZE)
            connect(thirdImage.id, ConstraintSet.TOP, barrierFirst.id, ConstraintSet.BOTTOM)
            connect(thirdImage.id, ConstraintSet.BOTTOM, fiveImage.id, ConstraintSet.TOP)
            connect(fourImage.id, ConstraintSet.TOP, barrierFirst.id, ConstraintSet.BOTTOM)
            connect(barrierSecond.id, ConstraintSet.TOP, thirdImage.id, ConstraintSet.BOTTOM, GRID_SEPARATOR_SIZE)
            connect(fiveImage.id, ConstraintSet.TOP, barrierSecond.id, ConstraintSet.BOTTOM)
            connect(fiveImage.id, ConstraintSet.BOTTOM, id, ConstraintSet.BOTTOM)
            connect(sixImage.id, ConstraintSet.TOP, barrierSecond.id, ConstraintSet.BOTTOM)
            connect(sevenImage.id, ConstraintSet.TOP, barrierSecond.id, ConstraintSet.BOTTOM)
        }
        picasso.loadInGrid(imageList[0].url, firstImage)
        picasso.loadInGrid(imageList[1].url, secondImage)
        picasso.loadInGrid(imageList[2].url, thirdImage)
        picasso.loadInGrid(imageList[3].url, fourImage)
        picasso.loadInGrid(imageList[4].url, fiveImage)
        picasso.loadInGrid(imageList[5].url, sixImage)
        picasso.loadInGrid(imageList[6].url, sevenImage)
    }


    private fun addSixImage(imageList: List<ImageDto>, constraintSet: ConstraintSet) {
        val firstLineMass = addTwiceLine(imageList[0], imageList[1], constraintSet)
        val secondLineMass = addTwiceLine(imageList[2], imageList[3], constraintSet)
        val thirdLineMass = addTwiceLine(imageList[4], imageList[5], constraintSet)
        val firstImage = firstLineMass[0]
        val secondImage = firstLineMass[1]
        val thirdImage = secondLineMass[0]
        val fourImage = secondLineMass[1]
        val fiveImage = thirdLineMass[0]
        val sixImage = thirdLineMass[1]
        val barrierFirst = addBarrier(constraintSet)
        val barrierSecond = addBarrier(constraintSet)
        constraintSet.apply {
            connect(firstImage.id, ConstraintSet.TOP, id, ConstraintSet.TOP)
            connect(firstImage.id, ConstraintSet.BOTTOM, barrierFirst.id, ConstraintSet.TOP)
            connect(secondImage.id, ConstraintSet.TOP, id, ConstraintSet.TOP)
            connect(barrierFirst.id, ConstraintSet.TOP, firstImage.id, ConstraintSet.BOTTOM, GRID_SEPARATOR_SIZE)
            connect(thirdImage.id, ConstraintSet.TOP, barrierFirst.id, ConstraintSet.BOTTOM)
            connect(thirdImage.id, ConstraintSet.BOTTOM, fiveImage.id, ConstraintSet.TOP)
            connect(fourImage.id, ConstraintSet.TOP, barrierFirst.id, ConstraintSet.BOTTOM)
            connect(barrierSecond.id, ConstraintSet.TOP, thirdImage.id, ConstraintSet.BOTTOM, GRID_SEPARATOR_SIZE)
            connect(fiveImage.id, ConstraintSet.TOP, barrierSecond.id, ConstraintSet.BOTTOM)
            connect(fiveImage.id, ConstraintSet.BOTTOM, id, ConstraintSet.BOTTOM)
            connect(sixImage.id, ConstraintSet.TOP, barrierSecond.id, ConstraintSet.BOTTOM)
        }
        picasso.loadInGrid(imageList[0].url, firstImage)
        picasso.loadInGrid(imageList[1].url, secondImage)
        picasso.loadInGrid(imageList[2].url, thirdImage)
        picasso.loadInGrid(imageList[3].url, fourImage)
        picasso.loadInGrid(imageList[4].url, fiveImage)
        picasso.loadInGrid(imageList[5].url, sixImage)
    }

    private fun addFiveImage(imageList: List<ImageDto>, constraintSet: ConstraintSet) {
        val firstLineMass = addTwiceLine(imageList[0], imageList[1], constraintSet)
        val secondLineMass = addTripleLine(imageList[2], imageList[3], imageList[4], constraintSet)
        val firstImage = firstLineMass[0]
        val secondImage = firstLineMass[1]
        val thirdImage = secondLineMass[0]
        val fourImage = secondLineMass[1]
        val fiveImage = secondLineMass[2]
        val barrier = addBarrier(constraintSet)
        constraintSet.apply {
            connect(firstImage.id, ConstraintSet.TOP, id, ConstraintSet.TOP)
            connect(firstImage.id, ConstraintSet.BOTTOM, barrier.id, ConstraintSet.TOP)
            connect(secondImage.id, ConstraintSet.TOP, id, ConstraintSet.TOP)
            connect(barrier.id, ConstraintSet.TOP, firstImage.id, ConstraintSet.BOTTOM, GRID_SEPARATOR_SIZE)
            connect(thirdImage.id, ConstraintSet.TOP, barrier.id, ConstraintSet.BOTTOM)
            connect(thirdImage.id, ConstraintSet.BOTTOM, id, ConstraintSet.BOTTOM)
            connect(fourImage.id, ConstraintSet.TOP, barrier.id, ConstraintSet.BOTTOM)
            connect(fiveImage.id, ConstraintSet.TOP, barrier.id, ConstraintSet.BOTTOM)
        }
        picasso.loadInGrid(imageList[0].url, firstImage)
        picasso.loadInGrid(imageList[1].url, secondImage)
        picasso.loadInGrid(imageList[2].url, thirdImage)
        picasso.loadInGrid(imageList[3].url, fourImage)
        picasso.loadInGrid(imageList[4].url, fiveImage)
    }

    private fun addFourImage(imageList: List<ImageDto>, constraintSet: ConstraintSet) {
        val firstLineMass = addTwiceLine(imageList[0], imageList[1], constraintSet)
        val secondLineMass = addTwiceLine(imageList[2], imageList[3], constraintSet)
        val firstImage = firstLineMass[0]
        val secondImage = firstLineMass[1]
        val thirdImage = secondLineMass[0]
        val fourImage = secondLineMass[1]
        val barrier = addBarrier(constraintSet)
        Log.d(DEBUG_TAG, "firstImage = ${firstImage.id} secondImage = ${secondImage.id} thirdImage = ${thirdImage.id} fourImage = ${fourImage.id} ")
        constraintSet.apply {
            connect(firstImage.id, ConstraintSet.TOP, id, ConstraintSet.TOP)
            connect(firstImage.id, ConstraintSet.BOTTOM, barrier.id, ConstraintSet.TOP)
            connect(secondImage.id, ConstraintSet.TOP, id, ConstraintSet.TOP)
            connect(barrier.id, ConstraintSet.TOP, firstImage.id, ConstraintSet.BOTTOM, GRID_SEPARATOR_SIZE)
            connect(thirdImage.id, ConstraintSet.TOP, barrier.id, ConstraintSet.BOTTOM)
            connect(thirdImage.id, ConstraintSet.BOTTOM, id, ConstraintSet.BOTTOM)
            connect(fourImage.id, ConstraintSet.TOP, barrier.id, ConstraintSet.BOTTOM)
        }
        picasso.loadInGrid(imageList[0].url, firstImage)
        picasso.loadInGrid(imageList[1].url, secondImage)
        picasso.loadInGrid(imageList[2].url, thirdImage)
        picasso.loadInGrid(imageList[3].url, fourImage)
    }

    private fun addThreeImage(imageList: List<ImageDto>, constraintSet: ConstraintSet) {
        val firstImage = addSingleLine(imageList[0], constraintSet)
        val secondLineMass: Array<ImageView> = addTwiceLine(imageList[1], imageList[2], constraintSet)
        val secondImage = secondLineMass[0]
        val thirdImage = secondLineMass[1]
        val barrier = addBarrier(constraintSet)
        constraintSet.apply {
            connect(firstImage.id, ConstraintSet.TOP, id, ConstraintSet.TOP)
            connect(firstImage.id, ConstraintSet.BOTTOM, barrier.id, ConstraintSet.TOP)
            connect(barrier.id, ConstraintSet.TOP, firstImage.id, ConstraintSet.BOTTOM, GRID_SEPARATOR_SIZE)
            connect(secondImage.id, ConstraintSet.TOP, barrier.id, ConstraintSet.BOTTOM)
            connect(secondImage.id, ConstraintSet.BOTTOM, id, ConstraintSet.BOTTOM)
            connect(thirdImage.id, ConstraintSet.TOP, barrier.id, ConstraintSet.BOTTOM)
        }
        picasso.loadInGrid(imageList[0].url, firstImage)
        picasso.loadInGrid(imageList[1].url, secondImage)
        picasso.loadInGrid(imageList[2].url, thirdImage)
    }

    private fun addTwoImage(imageList: List<ImageDto>, constraintSet: ConstraintSet) {
        val lineMass: Array<ImageView> = addTwiceLine(imageList[0], imageList[1], constraintSet)
        val firstImage = lineMass[0]
        val secondImage = lineMass[1]
        constraintSet.apply {
            connect(firstImage.id, ConstraintSet.TOP, id, ConstraintSet.TOP)
            connect(firstImage.id, ConstraintSet.BOTTOM, id, ConstraintSet.BOTTOM)
            connect(secondImage.id, ConstraintSet.TOP, id, ConstraintSet.TOP)
        }
        picasso.loadInGrid(imageList[0].url, firstImage)
        picasso.loadInGrid(imageList[1].url, secondImage)
    }

    private fun addSingleImage(image: ImageDto, constraintSet: ConstraintSet) {
        val imageView = addSingleLine(image, constraintSet)
        constraintSet.apply {
            connect(imageView.id, ConstraintSet.TOP, id, ConstraintSet.TOP)
            connect(imageView.id, ConstraintSet.BOTTOM, id, ConstraintSet.BOTTOM)
        }
        picasso.loadInGrid(image.url, imageView)
    }

    private fun addQuadrupleLine(firstImageDto: ImageDto, secondImageDto: ImageDto,
                                 thirdImageDto: ImageDto, fourImageDto: ImageDto,
                                 constraintSet: ConstraintSet): Array<ImageView> {
        val firstImage = addImageView(firstImageDto, constraintSet)
        val secondImage = addImageView(secondImageDto, constraintSet)
        val thirdImage = addImageView(thirdImageDto, constraintSet)
        val fourImage = addImageView(fourImageDto, constraintSet)
        constraintSet.apply {
            connect(firstImage.id, ConstraintSet.START, id, ConstraintSet.START)
            connect(secondImage.id, ConstraintSet.START, firstImage.id, ConstraintSet.END, GRID_SEPARATOR_SIZE)
            connect(secondImage.id, ConstraintSet.BOTTOM, firstImage.id, ConstraintSet.BOTTOM)
            connect(thirdImage.id, ConstraintSet.START, secondImage.id, ConstraintSet.END, GRID_SEPARATOR_SIZE)
            connect(thirdImage.id, ConstraintSet.BOTTOM, secondImage.id, ConstraintSet.BOTTOM)
            connect(fourImage.id, ConstraintSet.START, secondImage.id, ConstraintSet.END, GRID_SEPARATOR_SIZE)
            connect(fourImage.id, ConstraintSet.BOTTOM, secondImage.id, ConstraintSet.BOTTOM)
            connect(fourImage.id, ConstraintSet.END, id, ConstraintSet.END)
        }
        return arrayOf(firstImage, secondImage, thirdImage, fourImage)
    }

    private fun addTripleLine(firstImageDto: ImageDto, secondImageDto: ImageDto, thirdImageDto: ImageDto,
                              constraintSet: ConstraintSet): Array<ImageView> {
        val firstImage = addImageView(firstImageDto, constraintSet)
        val secondImage = addImageView(secondImageDto, constraintSet)
        val thirdImage = addImageView(thirdImageDto, constraintSet)
        constraintSet.apply {
            connect(firstImage.id, ConstraintSet.START, id, ConstraintSet.START)
            connect(secondImage.id, ConstraintSet.START, firstImage.id, ConstraintSet.END, GRID_SEPARATOR_SIZE)
            connect(secondImage.id, ConstraintSet.BOTTOM, firstImage.id, ConstraintSet.BOTTOM)
            connect(thirdImage.id, ConstraintSet.START, secondImage.id, ConstraintSet.END, GRID_SEPARATOR_SIZE)
            connect(thirdImage.id, ConstraintSet.BOTTOM, secondImage.id, ConstraintSet.BOTTOM)
            connect(thirdImage.id, ConstraintSet.END, id, ConstraintSet.END)
        }
        return arrayOf(firstImage, secondImage, thirdImage)
    }

    private fun addTwiceLine(firstImageDto: ImageDto, secondImageDto: ImageDto,
                             constraintSet: ConstraintSet): Array<ImageView> {
        val firstImage = addImageView(firstImageDto, constraintSet)
        val secondImage = addImageView(secondImageDto, constraintSet)
        constraintSet.apply {
            connect(firstImage.id, ConstraintSet.START, id, ConstraintSet.START)
            connect(secondImage.id, ConstraintSet.START, firstImage.id, ConstraintSet.END, GRID_SEPARATOR_SIZE)
            connect(secondImage.id, ConstraintSet.BOTTOM, firstImage.id, ConstraintSet.BOTTOM)
            connect(secondImage.id, ConstraintSet.END, id, ConstraintSet.END)
        }
        return arrayOf(firstImage, secondImage)
    }

    private fun addSingleLine(firstImageDto: ImageDto, constraintSet: ConstraintSet): ImageView {
        val imageView = addImageView(firstImageDto, constraintSet)
        constraintSet.apply {
            connect(imageView.id, ConstraintSet.START, id, ConstraintSet.START)
            connect(imageView.id, ConstraintSet.END, id, ConstraintSet.END)
        }
        return imageView
    }

    private fun addImageView(image: ImageDto, constraintSet: ConstraintSet): ImageView {
        val imageView = imageView {
            id = View.generateViewId()
            layoutParams = ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, ConstraintLayout.LayoutParams.MATCH_CONSTRAINT)
        }
        constraintSet.setDimensionRatio(imageView.id, "${image.widthRatio}:${image.heightRatio}")
        imageView.setOnClickListener { debugLogging(image.toString()) }
        return imageView
    }

    private fun addBarrier(constraintSet: ConstraintSet): View {
        val view = frameLayout {
            id = View.generateViewId()
            layoutParams = ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
        }
        constraintSet.apply {
            connect(view.id, ConstraintSet.START, id, ConstraintSet.START)
            connect(view.id, ConstraintSet.END, id, ConstraintSet.END)
        }
        return view
    }

    companion object {
        const val GRID_SEPARATOR_SIZE = 2
    }
}