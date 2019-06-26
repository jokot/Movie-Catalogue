package com.example.moviecatalogue

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


data class MovieResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val movie: List<Movie>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readInt(),
        source.createTypedArrayList(Movie.CREATOR),
        source.readInt(),
        source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(page)
        writeTypedList(movie)
        writeInt(totalPages)
        writeInt(totalResults)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<MovieResponse> = object : Parcelable.Creator<MovieResponse> {
            override fun createFromParcel(source: Parcel): MovieResponse = MovieResponse(source)
            override fun newArray(size: Int): Array<MovieResponse?> = arrayOfNulls(size)
        }
    }
}


data class Movie(
    @SerializedName("adult")
    val adult: Boolean,
    @SerializedName("backdrop_path")
    val backdropPath: String,
    @SerializedName("genre_ids")
    val genreIds: List<Int>,
    @SerializedName("id")
    val id: Int,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_title")
    val originalTitle: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("popularity")
    val popularity: Double,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("video")
    val video: Boolean,
    @SerializedName("vote_average")
    val voteAverage: Float,
    @SerializedName("vote_count")
    val voteCount: Int
) : Parcelable {
    constructor(source: Parcel) : this(
        1 == source.readInt(),
        source.readString(),
        ArrayList<Int>().apply { source.readList(this, Int::class.java.classLoader) },
        source.readInt(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readDouble(),
        source.readString(),
        source.readString(),
        source.readString(),
        1 == source.readInt(),
        source.readFloat(),
        source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt((if (adult) 1 else 0))
        writeString(backdropPath)
        writeList(genreIds)
        writeInt(id)
        writeString(originalLanguage)
        writeString(originalTitle)
        writeString(overview)
        writeDouble(popularity)
        writeString(posterPath)
        writeString(releaseDate)
        writeString(title)
        writeInt((if (video) 1 else 0))
        writeFloat(voteAverage)
        writeInt(voteCount)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Movie> = object : Parcelable.Creator<Movie> {
            override fun createFromParcel(source: Parcel): Movie = Movie(source)
            override fun newArray(size: Int): Array<Movie?> = arrayOfNulls(size)
        }
    }
}
