package de.moritzbruder.mvp_match_trial.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

fun <A, B, C> LiveData<A>.zip(stream: LiveData<B>, func: (source1: A?, source2: B?) -> C): LiveData<C> {
    val result = MediatorLiveData<C>()
    result.addSource(this) { a ->
        result.setValue(func.invoke(a,stream.value))
    }
    result.addSource(stream) { b ->
        result.setValue(func.invoke(this.value, b))
    }
    return result
}