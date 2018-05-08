package com.thebrodyaga.vkurse.ui.main.mvp

import com.thebrodyaga.vkurse.application.di.scopes.PerActivity
import com.thebrodyaga.vkurse.data.db.RoomDatabase
import com.thebrodyaga.vkurse.data.net.VkService
import com.thebrodyaga.vkurse.models.room.VkGroup
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

/**
 * Created by Win10
 *         on 17.04.2018.
 */
@PerActivity
class MainInteractor @Inject constructor(
        private val vkService: VkService,
        private val roomDatabase: RoomDatabase) {

    val scrollObservable: PublishSubject<Int> = PublishSubject.create<Int>()
    val searchObservable: PublishSubject<String> = PublishSubject.create<String>()

    fun searchControl(query: String?) {
        if (query != null && query.isNotBlank()) searchObservable.onNext(query)
        else searchObservable.onNext("")
    }

    fun scrollToTop(position: Int) {
        scrollObservable.onNext(position)
    }

    fun getFavoriteGroups(): Flowable<List<VkGroup>> {
        return roomDatabase.getGroupDao().getFlowableGroups()
    }

    fun getSingleGroups(): Single<List<VkGroup>> {
        return roomDatabase.getGroupDao().getSingleGroups()
    }

    fun insertGroup(vkGroup: VkGroup) {
        roomDatabase.getGroupDao().insertGroups(vkGroup)
    }
}