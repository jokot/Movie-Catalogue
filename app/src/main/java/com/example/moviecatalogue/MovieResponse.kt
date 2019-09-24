package com.example.moviecatalogue

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


data class MovieResponse(
    @SerializedName("page")
    var page: Int,
    @SerializedName("results")
    var movie: MutableList<Movie>,
    @SerializedName("total_pages")
    var totalPages: Int,
    @SerializedName("total_results")
    var totalResults: Int
)


data class Movie(
    @SerializedName("backdrop_path")
    var backdropPath: String? = "",
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("overview")
    var overview: String? = "",
    @SerializedName("poster_path")
    var posterPath: String? = "",
    @SerializedName("release_date")
    var releaseDate: String? = "",
    @SerializedName("title")
    var title: String? = "",
    @SerializedName("vote_average")
    var voteAverage: Float
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString(),
        source.readInt(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readFloat()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(backdropPath)
        writeInt(id)
        writeString(overview)
        writeString(posterPath)
        writeString(releaseDate)
        writeString(title)
        writeFloat(voteAverage)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Movie> = object : Parcelable.Creator<Movie> {
            override fun createFromParcel(source: Parcel): Movie = Movie(source)
            override fun newArray(size: Int): Array<Movie?> = arrayOfNulls(size)
        }
    }
}