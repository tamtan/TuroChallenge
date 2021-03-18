package vinh.tan.tam.com.myapplication.view

import android.content.Context
import android.util.AttributeSet
import java.lang.Math.abs

class ScalableImageView : androidx.appcompat.widget.AppCompatImageView {

    // Private Variables

    private var defaultImageHeightPx: Int = 0
    private var defaultImageWidthPx: Int = 0
    private var hasInit: Boolean = false

    private var currentScaleValue: Float = 1.0f

    // Constructors

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    // Public Functions

    fun scaleImage(scalePercent: Float) {
        initView()

        if ((defaultImageHeightPx <= 0) || (defaultImageWidthPx <= 0)) {
            return
        }

        if (abs(scalePercent - currentScaleValue) < 0.00001f) {
            return
        }

        layoutParams?.let {
            val scaledHeight: Int = (defaultImageHeightPx * scalePercent).toInt()
            it.height = scaledHeight

            val scaledWidth: Int = (defaultImageWidthPx * scalePercent).toInt()
            it.width = scaledWidth
        }

        currentScaleValue = scalePercent
    }

    // Private Functions

    private fun initView() {
        if (hasInit) {
            return
        }

        layoutParams?.let {
            defaultImageHeightPx = it.height
            defaultImageWidthPx = it.width
        }

        hasInit = true
    }
}