package com.learning.loginsignupwithmvvm.repository

import com.learning.loginsignupwithmvvm.network.AuthApi

class AuthRepository(
    private val api: AuthApi
): BaseRepository() {
    suspend fun login(
        email: String,
        password: String
    ) = safeApiCall {
        api.login(email, password)
    }
}