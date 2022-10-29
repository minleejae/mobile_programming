package com.example.assignment

import android.text.InputFilter
import android.widget.Toast
import java.util.regex.Pattern

object Utils {

    fun retFilterAlphaNumSpace(func: () -> Unit): InputFilter {
        val filterAlphaNumSpace = InputFilter { source, start, end, dest, dstart, dend ->
            val ps = Pattern.compile("^[a-z0-9]+$")
            if (ps.matcher(source).matches()) {
                return@InputFilter source
            }
            func()
            source.dropLast(1)
        }
        return filterAlphaNumSpace
    }
}