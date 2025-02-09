package com.example.technicaltest.utils

import android.app.Activity
import android.widget.Toast

fun Activity.toast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(this, message, duration).show()