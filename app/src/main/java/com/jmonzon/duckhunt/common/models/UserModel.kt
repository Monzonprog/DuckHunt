package com.jmonzon.duckhunt.common.models

data class UserModel(var nick: String, var ducks: Int) {
    constructor() : this("", 0)
}