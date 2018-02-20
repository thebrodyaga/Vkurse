package com.example.win10.vkurse.mvp.presenters

import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import rx.Subscription
import rx.subscriptions.CompositeSubscription

/**
 * Created by Emelyanov.N4 on 19.02.2018.
 */
open class BasePresenter<View : MvpView> : MvpPresenter<View>() {
    private val compositeSubscription: CompositeSubscription = CompositeSubscription()

    protected fun unsubscribeOnDestroy(subscription: Subscription) {
        compositeSubscription.add(subscription)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeSubscription.clear()
    }
}