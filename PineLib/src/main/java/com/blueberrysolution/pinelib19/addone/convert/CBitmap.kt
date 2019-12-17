package com.blueberrysolution.pinelib19.addone.convert

import android.graphics.Bitmap
import java.io.ByteArrayOutputStream

fun Bitmap.toBytes(): ByteArray{
    val baos = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.JPEG, 100, baos)
    return baos.toByteArray()
}