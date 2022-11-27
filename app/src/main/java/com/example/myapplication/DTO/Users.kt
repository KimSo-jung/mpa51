package com.example.myapplication.DTO

data class Users(
    var uid: String? = null,
    var nickname: String? = null,
    var profileImage: String?, var BDay: String? = null,
    var friends: List<String>? = listOf(),

){
    companion object {
        const val INVALID_USER = "-1"
    }
}
