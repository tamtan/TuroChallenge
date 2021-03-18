package vinh.tan.tam.com.myapplication.network

import android.util.Log
import retrofit2.Response
import vinh.tan.tam.com.myapplication.model.SearchResponse

object NetworkUtils {
    suspend fun makeSearchRequest(
        term: String,
        location: String,
        latitude: String,
        longitude: String,
        limit: Int,
        offset: Int,
        sort_by: String
    ): SearchResponse? {
        val response: Response<SearchResponse>? =
            RetrofitFactory.retrofit.create(SearchService::class.java)
                .search(term, location, latitude, longitude, limit, offset, sort_by)

        return if (response?.isSuccessful == true) {
            Log.d("tanvinhtam", response.body().toString())
            response.body()
        } else {
            Log.d("tanvinhtam", response.toString())
            null
        }
    }
}