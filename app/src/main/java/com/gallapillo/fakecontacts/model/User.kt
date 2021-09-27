package com.gallapillo.fakecontacts.model

/* Класс модели пользователя */

data class User (
    val id: Long,
    val photo: String,
    val name: String,
    val company: String
    )