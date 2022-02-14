package com.bosha.core.view

import androidx.lifecycle.ViewModel
import com.bosha.core.DataStateEmitter
import com.bosha.core.Event
import com.bosha.core.EventEmitter

open class BaseViewModel : ViewModel(), EventEmitter, DataStateEmitter {
    val errorEvent = Event<ErrorConfig>()
}