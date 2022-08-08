package com.lionTF.CShop.domain.admin.image.service

import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate


@Service
class AuthService {

    var authUrl = "https://api-identity.infrastructure.cloud.toast.com/v2.0"
    var tenantId = "507cc2a432bc43de8721f24810f3daa1"
    var username = "jongsoo-lee@nhn-commerce.com"
    var password = "1111"
    var tokenRequest = TokenRequest()
    var restTemplate = RestTemplate()

    inner class TokenRequest {
        var auth = Auth()

        inner class Auth {
            var tenantId: String? = null
            var passwordCredentials: PasswordCredentials = PasswordCredentials()
        }

        inner class PasswordCredentials {
            var username: String? = null
            var password: String? = null
        }
    }

    init {
        // 요청 본문 생성
        tokenRequest = TokenRequest()
        tokenRequest.auth.tenantId = tenantId
        tokenRequest.auth.passwordCredentials.username = username
        tokenRequest.auth.passwordCredentials.password = password
        restTemplate = RestTemplate()
    }

    fun requestToken(): String? {
        var identityUrl = authUrl + "/tokens"

        // 헤더 생성
        var headers = org.springframework.http.HttpHeaders()
        headers.add("Content-Type", "application/json")
        var httpEntity: HttpEntity<TokenRequest> = HttpEntity<TokenRequest>(tokenRequest, headers)

        // 토큰 요청
        var response = restTemplate.exchange(
            identityUrl, HttpMethod.POST, httpEntity,
            String::class.java
        )
        return response.body
    }

    fun generateToken() : String?{
        var authService = AuthService()
        var token = authService.requestToken()
        println(token)
        return token
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            var authService = AuthService()
            authService.generateToken()
        }
    }
}