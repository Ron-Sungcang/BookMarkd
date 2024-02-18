package com.example.bookmarkd.model

import org.intellij.lang.annotations.Language
import java.time.temporal.TemporalAmount
import java.util.concurrent.SubmissionPublisher


data class Book(
    val id: String,
    val volumeInfo: VolumeInfo,
    val saleInfo: SaleInfo
){
    companion object{
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
                data class ImageLinks(
                    val smallThumbnail: String,
                    val thumbnail: String
                )
            }
        }
    }
    data class SaleInfo (
        val country: String,
        val saleability: String,
        val isEbook: Boolean,
        val listPrice: ListPrice?,
        val buyLink: String?
    ){
        companion object{
            data class ListPrice(
                val amount: Float?,
                val currencyCode: String
            )
        }
    }
}
