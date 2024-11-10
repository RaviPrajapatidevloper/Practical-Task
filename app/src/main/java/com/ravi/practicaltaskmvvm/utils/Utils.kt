package com.ravi.practicaltaskmvvm.utils

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

inline fun <T : Any> Fragment.observeNotNull(
    liveData: LiveData<T>,
    crossinline observer: (t: T) -> Unit
): Observer<T> = Observer<T> {
    observer(it)
}.also { liveData.observe(this, it) }


fun roundToThousands(value: Int): String {
    val prefix = "star "
    return when {
        value < 100 -> "${prefix}${value}"
        value < 1000 -> "$prefix$value"
        else -> {
            val roundedValue = value / 1000.0
            val roundedString = String.format("%.1f", roundedValue)
            "$prefix${roundedString}k"
        }
    }
}

