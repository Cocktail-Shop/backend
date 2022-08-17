package com.lionTF.cshop.domain.admin.service

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.tomcat.util.http.fileupload.IOUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.*
import org.springframework.http.client.SimpleClientHttpRequestFactory
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpMessageConverterExtractor
import org.springframework.web.client.RequestCallback
import org.springframework.web.client.RestTemplate
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Service
class ImageService(

    @Value("\${spring.img.authUrl}")
    private val authUrl: String? = null,

    @Value("\${spring.img.storageUrl}")
    private val storageUrl: String? = null,

    @Value("\${spring.img.tenantId}")
    private val tenantId: String? = null,

    @Value("\${spring.img.username}")
    private val username: String? = null,

    @Value("\${spring.img.password}")
    private val password: String? = null,
) {
    final val tokenRequest = TokenRequest()
    val restTemplate = RestTemplate()
    var tokenId: String = ""
    val containerName = "lion-test"



    init {
        tokenRequest.auth.tenantId = tenantId
        tokenRequest.auth.passwordCredentials.username = username
        tokenRequest.auth.passwordCredentials.password = password
    }

    inner class TokenRequest {
        val auth = Auth()

        inner class Auth {
            var tenantId: String? = null
            var passwordCredentials: PasswordCredentials = PasswordCredentials()
        }

        inner class PasswordCredentials {
            var username: String? = null
            var password: String? = null
        }

    }

    //토큰 발급
    fun requestToken(): Any? {
        val identityUrl = "$authUrl/tokens"

        // 헤더 생성
        val headers = HttpHeaders()
        headers.add("Content-Type", "application/json")
        val httpEntity: HttpEntity<TokenRequest> = HttpEntity<TokenRequest>(tokenRequest, headers)

        // 토큰 요청
        val response = restTemplate.exchange(
            identityUrl, HttpMethod.POST, httpEntity,
            String::class.java
        )
        val mapper = ObjectMapper()

        val body: JsonNode = mapper.readTree(response.body)

        tokenId=body.path("access").path("token").path("id").toString()

        return tokenId
    }

    // lion-test컨테이너에 있는 object
    fun getContainerObject():List<String>{
        val headers = HttpHeaders()
        headers.add("X-Auth-Token", tokenId)

        val requestHttpEntity = HttpEntity<String>(null, headers)

        // API 호출

        // API 호출
        val response: ResponseEntity<String> = restTemplate.exchange(
            this.storageUrl, HttpMethod.GET, requestHttpEntity,
            String::class.java
        )

        var containerList: List<String>? = null
        if (response.statusCode == HttpStatus.OK) {
            // String으로 받은 목록을 배열로 변환
            containerList =
                response.body!!.split("\\r?\\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray().toList()
        }

        // 배열을 List로 변환하여 반환
        return ArrayList(containerList)
    }
    private fun getUrl(containerName: String?): String? {
        return this.storageUrl + "/" + containerName
    }


    fun getObjectList(): List<String?>? {
        return getList(this.getUrl(containerName))
    }

    fun getList(url: String?): List<String?>? {
        // 헤더 생성
        val headers = HttpHeaders()
        headers.add("X-Auth-Token", tokenId)
        val requestHttpEntity = HttpEntity<String>(null, headers)

        // API 호출
        val response = restTemplate.exchange(
            url!!, HttpMethod.GET, requestHttpEntity,
            String::class.java
        )
        return if (response.statusCode == HttpStatus.OK) {
            // String으로 받은 목록을 배열로 변환
            response.body!!.split("\\r?\\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray().toList()
        } else Collections.emptyList()
    }

    //오브젝트(사진) 업로드 또는 수정
    fun getUrl(containerName: String, objectName: String): String {
        return this.storageUrl + "/" + containerName + "/" + objectName
    }

    fun uploadObject(objectName: String,multipartFile: MultipartFile) : String{
        val url = getUrl(containerName, objectName)
        val inputStream= multipartFile.inputStream
        val requestCallback = RequestCallback { request ->
            request.headers.add("X-Auth-Token", tokenId)
            IOUtils.copy(inputStream, request.body)
        }

        val requestFactory = SimpleClientHttpRequestFactory()
        requestFactory.setBufferRequestBody(false)

        val restTemplate = RestTemplate(requestFactory)

        val responseExtractor = HttpMessageConverterExtractor(
            String::class.java, restTemplate.messageConverters
        )
        restTemplate.execute(url, HttpMethod.PUT, requestCallback, responseExtractor)

        return url
    }

    //delete object
    fun deleteObject(objectName: String?) {
        val url = this.getUrl(containerName, objectName!!)

        // 헤더 생성
        val headers = HttpHeaders()
        headers.add("X-Auth-Token", tokenId)
        val requestHttpEntity = HttpEntity<String>(null, headers)

        // API 호출
        restTemplate.exchange(url, HttpMethod.DELETE, requestHttpEntity, String::class.java)
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val imageService=ImageService()
            imageService.requestToken()

        }
    }

}
