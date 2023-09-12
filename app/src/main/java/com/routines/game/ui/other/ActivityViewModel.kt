package com.routines.game.ui.other

import androidx.lifecycle.ViewModel

class ActivityViewModel: ViewModel() {
    var callback: (()->Unit)? = null
}