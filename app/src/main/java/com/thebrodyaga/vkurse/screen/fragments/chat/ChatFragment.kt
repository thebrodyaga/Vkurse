package com.thebrodyaga.vkurse.screen.fragments.chat


import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.fivehundredpx.greedolayout.GreedoLayoutManager
import com.thebrodyaga.vkurse.R
import com.thebrodyaga.vkurse.domain.entities.ui.ImageDto
import com.thebrodyaga.vkurse.screen.adapters.ImageGridAdapter
import kotlinx.android.synthetic.main.fragment_chat.*
import java.util.*


class ChatFragment : MvpAppCompatFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageForGrid = mutableListOf<ImageDto>()
        val i = Random().nextInt(9)
        for (j in 0..i) imageForGrid
                .add(ImageDto("https://pp.userapi.com/c845217/v845217470/62f58/wtcB52CCCkM.jpg",
                        1920, 2560))
        val recyclerAdapter = ImageGridAdapter(mutableListOf())
        val layoutManager = GreedoLayoutManager(recyclerAdapter)
        layoutManager.setFixedHeight(true)
        content_view.layoutManager = layoutManager
        content_view.adapter = recyclerAdapter

        recyclerAdapter.setData(imageForGrid)
        content_view.post {
            content_view.height
        }
    }
}
