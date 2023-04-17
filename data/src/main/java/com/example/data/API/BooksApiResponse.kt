package com.example.data.API

import com.squareup.moshi.Json

class BooksApiResponse (val items: List<Item>)



data class Item (@field: Json(name = "id")val id: String, @field: Json(name = "volumeInfo") val volumeInfo: ApiVolumeInfo )
data class ApiVolumeInfo(val title: String, val authors: List<String>, val imagesLinks: ImageLinks?)
data class ImageLinks(val smallThumbnail: String, val thumbnail: String)