//package com.blueberrysolution.pinelib19.net.picasso
//
//import com.squareup.picasso.NetworkPolicy
//import androidx.annotation.NonNull
//import android.os.Build.VERSION_CODES.JELLY_BEAN_MR2
//import android.os.StatFs
//import android.annotation.TargetApi
//import android.content.Context
//import android.net.Uri
//import android.os.Build.VERSION.SDK_INT
//import androidx.annotation.VisibleForTesting
//import com.squareup.picasso.Downloader
//import org.jetbrains.exposed.sql.exists
//import okhttp3.*
//import java.io.File
//import java.io.IOException
//
//
////0. 缓存类
//class OkHttp3Downloader : Downloader {
//
//
//
//    private val client: Call.Factory
//    private val cache: Cache?
//    private var sharedClient = true
//
//    /**
//     * Create new downloader that uses OkHttp. This will install an image cache into your application
//     * cache directory.
//     */
//    constructor(context: Context) : this(createDefaultCacheDir(context)) {}
//
//    /**
//     * Create new downloader that uses OkHttp. This will install an image cache into your application
//     * cache directory.
//     *
//     * @param maxSize The size limit for the cache.
//     */
//    constructor(context: Context, maxSize: Long) : this(createDefaultCacheDir(context), maxSize) {}
//
//    /**
//     * Create new downloader that uses OkHttp. This will install an image cache into the specified
//     * directory.
//     *
//     * @param cacheDir The directory in which the cache should be stored
//     * @param maxSize  The size limit for the cache.
//     */
//    @JvmOverloads
//    constructor(cacheDir: File, maxSize: Long = calculateDiskCacheSize(cacheDir)) : this(
//        OkHttpClient.Builder().cache(Cache(cacheDir, maxSize)).build()
//    ) {
//        sharedClient = false
//    }
//
//    /**
//     * Create a new downloader that uses the specified OkHttp instance. A response cache will not be
//     * automatically configured.
//     */
//    constructor(client: OkHttpClient) {
//        this.client = client
//        this.cache = client.cache
//    }
//
//    /**
//     * Create a new downloader that uses the specified [Call.Factory] instance.
//     */
//    constructor(client: Call.Factory) {
//        this.client = client
//        this.cache = null
//    }
//
//    @VisibleForTesting
//    internal fun getCache(): Cache? {
//        return (client as OkHttpClient).cache
//    }
//
//
//
//    @Throws(IOException::class)
//    fun load(uri: Uri, networkPolicy: Int): Response {
//        var cacheControl: CacheControl? = null
//        if (networkPolicy != 0) {
//            if (NetworkPolicy.isOfflineOnly(networkPolicy)) {
//                cacheControl = CacheControl.FORCE_CACHE
//            } else {
//                val builder = CacheControl.Builder()
//                if (!NetworkPolicy.shouldReadFromDiskCache(networkPolicy)) {
//                    builder.noCache()
//                }
//                if (!NetworkPolicy.shouldWriteToDiskCache(networkPolicy)) {
//                    builder.noStore()
//                }
//                cacheControl = builder.build()
//            }
//        }
//
//        val builder = okhttp3.Request.Builder().url(uri.toString())
//        if (cacheControl != null) {
//            builder.cacheControl(cacheControl)
//        }
//
//        val response = client.newCall(builder.build()).execute()
//        val responseCode = response.code
//        if (responseCode >= 300) {
//            response.body!!.close()
//
//        }
//
//        val fromCache = response.cacheResponse != null
//
//        val responseBody = response.body
//        return Response(responseBody!!.byteStream(), fromCache, responseBody!!.contentLength())
//    }
//
//    override fun shutdown() {
//        if (!sharedClient) {
//            if (cache != null) {
//                try {
//                    cache!!.close()
//                } catch (ignored: IOException) {
//                }
//
//            }
//        }
//    }
//
//    companion object {
//        private val PICASSO_CACHE = "picasso-cache"
//        private val MIN_DISK_CACHE_SIZE = 5 * 1024 * 1024 // 5MB
//        private val MAX_DISK_CACHE_SIZE = 50 * 1024 * 1024 // 50MB
//
//        private fun createDefaultCacheDir(context: Context): File {
//            val cache = File(context.getApplicationContext().getCacheDir(), PICASSO_CACHE)
//            if (!cache.exists()) {
//
//                cache.mkdirs()
//            }
//            return cache
//        }
//
//        @TargetApi(JELLY_BEAN_MR2)
//        private fun calculateDiskCacheSize(dir: File): Long {
//            var size = MIN_DISK_CACHE_SIZE.toLong()
//
//            try {
//                val statFs = StatFs(dir.getAbsolutePath())
//
//                val blockCount =
//                    if (SDK_INT < JELLY_BEAN_MR2) statFs.blockCount.toLong() else statFs.blockCountLong
//
//                val blockSize =
//                    if (SDK_INT < JELLY_BEAN_MR2) statFs.blockSize.toLong() else statFs.blockSizeLong
//                val available = blockCount * blockSize
//                // Target 2% of the total space.
//                size = available / 50
//            } catch (ignored: IllegalArgumentException) {
//            }
//
//            // Bound inside min/max size for disk cache.
//            return Math.max(
//                Math.min(size, MAX_DISK_CACHE_SIZE.toLong()),
//                MIN_DISK_CACHE_SIZE.toLong()
//            )
//        }
//    }
//}
///**
// * Create new downloader that uses OkHttp. This will install an image cache into the specified
// * directory.
// *
// * @param cacheDir The directory in which the cache should be stored
// */