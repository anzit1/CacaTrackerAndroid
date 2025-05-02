package com.example.cacatrackermobileapp.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

object CompressionImage {

    fun resizeAndCompressImage(
        originalFile: File,
        targetWidth: Int,
        targetHeight: Int,
        maxSizeBytes: Long
    ): File {
        val bitmap = BitmapFactory.decodeFile(originalFile.absolutePath)
        val resizedBitmap = Bitmap.createScaledBitmap(bitmap, targetWidth, targetHeight, true)

        var quality = 100
        val tempFile = File.createTempFile("compressed_", ".jpg")
        tempFile.deleteOnExit()

        do {
            val outputStream = ByteArrayOutputStream()
            resizedBitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
            val byteArray = outputStream.toByteArray()

            FileOutputStream(tempFile).use {
                it.write(byteArray)
            }

            quality -= 5
        } while (tempFile.length() > maxSizeBytes && quality > 10)

        return tempFile
    }

    fun encodeImageToBase64(imageFile: File): String {
        val bytes = imageFile.readBytes()
        return Base64.encodeToString(bytes, Base64.NO_WRAP)
    }
}
