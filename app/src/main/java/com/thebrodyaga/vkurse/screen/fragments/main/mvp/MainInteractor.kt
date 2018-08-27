package com.thebrodyaga.vkurse.screen.fragments.main.mvp

import android.util.Log
import com.thebrodyaga.vkurse.application.di.scopes.ActivityScope
import com.thebrodyaga.vkurse.common.INSTANCE_TAG
import com.thebrodyaga.vkurse.models.room.VkGroup
import com.thebrodyaga.vkurse.repository.GroupRepository
import com.thebrodyaga.vkurse.repository.PostRepository
import io.reactivex.Flowable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

/**
 * Created by Win10
 *         on 17.04.2018.
 */
@ActivityScope
class MainInteractor(private val postRepository: PostRepository,
                     private val groupRepository: GroupRepository) {
    init {
        Log.d(INSTANCE_TAG, "${this.javaClass.simpleName} id: ${System.identityHashCode(this)}")
    }

    val scrollObservable: PublishSubject<Int> = PublishSubject.create<Int>()
    val searchObservable: PublishSubject<String> = PublishSubject.create<String>()
    val visibleObservable: BehaviorSubject<Int> = BehaviorSubject.create<Int>()

    fun searchControl(query: String?) {
        if (query != null && query.isNotBlank()) searchObservable.onNext(query)
        else searchObservable.onNext("")
    }

    fun scrollToTop(position: Int) {
        scrollObservable.onNext(position)
    }

    fun visibleFragment(position: Int) {
        visibleObservable.onNext(position)
    }

    fun getFavoriteGroups(): Flowable<List<VkGroup>> {
        return groupRepository.getFavoriteGroups()
    }

    fun addFavoriteGroup(vkGroup: VkGroup) {
        groupRepository.addFavoriteGroup(vkGroup)
    }
}