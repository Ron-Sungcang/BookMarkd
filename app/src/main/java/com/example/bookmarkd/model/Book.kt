package com.example.bookmarkd.model

import kotlinx.serialization.Serializable
import org.intellij.lang.annotations.Language
import java.time.temporal.TemporalAmount
import java.util.concurrent.SubmissionPublisher

@Serializable
data class Book(
    val id: String,
    val volumeInfo: VolumeInfo,
    val saleInfo: SaleInfo
){
    companion object{
        @Serializable
        data class VolumeInfo(
            val title: String,
            val authors: List<String>?,
            val publisher: String,
            val publishedDate: String?,
            val description: String?,
            val language: String?,
            val imageLinks: ImageLinks?,
            val previewLink: String?,
            val canonicalVolumeLink: String?
        ){
            companion object{
                @Serializable
                data class ImageLinks(
                    val smallThumbnail: String,
                    val thumbnail: String
                )
            }
        }
    }
    @Serializable
    data class SaleInfo (
        val country: String,
        val saleability: String,
        val isEbook: Boolean,
        val listPrice: ListPrice?,
        val buyLink: String?
    ){
        companion object{
            @Serializable
            data class ListPrice(
                val amount: Float?,
                val currencyCode: String
            )
        }
    }
}
