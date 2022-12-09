package com.example.msg_app.application

import android.app.Application
import com.example.msg_app.repository.MsgAppRepository

class MsgAppApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        MsgAppRepository.initialize()
    }
}