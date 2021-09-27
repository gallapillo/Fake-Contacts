package com.gallapillo.fakecontacts

import android.app.Application
import com.gallapillo.fakecontacts.model.UserService

/* Класс приложения для получение доступа класса UserService */
class App : Application() {

    val userService = UserService()
}