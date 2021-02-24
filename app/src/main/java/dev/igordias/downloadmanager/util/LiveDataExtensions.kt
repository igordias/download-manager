package dev.igordias.downloadmanager.util

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import dev.igordias.downloadmanager.util.arch.Event
import dev.igordias.downloadmanager.util.arch.EventObserver

fun <T> LiveData<T>.observe(owner: LifecycleOwner, observer: (T?) -> Unit) {
    observe(owner, Observer { observer(it) })
}

fun <T> LiveData<Event<T>>.observeEvent(owner: LifecycleOwner, observer: (T?) -> Unit) {
    observe(owner, EventObserver(observer))
}