package com.ravi.practicaltaskmvvm.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize


@Entity(tableName = "Repository")
@Parcelize
@JsonClass(generateAdapter = true)
data class RepositoryEntity(
    @PrimaryKey(autoGenerate = true)
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "full_name")
    val fullName: String,
    @Json(name = "stargazers_count")
    val stargazersCount: Int,
    var bookmarked: Boolean = false
):Parcelable
