package com.perfex.app.main.ui.base.component

import android.app.Activity
import android.os.Bundle
import androidx.lifecycle.*
import com.perfex.app.main.ui.base.callback.MvpView
import com.perfex.app.main.ui.base.callback.Presenter
import io.reactivex.disposables.CompositeDisposable

/**
 * This is base presenter class which will be extended for creating next presenter classes
 * @author Mohd. Asfaq-E-Azam Rifat
 * */
abstract class BasePresenter<V : MvpView> : LifecycleObserver, Presenter<V> {

    /**
     * Marks a class as a LifecycleObserver.
     * It does not have any methods, instead,
     * relies on OnLifecycleEvent annotated methods.
     *
     *
     * class TestObserver implements LifecycleObserver {
     *
     * @OnLifecycleEvent(ON_CREATE) void onCreated(LifecycleOwner source) {}
     * @OnLifecycleEvent(ON_ANY) void onAny(LifecycleOwner source, Event event) {}
     * }
     */
    @Volatile
    var mvpView: V? = null
        private set

    private var mStateBundle: Bundle? = null
        get() {
            if (field == null) {
                mStateBundle = Bundle()
            }
            return field
        }

    var activity: Activity? = null
    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    /**
     * Check MvpView attached with presenter or not
     *
     * @return boolean value is view attached state
     */
    private val isViewAttached: Boolean
        get() = mvpView != null

    /**
     * Getter method for current LifecycleOwner of active activity
     *
     * @return [LifecycleOwner] object
     */
    protected val lifecycleOwner: LifecycleOwner
        get() = (activity as LifecycleOwner?)!!

    /**
     * This method is called while attaching the view
     * */
    override fun attachView(mvpView: V) {
        this.mvpView = mvpView
    }

    /**
     * This method is called while detaching view
     * */
    override fun detachView() {
       // ProgressDialogUtils.on().hideProgressDialog()
        this.compositeDisposable.dispose()
        mvpView = null
    }

    /**
     * This method is called while attaching the lifecycle
     * */
    override fun attachLifecycle(lifecycle: Lifecycle) {
        lifecycle.addObserver(this)
    }

    /**
     * This method is called while detaching the lifecycle
     * */
    override fun detachLifecycle(lifecycle: Lifecycle) {
        lifecycle.removeObserver(this)
    }

    /**
     * This method checks [MvpView] attached with presenter, otherwise throw exceptions
     */
    fun checkViewAttached() {
        if (!isViewAttached) {
            throw MvpViewNotAttachedException()
        }
    }

    /**
     * This method is fired when presenter gets destroyed
     * */
    override fun onPresenterDestroy() {
        if (mStateBundle != null && !mStateBundle!!.isEmpty) {
            mStateBundle!!.clear()
        }
    }

    /**
     * This method is fired when presenter gets created
     * */
    override fun onPresenterCreated() {
        // Do nothing
    }



    class MvpViewNotAttachedException :
            RuntimeException("Please call Presenter.attachView(MvpView) before requesting data to the Presenter")
}