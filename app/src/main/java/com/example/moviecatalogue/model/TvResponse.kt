package com.example.moviecatalogue.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


data class TvResponse(
    @SerializedName("page")
    var page: Int,
    @SerializedName("results")
    var tvShow: MutableList<TvShow>,
    @SerializedName("total_pages")
    var totalPages: Int,
    @SerializedName("total_results")
    var totalResults: Int
)


data class TvShow(
    @SerializedName("backdrop_path")
    var backdropPath: String? = "",
    @SerializedName("first_air_date")
    var firstAirDate: String? = "",
    @SerializedName("genre_ids")
    var genreIds: List<Int>,
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name: String? = "",
    @SerializedName("origin_country")
    var originCountry: List<Any>,
    @SerializedName("original_language")
    var originalLanguage: String? = "",
    @SerializedName("original_name")
    var originalName: String? = "",
    @SerializedName("overview")
    var overview: String? = "",
    @SerializedName("popularity")
    var popularity: Double,
    @SerializedName("poster_path")
    var posterPath: String? = "",
    @SerializedName("vote_average")
    var voteAverage: Double,
    @SerializedName("vote_count")
    var voteCount: Int
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString(),
        source.readString(),
        ArrayList<Int>().apply { source.readList(this, Int::class.java.classLoader) },
        source.readInt(),
        source.readString(),
        ArrayList<Any>().apply { source.readList(this, Any::class.java.classLoader) },
        source.readString(),
        source.readString(),
        source.readString(),
        source.readDouble(),
        source.readString(),
        source.readDouble(),
        source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(backdropPath)
        writeString(firstAirDate)
        writeList(genreIds)
        writeInt(id)
        writeString(name)
        writeList(originCountry)
        writeString(originalLanguage)
        writeString(originalName)
        writeString(overview)
        writeDouble(popularity)
        writeString(posterPath)
        writeDouble(voteAverage)
        writeInt(voteCount)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<TvShow> = object : Parcelable.Creator<TvShow> {
            override fun createFromParcel(source: Parcel): TvShow =
                TvShow(source)
            override fun newArray(size: Int): Array<TvShow?> = arrayOfNulls(size)
        }
    }
}