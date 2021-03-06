package com.thebrodyaga.vkurse.screen.base

import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by Emelyanov.N4
 *         on 19.02.2018.
 */
abstract class BasePresenter<View : MvpView> : MvpPresenter<View>() {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    protected fun unSubscribeOnDestroy(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    open fun onBackPressed() {}

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}