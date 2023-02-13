package com.spbdemosss.gpstrackercurse

import android.app.Application
import com.spbdemosss.gpstrackercurse.db.MainDb

class MainApp : Application() {
    val database by lazy { MainDb.getDatabase(this) }
}