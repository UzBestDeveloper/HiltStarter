package com.example.hiltstarter.utils.lifecycle

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.hiltstarter.utils.info
import java.util.concurrent.atomic.AtomicBoolean

class SingleEvent<T> : MutableLiveData<T>() {
    private val pending = AtomicBoolean(false)

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        if (hasActiveObservers()) {
            info("Multiple observers registered but only one will be notified of changes")
        }
        // Observe the internal MutableLiveData
        super.observe(owner, Observer {
            if (pending.compareAndSet(true, false))
                observer.onChanged(it)
        })
    }

    @MainThread
    override fun setValue(value: T?) {
        pending.set(true)
        super.setValue(value)
    }

    /**
     * Used for cases where T is Void, to make calls cleaner
     * */
    @MainThread
    fun call() {
        value = null
    }


}