package dev.igordias.downloadmanager.data

import dev.igordias.downloadmanager.data.provider.FtpFileProvider
import dev.igordias.downloadmanager.data.provider.HttpFileProvider
import dev.igordias.downloadmanager.domain.entity.Protocol

object Repository {
    fun executeDownload(protocol: Protocol, url: String): ByteArray {
        return when (protocol) {
            Protocol.Http -> executeHttpDownload(url)
            Protocol.Ftp -> executeFtpDownload(url)
        }
    }

    private fun executeHttpDownload(url: String): ByteArray = HttpFileProvider(url).execute()
    private fun executeFtpDownload(url: String): ByteArray = FtpFileProvider(url).execute()
}