package com.example.newappproject.RemoteDatabase.RemoteDataModel

data class Exif(
    var aperture: String,
    var exposure_time: String,
    var focal_length: String,
    var iso: Int,
    var make: String,
    var model: String,
    var name: String
)