package vinh.tan.tam.com.myapplication.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import retrofit2.Response
import vinh.tan.tam.com.myapplication.model.SearchResponse
import vinh.tan.tam.com.myapplication.network.Resource
import vinh.tan.tam.com.myapplication.network.SearchService

class MainViewModel(private val searchService: SearchService) : ViewModel() {
    var item: String = ""
    var location: String = "Dallas"
    var latitude: String = ""
    var longitude: String = ""
    var limit: Int = 20
    var sort_by: String = "distance"
    var offset: Int = 0
    var resoure = MutableLiveData<Resource<Response<SearchResponse>>>()
    val viewModelJob = SupervisorJob()
    var isLoadMore: Boolean = false
    var isLoading = MutableLiveData<Boolean>(false)
    fun search() {
        Log.d("tanvinhtam", "search() clicked")
    }

    fun search(
        term: String?,
        location: String?,
        latitude: String = "",
        longitude: String = "",
        limit: Int = 20,
        offset: Int = 0,
        sort_by: String = "distance",
        isLoadMore: Boolean
    ) {
        if (isLoading.value == true) {
            return
        }
        isLoading.postValue(true);
        this.isLoadMore = isLoadMore
        this.offset = if (isLoadMore) {
            this.offset + 1
        } else {
            0
        }
        Log.d(
            "tanvinhtam",
            "term " + term + " location " + location + " latitude " + latitude + " longitude " + longitude + " limit " + limit + " offset " + offset + " sortby " + sort_by
        )
        CoroutineScope(Dispatchers.IO + viewModelJob).launch {
            resoure.postValue(Resource.loading(data = null))
            try {
                resoure.postValue(
                    Resource.success(
                        data = searchService.search(
                            term ?: "",
                            location ?: "Dallas",
                            latitude,
                            longitude,
                            limit,
                            offset,
                            sort_by
                        )
                    )
                )
            } catch (exception: Exception) {
                resoure.postValue(
                    Resource.error(
                        data = null,
                        message = exception.message ?: "Error occured"
                    )
                )
            }
            isLoading.postValue(false)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}