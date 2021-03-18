package vinh.tan.tam.com.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import vinh.tan.tam.com.myapplication.network.SearchService

class ViewModelFactory(private val searchService: SearchService) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(searchService) as T
        }

        throw IllegalArgumentException("Unknown class name")
    }
}