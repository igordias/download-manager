package dev.igordias.downloadmanager.data.provider

import dev.igordias.downloadmanager.domain.interactor.FileProvider
import dev.igordias.downloadmanager.util.Constants.FTP_DEFAULT_PASSWORD
import dev.igordias.downloadmanager.util.Constants.FTP_DEFAULT_PORT
import dev.igordias.downloadmanager.util.Constants.FTP_DEFAULT_USERNAME
import dev.igordias.downloadmanager.util.Constants.FTP_PROTOCOL_STRING
import dev.igordias.downloadmanager.util.Utils.checkFtpUrl
import org.apache.commons.net.ftp.FTPClient
import java.net.URL

class FtpFileProvider(
    private val url: String,
    private val authUsername: String = FTP_DEFAULT_USERNAME,
    private val authPassword: String = FTP_DEFAULT_PASSWORD
) : FileProvider {

    private val mUrl = URL(checkFtpUrl(url))

    private val mClient = FTPClient().apply {
        connect(mUrl.host, getPort())
        login(authUsername, authPassword)
        enterLocalPassiveMode()
    }

    override fun execute(): ByteArray {
        return mClient.retrieveFileStream(mUrl.file).readBytes()
    }

    private fun getPort(): Int = if(mUrl.port == -1) FTP_DEFAULT_PORT else mUrl.port

}