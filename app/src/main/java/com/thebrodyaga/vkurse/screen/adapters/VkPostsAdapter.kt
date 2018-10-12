package com.thebrodyaga.vkurse.screen.adapters

import android.support.v4.content.ContextCompat
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.text.util.Linkify
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import at.blogc.android.views.ExpandableTextView
import com.thebrodyaga.vkobjects.audio.AudioFull
import com.thebrodyaga.vkobjects.base.Link
import com.thebrodyaga.vkobjects.docs.Doc
import com.thebrodyaga.vkobjects.market.MarketAlbum
import com.thebrodyaga.vkobjects.market.MarketItem
import com.thebrodyaga.vkobjects.pages.WikipageFull
import com.thebrodyaga.vkobjects.photos.Photo
import com.thebrodyaga.vkobjects.photos.PhotoAlbum
import com.thebrodyaga.vkobjects.polls.Poll
import com.thebrodyaga.vkobjects.video.Video
import com.thebrodyaga.vkobjects.wall.AttachedNote
import com.thebrodyaga.vkobjects.wall.WallPostFull
import com.thebrodyaga.vkobjects.wall.WallpostAttachmentType
import com.thebrodyaga.vkurse.R
import com.thebrodyaga.vkurse.application.App
import com.thebrodyaga.vkurse.common.getDate
import com.thebrodyaga.vkurse.domain.entities.ui.ItemModel
import com.thebrodyaga.vkurse.domain.entities.ui.postList.ItemsForPostList
import com.thebrodyaga.vkurse.domain.entities.ui.postList.VkPostItem
import com.thebrodyaga.vkurse.screen.base.BaseAdapter
import com.thebrodyaga.vkurse.screen.base.BaseListAdapter
import com.thebrodyaga.vkurse.screen.utils.countToText
import com.thebrodyaga.vkurse.screen.utils.visibleOrGone
import com.volokh.danylo.hashtaghelper.HashTagHelper
import kotlinx.android.synthetic.main.item_post.view.*


/**
 * Created by Emelyanov.N4
 *         on 03.03.2018.
 */
