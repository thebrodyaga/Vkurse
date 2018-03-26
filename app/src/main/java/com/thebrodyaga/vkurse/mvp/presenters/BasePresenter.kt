package com.thebrodyaga.vkurse.mvp.presenters

import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by Emelyanov.N4
 *         on 19.02.2018.
 */
open class BasePresenter<View : MvpView> : MvpPresenter<View>() {
        private val compositeDisposable: CompositeDisposable = CompositeDisposable()

        protected fun unsubscribeOnDestroy(disposable: Disposable) {
            compositeDisposable.add(disposable)
        }

        override fun onDestroy() {
            super.onDestroy()
            compositeDisposable.clear()
        }
}