package com.example.warehouse.service

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import coil.ImageLoader
import coil.decode.DataSource
import coil.decode.ImageSource
import coil.fetch.Fetcher
import coil.fetch.FetchResult
import coil.fetch.SourceResult
import coil.request.Options
import okio.buffer
import okio.source

class ContentUriFetcher(private val context: Context, private val uri: Uri) : Fetcher {
    override suspend fun fetch(): FetchResult {
        val contentResolver = context.contentResolver
        val inputStream = contentResolver.openInputStream(uri)
            ?: throw IllegalStateException("Unable to open InputStream for URI: $uri")

        val imageSource = ImageSource(inputStream.source().buffer(), context)

        return SourceResult(
            source = imageSource,
            mimeType = contentResolver.getType(uri), // Определяем MIME-тип
            dataSource = DataSource.DISK // Указываем источник данных
        )
    }

    class Factory(private val context: Context) : Fetcher.Factory<Uri> {
        override fun create(data: Uri, options: Options, imageLoader: ImageLoader): Fetcher? {
            return if (data.scheme == ContentResolver.SCHEME_CONTENT) {
                ContentUriFetcher(context, data)
            } else {
                null
            }
        }
    }
}
