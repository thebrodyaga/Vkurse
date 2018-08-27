package com.thebrodyaga.vkurse.screen.fragments.main


import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter
import com.thebrodyaga.vkurse.R
import com.thebrodyaga.vkurse.screen.base.BaseFragment
import com.thebrodyaga.vkurse.screen.chat.ChatFragment
import com.thebrodyaga.vkurse.screen.fragments.main.mvp.MainPresenter
import com.thebrodyaga.vkurse.screen.fragments.main.mvp.MainView
import com.thebrodyaga.vkurse.screen.groupList.VkListGroupsFragment
import com.thebrodyaga.vkurse.screen.postList.VkListPostsFragment
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject

class MainFragment : BaseFragment(), MainView {
    private var postsFragment: VkListPostsFragment? = null
    private var groupsFragment: VkListGroupsFragment? = null
    private var chatFragment: ChatFragment? = null

    @Inject
    @InjectPresenter
    lateinit var presenter: MainPresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initContainers()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_main, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AHBottomNavigationAdapter(activity, R.menu.main_bottom_bar).setupWithBottomNavigation(bottom_navigation)
        bottom_navigation.isBehaviorTranslationEnabled = false
        bottom_navigation.defaultBackgroundColor = ContextCompat.getColor(view.context, R.color.primaryColor)
        bottom_navigation.setOnTabSelectedListener { position, wasSelected ->
            if (wasSelected) presenter.onBottomBarReClick(position)
            else presenter.onBottomBarClick(position)
        }
        if (savedInstanceState == null) presenter.onBottomBarClick(MainView.POSTS_PAGE.first)
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

    override fun showPage(fragmentTag: String) {
        val fragmentTransaction = childFragmentManager.beginTransaction()
        when (fragmentTag) {
            MainView.POSTS_PAGE.second -> fragmentTransaction
                    .attach(postsFragment!!)
                    .detach(groupsFragment!!)
                    .detach(chatFragment!!)
            MainView.SEARCH_PAGE.second -> fragmentTransaction
                    .attach(groupsFragment!!)
                    .detach(postsFragment!!)
                    .detach(chatFragment!!)
            MainView.CHAT_PAGE.second -> fragmentTransaction
                    .attach(chatFragment!!)
                    .detach(groupsFragment!!)
                    .detach(postsFragment!!)
        }
        fragmentTransaction.commitNow()
    }

    private fun initContainers() {
        val fm = childFragmentManager
        postsFragment = fm.findFragmentByTag(MainView.POSTS_PAGE.second) as? VkListPostsFragment
        if (postsFragment == null) {
            postsFragment = VkListPostsFragment()
            fm.beginTransaction()
                    .add(R.id.main_fragment_container, postsFragment!!, MainView.POSTS_PAGE.second)
                    .detach(postsFragment!!).commitNow()
        }

        groupsFragment = fm.findFragmentByTag(MainView.SEARCH_PAGE.second) as? VkListGroupsFragment
        if (groupsFragment == null) {
            groupsFragment = VkListGroupsFragment()
            fm.beginTransaction()
                    .add(R.id.main_fragment_container, groupsFragment!!, MainView.SEARCH_PAGE.second)
                    .detach(groupsFragment!!).commitNow()
        }

        chatFragment = fm.findFragmentByTag(MainView.CHAT_PAGE.second) as? ChatFragment
        if (chatFragment == null) {
            chatFragment = ChatFragment()
            fm.beginTransaction()
                    .add(R.id.main_fragment_container, chatFragment!!, MainView.CHAT_PAGE.second)
                    .detach(chatFragment!!).commitNow()
        }
    }
}