class VkPostsAdapter : BaseListAdapter<ItemModel<ItemsForPostList>>(VkPostDiffCallback()) {
    //  first id source, second id post
    private val expandedViews = mutableSetOf<Pair<Int, Int>>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            when (viewType) {
                ItemsForPostList.POST.viewType -> PostHolder(LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_post, parent, false)).apply { itemView.post_text.setCollapseInterpolator { 1.0F } }
                ItemsForPostList.PROGRESS.viewType -> BaseAdapter.ProgressHolder(LayoutInflater.from(parent.context)
                        .inflate(R.layout.middle_progress_bar, parent, false))
                else -> throw RuntimeException("Not valid viewType")
            }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PostHolder -> holder.bind(getItem(position) as VkPostItem, onListItemClick, expandedViews)
        }
    }

    override fun getItemViewType(position: Int): Int =
            getItem(position).getItemType().viewType


    class PostHolder(containerView: View) : RecyclerView.ViewHolder(containerView) {
        fun bind(vkPostItem: VkPostItem,
                 onListItemClick: ((item: ItemModel<ItemsForPostList>, position: Int, view: View) -> Unit)?,
                 expandedViews: MutableSet<Pair<Int, Int>>) = with(itemView) {

            setOnClickListener { onListItemClick?.invoke(vkPostItem, adapterPosition, it) }
            val post = vkPostItem.vkPost.wallPostFull
            val groups = vkPostItem.vkPost.groups
            val profiles = vkPostItem.vkPost.profiles
            val source = groups[-1 * post.ownerId] ?: return@with
            post_title.text = (source.name)
            post_subtitle.text = getDate(post.date.toLong() * 1000)
            App.appComponent.getPicasso().load(source.photo50).into(profile_image)
            like_count.text = post.likes.count.countToText()
            comment_count.text = post.comments.count.countToText()
            view_count.text = (post.views?.count ?: 0).countToText()

            buildTextView(post_text, show_more, post, expandedViews)

            val photoList = mutableListOf<Photo>()
            val videoList = mutableListOf<Video>()
            var link: Link? = null
            var doc: Doc? = null
            var audioFull: AudioFull? = null
            var note: AttachedNote? = null
            var poll: Poll? = null
            var wikiPageFull: WikipageFull? = null
            val photoAlbumList = mutableListOf<PhotoAlbum>()
            var photosList: List<String>? = null
            val marketAlbum = mutableListOf<MarketAlbum>()
            val marketItem: MarketItem? = null

            post.attachments?.forEach {
                when (it.type ?: return@forEach) {
                    WallpostAttachmentType.PHOTO -> photoList.add(it.photo)
                    WallpostAttachmentType.POSTED_PHOTO -> TODO()   //для записей раньше 2013
                    WallpostAttachmentType.AUDIO -> audioFull = it.audio
                    WallpostAttachmentType.VIDEO -> videoList.add(it.video)
                    WallpostAttachmentType.DOC -> doc = it.doc
                    WallpostAttachmentType.LINK -> link = it.link
                    WallpostAttachmentType.GRAFFITI -> TODO()   //для записей раньше 2013
                    WallpostAttachmentType.NOTE -> note = it.note
                    WallpostAttachmentType.APP -> TODO()    //для записей раньше 2013
                    WallpostAttachmentType.POLL -> it.poll
                    WallpostAttachmentType.PAGE -> wikiPageFull = it.page
                    WallpostAttachmentType.ALBUM -> photoAlbumList.add(it.album)
                    WallpostAttachmentType.PHOTOS_LIST -> photosList = it.photosList
                    WallpostAttachmentType.MARKET_MARKET_ALBUM -> it.marketMarketAlbum
                    WallpostAttachmentType.MARKET -> it.market
                }
            }
        }

        private fun buildTextView(post_text: ExpandableTextView, show_more: TextView,
                                  post: WallPostFull, expandedViews: MutableSet<Pair<Int, Int>>) {
            post_text.text = post.text.trim()
            if (post.text.isEmpty()) {
                post_text.visibleOrGone(false)
                show_more.visibleOrGone(false)
                return
            } else post_text.visibleOrGone(true)
            val isExpand = expandedViews.contains(Pair(post.ownerId, post.id))
            post_text.setAnimationDuration(if (isExpand) 0 else 500)
            show_more.visibleOrGone(!isExpand)
            if (isExpand) post_text.expand() else post_text.collapse()
            show_more.setOnClickListener {
                post_text.expand()
                it.visibility = View.GONE
                expandedViews.add(Pair(post.ownerId, post.id))
            }
            HashTagHelper.Creator
                    .create(ContextCompat.getColor(post_text.context, R.color.colorAccent),
                            { Toast.makeText(post_text.context, it, Toast.LENGTH_SHORT).show() },
                            charArrayOf('_'))
                    .handle(post_text)
            Linkify.addLinks(post_text, Linkify.WEB_URLS)
        }

    }

    private class VkPostDiffCallback : DiffUtil.ItemCallback<ItemModel<ItemsForPostList>>() {
        override fun areItemsTheSame(old: ItemModel<ItemsForPostList>, new: ItemModel<ItemsForPostList>): Boolean {
            val oldType = old.getItemType()
            val newType = new.getItemType()
            return when {
                oldType != newType -> false
                oldType == ItemsForPostList.POST && newType == ItemsForPostList.POST ->
                    (old as VkPostItem).vkPost.wallPostFull.id == (new as VkPostItem).vkPost.wallPostFull.id
                oldType == newType -> true
                else -> false
            }
        }

        override fun areContentsTheSame(old: ItemModel<ItemsForPostList>, new: ItemModel<ItemsForPostList>): Boolean {
            val oldType = old.getItemType()
            val newType = new.getItemType()
            return when {
                oldType != newType -> false
                oldType == ItemsForPostList.POST && newType == ItemsForPostList.POST ->
                    (old as VkPostItem).vkPost.wallPostFull == (new as VkPostItem).vkPost.wallPostFull
                oldType == newType -> true
                else -> false
            }
        }
    }
}