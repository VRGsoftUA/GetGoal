package com.vrgsoft.mygoal.presentation.common.view

import android.content.Context
import android.content.res.AssetManager
import android.graphics.Typeface
import android.text.TextUtils

import java.io.File
import java.util.Arrays
import java.util.HashMap

object CheckableUtils {
    private val cachedFontMap = HashMap<String, Typeface>()

    fun pxToSp(context: Context, px: Float): Int {
        return Math.round(px / context.resources.displayMetrics.scaledDensity)
    }

    fun spToPx(context: Context, sp: Float): Int {
        return Math.round(sp * context.resources.displayMetrics.scaledDensity)
    }

    fun findFont(context: Context, fontPath: String?, defaultFontPath: String?): Typeface? {

        if (fontPath == null) {
            return Typeface.DEFAULT
        }

        val fontName = File(fontPath).name
        var defaultFontName = ""
        if (!TextUtils.isEmpty(defaultFontPath)) {
            defaultFontName = File(defaultFontPath).name
        }

        if (cachedFontMap.containsKey(fontName)) {
            return cachedFontMap[fontName]
        } else {
            try {
                val assets = context.resources.assets

                if (Arrays.asList(*assets.list("")).contains(fontPath)) {
                    val typeface = Typeface.createFromAsset(context.assets, fontName)
                    cachedFontMap.put(fontName, typeface)
                    return typeface
                } else if (Arrays.asList(*assets.list("fonts")).contains(fontName)) {
                    val typeface = Typeface.createFromAsset(context.assets, String.format("fonts/%s", fontName))
                    cachedFontMap.put(fontName, typeface)
                    return typeface
                } else if (Arrays.asList(*assets.list("iconfonts")).contains(fontName)) {
                    val typeface = Typeface.createFromAsset(context.assets, String.format("iconfonts/%s", fontName))
                    cachedFontMap.put(fontName, typeface)
                    return typeface
                } else if (!TextUtils.isEmpty(defaultFontPath) && Arrays.asList(*assets.list("")).contains(defaultFontPath)) {
                    val typeface = Typeface.createFromAsset(context.assets, defaultFontPath)
                    cachedFontMap.put(defaultFontName, typeface)
                    return typeface
                } else {
                    throw Exception("Font not Found")
                }

            } catch (e: Exception) {
                return Typeface.DEFAULT
            }

        }

    }
}
