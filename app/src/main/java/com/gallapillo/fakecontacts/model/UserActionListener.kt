package com.gallapillo.fakecontacts.model

/* Интерфейс для мменю контакта пользователя */
interface UserActionListener {

    fun onUserMove(user: User, moveBy: Int)

    fun onUserDelete(user: User)

    fun onUserDetails(user: User)

}