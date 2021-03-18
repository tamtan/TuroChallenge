package vinh.tan.tam.com.myapplication.model

import java.io.Serializable

class SearchResponse : Serializable {
    var total = 0
    var businesses: List<Business>? = null
    var region: Region? = null
}