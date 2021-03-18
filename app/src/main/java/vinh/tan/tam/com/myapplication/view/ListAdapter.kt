package vinh.tan.tam.com.myapplication.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import vinh.tan.tam.com.myapplication.R
import vinh.tan.tam.com.myapplication.model.Business

class ListAdapter(val list: List<Business>) :
    RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(list[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(business: Business) {

            val name = itemView.findViewById<TextView>(R.id.tv_name)
            val address = itemView.findViewById<TextView>(R.id.tv_address)
            val phone = itemView.findViewById<TextView>(R.id.tv_phone)
            val image = itemView.findViewById<ImageView>(R.id.image);
            val rating = itemView.findViewById<View>(R.id.productRatingLayout)

            name.text = business.name
            address.text = "${business.location?.address1}, ${business.location?.city}"
            phone.text = business.display_phone ?: ""
            Glide.with(itemView.context).load(business.image_url).into(image);
            ViewUtils.setProductRating( business.review_count.toString(),business.rating.toString(), rating)
        }

    }
}