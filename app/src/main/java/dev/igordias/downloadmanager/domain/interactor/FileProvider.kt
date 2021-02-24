package dev.igordias.downloadmanager.domain.interactor

interface FileProvider {
    fun execute(): ByteArray
}