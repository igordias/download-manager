package dev.igordias.downloadmanager.data.provider

import dev.igordias.downloadmanager.domain.interactor.FileProvider
import dev.igordias.downloadmanager.util.Constants.HTTP_PROTOCOL_STRING
import dev.igordias.downloadmanager.util.Utils.checkHttpUrl
import dev.igordias.downloadmanager.util.checkExceptions
import okhttp3.OkHttpClient
import okhttp3.Request


class HttpFileProvider(private val url: String) : FileProvider {

    private val mClient = OkHttpClient()
    private val mRequest = Request.Builder().url(checkHttpUrl(url)).get().build()

    override fun execute(): ByteArray {
        return mClient.newCall(mRequest).execute().run {
            checkExceptions()
            body!!.bytes()
        }
    }



}