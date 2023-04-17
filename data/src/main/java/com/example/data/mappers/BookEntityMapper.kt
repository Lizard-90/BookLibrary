package com.example.data.mappers

import com.example.data.entities.BookEntity
import com.example.domain.entities.Volume
import com.example.domain.entities.VolumeInfo

class BookEntityMapper {
    fun toBookEntity(volume: Volume): BookEntity {
        return BookEntity(
            id = volume.id,
            title = volume.volumeInfo.title,
            author = volume.volumeInfo.authors,
            imageUrl = volume.volumeInfo.imageUrl,
        )
    }

    fun toVolume(bookEntity: BookEntity): Volume {
        return Volume(
            bookEntity.id,
            VolumeInfo(bookEntity.title, bookEntity.author, bookEntity.imageUrl)
        )
    }
}