package com.example.newappproject.RemoteDatabase.RemoteDataModel

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey


data class RemoteData(

    var id: String,
    var alt_description: Any,
    var blur_hash: String,
    var color: String,
    var created_at: String,
    var current_user_collections: List<Any>,
    var description: String,
    var downloads: Int,
    var exif: Exif,
    var height: Int,
    var liked_by_user: Boolean,
    var likes: Int,
    var links: Links,
    var location: Location,
    var meta: Meta,
    var promoted_at: String,
    var public_domain: Boolean,
    var sponsorship: Any,
    var tags: List<Tag>,
    var tags_preview: List<TagsPreview>,
    var topics: List<Any>,
    var updated_at: String,
    var urls: Urls,
    var user: User,
    var views: Int,
    var width: Int
)