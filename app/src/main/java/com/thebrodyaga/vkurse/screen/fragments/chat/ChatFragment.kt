package com.thebrodyaga.vkurse.screen.fragments.chat


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.thebrodyaga.vkurse.R
import com.thebrodyaga.vkurse.screen.utils.toggleShowMore
import kotlinx.android.synthetic.main.fragment_chat.*


class ChatFragment : MvpAppCompatFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        post_text.text = /*post.text*/"\nsfdvd".repeat(10)
        post_text.toggleShowMore(show_more)
    }

    companion object {
        const val FragmentTAG = "ChatFragmentTAG"
    }
}
