package vinh.tan.tam.com.myapplication.view

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import vinh.tan.tam.com.myapplication.R
import java.text.DecimalFormat


object ViewUtils {
    fun setProductRating(
        totalRatings: String,
        averageRating: String,
        view: View
    ) {
        var isProductRatingAvail: Boolean
        val ratingsLayout: LinearLayout = view.findViewById(R.id.review_detail_list_item_ratingLH)
        val star1Image: ImageView = view.findViewById(R.id.pip_review_detail_rating_star_1_img)
        val star2Image: ImageView = view.findViewById(R.id.pip_review_detail_rating_star_2_img)
        val star3Image: ImageView = view.findViewById(R.id.pip_review_detail_rating_star_3_img)
        val star4Image: ImageView = view.findViewById(R.id.pip_review_detail_rating_star_4_img)
        val star5Image: ImageView = view.findViewById(R.id.pip_review_detail_rating_star_5_img)
        val reviewsCount: TextView = view.findViewById(R.id.review_count_TV)
        val noReviewsImage: ImageView = view.findViewById(R.id.plp_no_review)
        if (averageRating.isEmpty() || averageRating.equals(
                "0",
                ignoreCase = true
            ) || averageRating.equals("0.0", ignoreCase = true)
        ) {
            isProductRatingAvail = false
        } else {
            try {
                // Replace all the non digit terms
                val r =
                    averageRating.trim { it <= ' ' }.replace("\\D".toRegex(), "")
                isProductRatingAvail = r.toInt() > 0
            } catch (e: Exception) {
                isProductRatingAvail = false
            }
        }
        if (isProductRatingAvail) {
            ratingsLayout.visibility = View.VISIBLE
            reviewsCount.visibility = View.VISIBLE
            if (!averageRating.isEmpty()) {
                // Hide No Rating image
                noReviewsImage.setVisibility(View.GONE)
                // Show Rating layout
                reviewsCount.visibility = View.VISIBLE
                // Set Rating count
                if (!totalRatings.isEmpty()) {
                    val formatter = DecimalFormat("#,###,###")
                    val reviewCount: String = view.getResources().getString(
                        R.string.pip_rating_review_count,
                        formatter.format(totalRatings.toInt())
                    )
                    reviewsCount.text = reviewCount
                } else {
                    reviewsCount.text = ""
                }

                // get the star rating for the product
                val rating = averageRating.toDouble()
                if (rating == 0.0) {
                    // set the star images to be gone
                    star1Image.setVisibility(View.GONE)
                    star2Image.setVisibility(View.GONE)
                    star3Image.setVisibility(View.GONE)
                    star4Image.setVisibility(View.GONE)
                    star5Image.setVisibility(View.GONE)

                    // otherwise get the images for the appropriate start rating for the product
                } else {
                    if (rating >= 1.0) {
                        reviewsCount.visibility = View.VISIBLE
                        // make the star images visible and preset the image resources
                        star1Image.setVisibility(View.VISIBLE)
                        star2Image.setVisibility(View.VISIBLE)
                        star3Image.setVisibility(View.VISIBLE)
                        star4Image.setVisibility(View.VISIBLE)
                        star5Image.setVisibility(View.VISIBLE)
                        star1Image.setImageResource(R.drawable.ico_stars_whole_org)
                        star2Image.setImageResource(R.drawable.ico_stars_gray)
                        star3Image.setImageResource(R.drawable.ico_stars_gray)
                        star4Image.setImageResource(R.drawable.ico_stars_gray)
                        star5Image.setImageResource(R.drawable.ico_stars_gray)
                    }
                    if (rating >= 1.25) {
                        star2Image.setImageResource(R.drawable.ico_stars_half)
                    }
                    if (rating >= 1.75) {
                        star2Image.setImageResource(R.drawable.ico_stars_whole_org)
                    }
                    if (rating >= 2.25) {
                        star3Image.setImageResource(R.drawable.ico_stars_half)
                    }
                    if (rating >= 2.75) {
                        star3Image.setImageResource(R.drawable.ico_stars_whole_org)
                    }
                    if (rating >= 3.25) {
                        star4Image.setImageResource(R.drawable.ico_stars_half)
                    }
                    if (rating >= 3.75) {
                        star4Image.setImageResource(R.drawable.ico_stars_whole_org)
                    }
                    if (rating >= 4.25) {
                        star5Image.setImageResource(R.drawable.ico_stars_half)
                    }
                    if (rating >= 4.75) {
                        star5Image.setImageResource(R.drawable.ico_stars_whole_org)
                    }
                }
            }
        } else {
            // Show No Rating image
            noReviewsImage.setVisibility(View.GONE)
            // Hide Rating details
            reviewsCount.visibility = View.GONE
            ratingsLayout.visibility = View.GONE
            star1Image.setImageResource(R.drawable.ico_stars_gray)
            star2Image.setImageResource(R.drawable.ico_stars_gray)
            star3Image.setImageResource(R.drawable.ico_stars_gray)
            star4Image.setImageResource(R.drawable.ico_stars_gray)
            star5Image.setImageResource(R.drawable.ico_stars_gray)
        }

        // Needs to resize the star image views to accommodate for the space when there are 4 digits in total ratings
        val scaleStarDown =
            totalRatings.isNotEmpty() && totalRatings.length > 5
        val scaleValue = if (scaleStarDown) 0.64f else 1.0f
        if (star1Image is ScalableImageView) {
            val scalableStar = star1Image as ScalableImageView
            scalableStar.scaleImage(scaleValue)
        }
        if (star2Image is ScalableImageView) {
            val scalableStar = star2Image as ScalableImageView
            scalableStar.scaleImage(scaleValue)
        }
        if (star3Image is ScalableImageView) {
            val scalableStar = star3Image as ScalableImageView
            scalableStar.scaleImage(scaleValue)
        }
        if (star4Image is ScalableImageView) {
            val scalableStar = star4Image as ScalableImageView
            scalableStar.scaleImage(scaleValue)
        }
        if (star5Image is ScalableImageView) {
            val scalableStar = star5Image as ScalableImageView
            scalableStar.scaleImage(scaleValue)
        }
    }
}