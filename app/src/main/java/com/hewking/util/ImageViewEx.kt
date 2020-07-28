package com.hewking.util

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.TransitionDrawable
import android.os.Build
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.request.RequestOptions
import com.hewking.custom.R

/**
 * 占位符矩形
 */
fun ImageView.load(url: String) {
    get(url).apply(RequestOptions()
            .centerCrop()
            .dontAnimate()
            .error(R.drawable.asm_logo))
            .into(this)
}

fun ImageView.load(url: String, default: Int = R.drawable.asm_logo
                   , error: Int = R.drawable.asm_logo) {
    get(url)
            .apply(RequestOptions()
                    .centerCrop()
                    .dontAnimate()
                    .placeholder(R.drawable.asm_logo)
                    .error(R.drawable.asm_logo))
            .into(this)
}


fun ImageView.copyDrawable(): Drawable? {
    return copyDrawable(this.drawable)
}

fun ImageView.copyDrawable(iamgeDrable: Drawable?): Drawable? {
    var mydrawable = iamgeDrable
    var result: Drawable? = null
    if (mydrawable != null) {
        if (mydrawable is TransitionDrawable) {
            val transitionDrawable = mydrawable as TransitionDrawable?
            mydrawable = transitionDrawable!!.getDrawable(transitionDrawable.numberOfLayers - 1)
        }

        if (mydrawable == null) {
            return null
        }
        val bounds = mydrawable.bounds
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            val width = bounds.width()
            val height = bounds.height()

            if (width == 0 || height == 0) {
                return mydrawable
            }

            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            mydrawable.draw(canvas)
            result = BitmapDrawable(context.getResources(), bitmap)
            result.bounds = bounds
        } else {
            val constantState = mydrawable.mutate().constantState
            if (constantState != null) {
                result = constantState.newDrawable()
                result!!.bounds = bounds
            }
        }
    }
    return result
}

fun ImageView.get(url: String): RequestBuilder<Drawable> = Glide.with(context.applicationContext).load(url)
fun ImageView.get(url: Drawable): RequestBuilder<Drawable> = Glide.with(context.applicationContext).load(url)
