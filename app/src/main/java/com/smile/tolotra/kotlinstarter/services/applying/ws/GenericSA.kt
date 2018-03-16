package com.smile.tolotra.kotlinstarter.services.applying.ws

import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.DownloadListener
import com.androidnetworking.interfaces.ParsedRequestListener
import com.smile.tolotra.kotlinstarter.services.business.GenericBdl
import java.io.File

abstract class GenericSA {

    val SERVER_ERROR = "Erreur lors de la connexion au serveur."

    fun getDefaultHeader(token: String) = mapOf("Authorization" to "Bearer $token")

    inline fun <REQUEST, reified RESPONSE> post(url: String,
                                                headers: Map<String, String>? = null,
                                                params: REQUEST,
                                                crossinline success: ((RESPONSE?) -> Unit),
                                                crossinline failure: ((String) -> Unit)) {

        GenericBdl.post(url = url, headers = headers, params = params)?.getAsObject(
                RESPONSE::class.java,
                object : ParsedRequestListener<RESPONSE> {
                    override fun onResponse(response: RESPONSE?) {
                        success(response)
                    }

                    override fun onError(anError: ANError?) {
                        anError?.let {
                            System.err.println("--------- ${it.errorDetail}")
                            System.err.println("--------- ${it.response}")
                            failure.invoke(/*"${it.errorCode} ${it.errorBody}"*/SERVER_ERROR)
                        }
                    }
                }
        )
    }

    inline fun <reified RESPONSE> getObject(url: String,
                                            headers: Map<String, String>? = null,
                                            params: Map<String, String>? = mapOf(),
                                            crossinline success: ((RESPONSE?) -> Unit),
                                            crossinline failure: ((String) -> Unit)) {
        GenericBdl.get(url = url, headers = headers, params = params)?.getAsObject(
                RESPONSE::class.java,
                object : ParsedRequestListener<RESPONSE> {
                    override fun onResponse(response: RESPONSE?) {
                        success(response)
                    }

                    override fun onError(anError: ANError?) {
                        anError?.let {
                            System.err.println("--------- ${it.errorDetail}")
                            System.err.println("--------- ${it.response}")
                            failure.invoke(/*"${it.errorCode} ${it.errorBody}"*/SERVER_ERROR)
                        }
                    }
                }
        )
    }

    inline fun <REQUEST, reified RESPONSE> postAndGetList(url: String,
                                                          headers: Map<String, String>? = null,
                                                          params: REQUEST,
                                                          crossinline success: ((List<RESPONSE>?) -> Unit),
                                                          crossinline failure: ((String) -> Unit)) {

        GenericBdl.post(url = url, headers = headers, params = params)?.getAsObjectList(
                RESPONSE::class.java,
                object : ParsedRequestListener<List<RESPONSE>> {
                    override fun onResponse(responses: List<RESPONSE>?) {
                        success(responses)
                    }

                    override fun onError(anError: ANError?) {
                        anError?.let {
                            System.err.println("--------- ${it.errorDetail}")
                            System.err.println("--------- ${it.response}")
                            failure.invoke(/*"${it.errorCode} ${it.errorBody}"*/SERVER_ERROR)
                        }
                    }
                }
        )
    }

    inline fun <reified RESPONSE> getList(url: String,
                                          headers: Map<String, String>? = null,
                                          params: Map<String, String>? = mapOf(),
                                          crossinline success: ((List<RESPONSE>?) -> Unit),
                                          crossinline failure: ((String) -> Unit)) {
        GenericBdl.get(url = url, headers = headers, params = params)?.getAsObjectList(
                RESPONSE::class.java,
                object : ParsedRequestListener<List<RESPONSE>> {
                    override fun onResponse(response: List<RESPONSE>?) {
                        success(response)
                    }

                    override fun onError(anError: ANError?) {
                        anError?.let {
                            System.err.println("--------- ${it.errorDetail}")
                            System.err.println("--------- ${it.response}")
                            failure.invoke(/*"${it.errorCode} ${it.errorBody}"*/SERVER_ERROR)
                        }
                    }
                }
        )
    }

    inline fun <REQUEST, reified RESPONSE> put(url: String,
                                               headers: Map<String, String>? = null,
                                               params: REQUEST,
                                               crossinline success: ((RESPONSE?) -> Unit),
                                               crossinline failure: ((String) -> Unit)) {

        GenericBdl.put(url = url, headers = headers, params = params)?.getAsObject(
                RESPONSE::class.java,
                object : ParsedRequestListener<RESPONSE> {
                    override fun onResponse(response: RESPONSE?) {
                        success(response)
                    }

                    override fun onError(anError: ANError?) {
                        anError?.let {
                            System.err.println("--------- ${it.errorDetail}")
                            System.err.println("--------- ${it.response}")
                            failure.invoke(/*"${it.errorCode} ${it.errorBody}"*/SERVER_ERROR)
                        }
                    }
                }
        )
    }

    inline fun <reified RESPONSE> upload(url: String,
                                         headers: Map<String, String>? = null,
                                         fileParams: Map<String, File> = mapOf(),
                                         params: Map<String, Any> = mapOf(),
                                         crossinline success: ((RESPONSE?) -> Unit),
                                         crossinline failure: ((String) -> Unit)) {


        GenericBdl.upload(url = url, headers = headers, file = fileParams, params = params)?.getAsObject(
                RESPONSE::class.java,
                object : ParsedRequestListener<RESPONSE> {
                    override fun onResponse(response: RESPONSE?) {
                        success(response)
                    }

                    override fun onError(anError: ANError?) {
                        anError?.let {
                            System.err.println("--------- ${it.errorCode} : ${it.errorDetail}")
                            System.err.println("--------- ${it.response}")
                            failure.invoke(/*"${it.errorCode} ${it.errorBody}"*/SERVER_ERROR)
                        }
                    }
                }
        )
    }

    inline fun download(url: String,
                        dirPath: String,
                        fileName: String,
                        crossinline downloadComplete: ((String) -> Unit),
                        noinline before: (() -> Unit)? = null) {
        before?.invoke()
        GenericBdl.download(url = url, dirPath = dirPath, fileName = fileName)?.startDownload(
                object : DownloadListener {
                    override fun onDownloadComplete() {
                        downloadComplete(fileName)
                    }

                    override fun onError(anError: ANError?) {
                        println("Unable to download $url")
                    }
                }
        )
    }
}