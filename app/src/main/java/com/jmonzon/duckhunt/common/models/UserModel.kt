package com.jmonzon.duckhunt.common.models

data class UserModel(var nick: String, var duck: Int) {
    constructor() : this("", 0)
}