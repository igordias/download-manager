package dev.igordias.downloadmanager

import androidx.lifecycle.*
import dev.igordias.downloadmanager.data.Repository
import dev.igordias.downloadmanager.domain.entity.Protocol
import dev.igordias.downloadmanager.util.arch.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel : ViewModel(), LifecycleObserver {
    private var mFileBytes: MutableLiveData<Event<ByteArray>> = MutableLiveData()
    val fileBytes: LiveData<Event<ByteArray>> get() = mFileBytes


    fun onDownloadRequested(protocolName: String, url: String) {
        CoroutineScope(Dispatchers.IO).launch {
            Protocol.get(protocolName)?.let {
                val fileBytes = Repository.executeDownload(it, url)
                mFileBytes.postValue(Event(fileBytes))
            }
        }
    }

}