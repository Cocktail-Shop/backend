package com.lionTF.cshop.domain.admin.service.admininterface

import org.springframework.web.multipart.MultipartFile

interface ImageService {

    fun requestToken(): Any?

    fun getContainerObject(): List<String>

    fun getObjectList(): List<String?>?

    fun getList(url: String?): List<String?>?

    fun getUrl(containerName: String, objectName: String): String

    fun uploadObject(objectName: String, multipartFile: MultipartFile): String

    fun deleteObject(objectName: String?)
}