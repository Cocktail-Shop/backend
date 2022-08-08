package com.lionTF.CShop.domain.admin.image.service

import org.apache.tomcat.util.http.fileupload.IOUtils
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.client.SimpleClientHttpRequestFactory
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpMessageConverterExtractor
import org.springframework.web.client.RequestCallback
import org.springframework.web.client.RestTemplate
import java.io.InputStream
import java.util.*

@Service
class ObjectService {
    var storageUrl = "https://api-storage.cloud.toast.com/v1/AUTH_507cc2a432bc43de8721f24810f3daa1"
    var containerName = "lion-test"
    var tokenId = ""
    var restTemplate = RestTemplate()

    fun insertTokenId(tokenId : String){
        this.tokenId = tokenId
    }

    private fun getUrl(containerName: String, objectName: String): String {
        return this.storageUrl + "/" + containerName + "/" + objectName
    }

    fun uploadObject(containerName: String, objectName: String, inputStream: InputStream?) : String{
        var url = getUrl(containerName, objectName)
        var requestCallback = RequestCallback { request ->
            request.headers.add("X-Auth-Token", tokenId)
            IOUtils.copy(inputStream, request.body)
        }
        var requestFactory = SimpleClientHttpRequestFactory()
        requestFactory.setBufferRequestBody(false)
        var restTemplate = RestTemplate(requestFactory)

        var responseExtractor = HttpMessageConverterExtractor(
            String::class.java, restTemplate.messageConverters
        )
        restTemplate.execute(url, HttpMethod.PUT, requestCallback, responseExtractor)
        return url
    }

    fun deleteObject(objectName: String) {
        val url = getUrl(containerName, objectName)
        val headers = HttpHeaders()
        headers.add("X-Auth-Token", tokenId)
        val requestHttpEntity: HttpEntity<String> = HttpEntity<String>(null, headers)
        restTemplate.exchange(url, HttpMethod.DELETE, requestHttpEntity, String::class.java)
    }

    fun uploadImage(inputStream: InputStream?) : String{
        try {
            var uuid = UUID.randomUUID().toString()
            val url = uploadObject(containerName, uuid, inputStream)
            return url
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    fun deleteImage(objectName : String){
        try {
            deleteObject(objectName)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }
}