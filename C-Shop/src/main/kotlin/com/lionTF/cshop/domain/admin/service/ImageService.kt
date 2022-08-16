//package com.lionTF.CShop.domain.admin.service
//
//class ImageService {
//    private val tokenId: String? = null
//    private val storageUrl: String? = null
//    private var restTemplate: RestTemplate? = null
//
//    fun ObjectService(storageUrl: String?, tokenId: String?) {
//        this.setStorageUrl(storageUrl)
//        this.setTokenId(tokenId)
//        restTemplate = RestTemplate()
//    }
//
//    private fun getUrl(@NonNull containerName: String, @NonNull objectName: String): String {
//        return this.getStorageUrl().toString() + "/" + containerName + "/" + objectName
//    }
//
//    fun uploadObject(containerName: String, objectName: String, inputStream: InputStream?) {
//        val url = getUrl(containerName, objectName)
//
//        // InputStream을 요청 본문에 추가할 수 있도록 RequestCallback 오버라이드
//        val requestCallback = RequestCallback { request ->
//            request.headers.add("X-Auth-Token", tokenId)
//            IOUtils.copy(inputStream, request.body)
//        }
//
//        // 오버라이드한 RequestCallback을 사용할 수 있도록 설정
//        val requestFactory = SimpleClientHttpRequestFactory()
//        requestFactory.setBufferRequestBody(false)
//        val restTemplate = RestTemplate(requestFactory)
//        val responseExtractor = HttpMessageConverterExtractor(
//            String::class.java, restTemplate.messageConverters
//        )
//
//        // API 호출
//        restTemplate.execute(url, HttpMethod.PUT, requestCallback, responseExtractor)
//    }
//
//    @JvmStatic
//    fun main(args: Array<String>) {
//        val storageUrl = "https://api-storage.cloud.toast.com/v1/AUTH_*****"
//        val tokenId = "d052a0a054b745dbac74250b7fecbc09"
//        val containerName = "test"
//        val objectPath = "/home/example/"
//        val objectName = "46432aa503ab715f288c4922911d2035.jpg"
//        val objectService = ObjectService(storageUrl, tokenId)
//        try {
//            // 파일로 부터 InputStream 생성
//            val objFile = File("$objectPath/$objectName")
//            val inputStream: InputStream = FileInputStream(objFile)
//
//            // 업로드
//            objectService.uploadObject(containerName, objectName, inputStream)
//            println("\nUpload OK")
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }
//}