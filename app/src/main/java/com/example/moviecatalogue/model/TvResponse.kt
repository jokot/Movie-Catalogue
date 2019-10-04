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
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name: String? = "",
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
    var voteCount: Double
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readDouble()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(backdropPath)
        parcel.writeString(firstAirDate)
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(originalLanguage)
        parcel.writeString(originalName)
        parcel.writeString(overview)
        parcel.writeDouble(popularity)
        parcel.writeString(posterPath)
        parcel.writeDouble(voteAverage)
        parcel.writeDouble(voteCount)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TvShow> {
        override fun createFromParcel(parcel: Parcel): TvShow {
            return TvShow(parcel)
        }

        override fun newArray(size: Int): Array<TvShow?> {
            return arrayOfNulls(size)
        }
    }
}
