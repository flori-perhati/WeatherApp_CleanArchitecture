package com.example.weatherapp.utility.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.weatherapp.R
import com.example.weatherapp.databinding.WarningViewBinding

class WarningView(context: Context, attributeSet: AttributeSet) :
    ConstraintLayout(context, attributeSet) {

    var warningText: String? = ""
        set(value) {
            field = value
            viewBinding.tvMessage.text = value ?: ""
        }

    @DrawableRes
    var imgSrc: Int = R.drawable.ic_alert_outline
        set(value) {
            field = value
            if (value != 0)
                viewBinding.img.setImageDrawable(AppCompatResources.getDrawable(context, value))
        }

    private val viewBinding: WarningViewBinding =
        WarningViewBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        context.theme.obtainStyledAttributes(
            attributeSet,
            R.styleable.WarningView,
            0, 0
        ).apply {
            try {
                imgSrc = getResourceId(R.styleable.WarningView_imgSrc, imgSrc)
                warningText = getString(R.styleable.WarningView_warningText)
            } finally {
                recycle()
            }
        }
    }
}