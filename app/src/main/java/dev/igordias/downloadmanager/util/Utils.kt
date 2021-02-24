package dev.igordias.downloadmanager.util

object Utils {
    fun checkHttpUrl(url: String): String {
        val hasProtocolDesc = url.toLowerCase().matches("^\\w+://.*".toRegex())
        return if (!hasProtocolDesc) "${Constants.HTTP_PROTOCOL_STRING}$url" else url
    }

    fun checkFtpUrl(url: String): String {
        val hasProtocolDesc = url.toLowerCase().matches("^\\w+://.*".toRegex())
        return if (!hasProtocolDesc) "${Constants.FTP_PROTOCOL_STRING}$url" else url
    }
}