package vinh.tan.tam.com.myapplication.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import vinh.tan.tam.com.myapplication.model.SearchResponse

interface SearchService {
    @GET("v3/businesses/search?")
    @Headers("Authorization: Bearer 2ROaa2Rh9qu3WVTCms8FoVE4mSfHQHC7QJua95-kKT-PqzIlLSrs4tmHVdtdFw_66-JNfRiJmbCByHTvFNy5dQq-tpfS4FrPpupIzKlgELR3br-r5trpeFhrCRgwWnYx")
    suspend fun search(
        @Query("term") term: String,
        @Query("location") location: String,
        @Query("latitude") latitude: String,
        @Query("longitude") longitude: String,
        @Query("limit") limit: Int,
        @Query("offset") off_set: Int,
        @Query("sort_by") sort_by: String
    ): Response<SearchResponse>
}