package dev.igordias.downloadmanager.domain.entity

import dev.igordias.downloadmanager.util.Constants.FTP_NAME
import dev.igordias.downloadmanager.util.Constants.FTP_PROTOCOL_STRING
import dev.igordias.downloadmanager.util.Constants.HTTP_NAME
import dev.igordias.downloadmanager.util.Constants.HTTP_PROTOCOL_STRING

sealed class Protocol {
    object Http : Protocol()
    object Ftp : Protocol()

    fun getName(): String {
        return when (this) {
            is Http -> HTTP_NAME
            is Ftp -> FTP_NAME
        }
    }

    fun getProtocolString():String {
        return when (this){
            is Http -> HTTP_PROTOCOL_STRING
            is Ftp -> FTP_PROTOCOL_STRING
        }
    }

    companion object {
        fun getAll(): List<Protocol> {
            return listOf(Http, Ftp)
        }

        fun getAllNames() = getAll().map { it.getName() }

        fun get(name: String): Protocol? {
            return when (name) {
                HTTP_NAME -> Http
                FTP_NAME -> Ftp
                else -> null
            }
        }
    }
}