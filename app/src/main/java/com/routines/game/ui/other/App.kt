package com.routines.game.ui.other

import android.app.Application
import com.routines.game.data.data_base.Database

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        Database.init(this)
    }
}