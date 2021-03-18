package vinh.tan.tam.com.myapplication.model

class Business {
    var rating: Double = 0.0
    var price: String? = null
    var phone: String? = null
    var id: String? = null
    var alias: String? = null
    var is_closed = false
    var categories: List<Category>? = null
    var review_count = 0
    var name: String? = null
    var url: String? = null
    var coordinates: Coordinates? = null
    var image_url: String? = null
    var location: Location? = null
    var distance = 0.0
    var display_phone: String? = null
    var transactions: List<String>? = null
}