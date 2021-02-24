package dev.igordias.downloadmanager.util

import okhttp3.Response
import java.io.IOException

fun Response.checkExceptions() {
    if (!this.isSuccessful) throw IOException("Download failed: $this")
    if (this.body == null) throw IOException("Download failed: $this")
}