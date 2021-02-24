package dev.igordias.downloadmanager

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.igordias.downloadmanager.databinding.ActivityMainBinding
import dev.igordias.downloadmanager.domain.entity.Protocol
import dev.igordias.downloadmanager.util.observeEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URL

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var protocolListAdapter: ArrayAdapter<String>
    private lateinit var viewModel: MainActivityViewModel

    private val scopeMainThread = CoroutineScope(Dispatchers.Main)
    private val scopeIO = CoroutineScope(Dispatchers.IO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        initUi()
        initHandlers()
    }

    private fun initUi() {
        initProtocolList()
        binding.btnDownload.setOnClickListener { onDownloadRequested() }
    }

    private fun initHandlers() {
        viewModel.fileBytes.observeEvent(this, ::onNewFileReceived)
    }

    private fun initProtocolList() {
        protocolListAdapter = ArrayAdapter(
            this,
            R.layout.support_simple_spinner_dropdown_item,
            Protocol.getAllNames()
        )
        with(binding.protocolsList) {
            setAdapter(protocolListAdapter)
            threshold = 1
        }
    }

    private fun isFormValid(): Boolean {
        with(binding) {
            if (edtUrl.text.toString() == "") return false
            if (Protocol.get(protocolsList.text.toString()) == null) return false
        }
        return true
    }

    private fun onDownloadRequested() {
        if (isFormValid()) {
            with(binding) {
                viewModel.onDownloadRequested(protocolsList.text.toString(), edtUrl.text.toString())
            }
        } else showToast(resources.getString(R.string.message_error_form_fields))
    }

    private fun onNewFileReceived(byteArray: ByteArray?) {
        scopeIO.launch {
            byteArray?.let {
                val resultFileName = getFileNameFromUrl(binding.edtUrl.text.toString())
                applicationContext.openFileOutput(resultFileName, Context.MODE_PRIVATE)
                    .write(byteArray)
            }
            scopeMainThread.launch { showSuccessMessage() }
        }.invokeOnCompletion { throwable -> scopeMainThread.launch { onError(throwable) } }
    }

    private fun getFileNameFromUrl(url: String): String {
        return URL(checkProtocol(url)).file.replace("/", "")
    }

    fun checkProtocol(url: String): String {
        val protocolString =
            Protocol.get(binding.protocolsList.text.toString())?.getProtocolString()
        val hasProtocolDesc = url.toLowerCase().matches("^\\w+://.*".toRegex())
        return if (!hasProtocolDesc) "$protocolString$url" else url
    }

    private fun showSuccessMessage() {
        showToast(resources.getString(R.string.message_success))
    }

    private fun onError(throwable: Throwable?) {
        throwable?.let {
            showToast(resources.getString(R.string.message_error))
        }
    }

    private fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }


}