package com.poc.myapplication

import android.content.res.AssetManager
import android.graphics.Color
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.View


fun View.gone(){
    visibility = View.GONE
}

fun View.visible(){
    visibility = View.VISIBLE
}

fun View.invisible(){
    visibility = View.INVISIBLE
}


object Utils {


    fun loadFile(assetManager: AssetManager?, filename: String): String {

        var jsonString = ""
        try {
            jsonString = assetManager?.open(filename)
                ?.bufferedReader()
                .use { it?.readText() ?: "" }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return jsonString;
    }



    fun getSpannedString(string: String, searchString: String): Spannable {
        val sb: Spannable = SpannableString(string)
        if(string.contains(searchString, true)) {
            val lowercase = string.lowercase()
            val start = lowercase.indexOf(searchString)
            sb.setSpan(
                ForegroundColorSpan(Color.GREEN),
                start,
                start + searchString.length,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE
            ) //bold
        }
        return sb
    }
}