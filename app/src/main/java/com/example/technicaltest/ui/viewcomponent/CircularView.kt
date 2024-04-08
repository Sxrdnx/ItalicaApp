package com.example.technicaltest.ui.viewcomponent

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.technicaltest.R
import com.google.android.material.imageview.ShapeableImageView

class CircularView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    style: Int = 0
) : ConstraintLayout(context, attributeSet, style) {

    val circularImageView: ShapeableImageView
    private val textView: TextView

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.view_circular, this, true)
        circularImageView = view.findViewById(R.id.circularImageView)
        textView = view.findViewById(R.id.textView)
    }

    fun setErrorView(
        initialsText: String,
        idTextColor: Int,
        textBackground: Drawable,
    ) {
        if (initialsText.isEmpty())
            return
        val arrayStrings = initialsText.uppercase().split(" ")
        val firstInitial = arrayStrings.firstOrNull()?.first()
        if (arrayStrings.isNotEmpty() && firstInitial != null && !firstInitial.equals("!")) {
            textView.setTextColor(idTextColor)
            textView.visibility = View.VISIBLE
            circularImageView.background = textBackground
            if (arrayStrings.count() < 2)
                textView.text = firstInitial.toString()
            else {
                val secondInitial = arrayStrings[1].first()
                textView.text = "$firstInitial$secondInitial"
            }
        }
    }


}

