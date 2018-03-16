package com.smile.tolotra.kotlinstarter.services.business

import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.ANRequest
import com.androidnetworking.interfaces.DownloadProgressListener
import com.google.gson.Gson
import java.io.File

object GenericBdl {

    fun <K> post(url: String,
                 headers: Map<String, String>? = null,
                 params: K? = null): ANRequest<out ANRequest<*>>? {

        println("------------- post $url")
        println("------------- params : ${Gson().toJson(params)}")

        return AndroidNetworking.post(url)
                .addHeaders(headers)
                .addApplicationJsonBody(params)
                .setContentType("application/json; charset=utf-8")
                .build()
    }

    fun <K> put(url: String,
                headers: Map<String, String>? = null,
                params: K? = null): ANRequest<out ANRequest<*>>? {

        println("------------- put $url")
        println("------------- params : ${Gson().toJson(params)}")

        return AndroidNetworking.put(url)
                .addHeaders(headers)
                .addApplicationJsonBody(params)
                .setContentType("application/json; charset=utf-8")
                .build()
    }

    fun get(url: String,
            headers: Map<String, String>? = null,
            params: Map<String, String>? = mapOf()): ANRequest<out ANRequest<*>>? {

        println("------------- get $url")
        println("------------- params : ${Gson().toJson(params)}")

        return AndroidNetworking.get(url)
                .addHeaders(headers)
                .addPathParameter(params)
                .build()
    }

    fun upload(url: String,
               headers: Map<String, String>? = null,
               file: Map<String, File> = mapOf(),
               params: Map<String, Any> = mapOf()): ANRequest<out ANRequest<*>>? {

        println("------------- upload $url")
        println("------------- params : ${Gson().toJson(params)}")
        println("------------- file : ${Gson().toJson(file)}")

        return AndroidNetworking.upload(url)
                .addHeaders(headers)
                .addMultipartFile(file)
                .addMultipartParameter(params).build()
    }

    fun download(url: String,
                 dirPath: String,
                 fileName: String,
                 downloadProgressListener: DownloadProgressListener? = null): ANRequest<out ANRequest<*>>? {

        val request = AndroidNetworking.download(url, dirPath, fileName).build()
        downloadProgressListener?.let {
            request.setDownloadProgressListener(it)
        }
        return request
    }
}