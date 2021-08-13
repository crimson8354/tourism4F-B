package com.example.practice

import com.google.gson.annotations.SerializedName
import java.net.URL

data class Welcome (
    @SerializedName("XML_Head")
    var xmlHead: XMLHead
)

data class XMLHead (
    @SerializedName("Listname")
    var listname: String = "",

    @SerializedName("Language")
    var language: String = "",

    @SerializedName("Orgname")
    var orgname: String = "",

    @SerializedName("Updatetime")
    var updatetime: String = "",

    @SerializedName("Infos")
    var infos: Infos
)

data class Infos (
    @SerializedName("Info")
    var info: List<Restaurant> = emptyList()
)

data class Restaurant (
    @SerializedName("Id")
    var id: String = "",
    @SerializedName("Name")
    var name: String = "",
    @SerializedName("Description")
    var desc: String = "",
    @SerializedName("Add")
    var address: String = "",
    @SerializedName("Zipcode")
    var zipCode: String = "",
    @SerializedName("Region")
    var region: String = "",
    @SerializedName("Town")
    var town: String = "",
    @SerializedName("Tel")
    var telephone: String = "",
    @SerializedName("Opentime")
    var openTime: String = "",
    @SerializedName("Website")
    var website: String = "",
    @SerializedName("Picture1")
    var picture1: String = "",
    @SerializedName("Picdescribe1")
    var picDesc1: String = "",
    @SerializedName("Picture2")
    var picture2: String = "",
    @SerializedName("Picdescribe2")
    var picDesc2: String = "",
    @SerializedName("Picture3")
    var picture3: String = "",
    @SerializedName("Picdescribe3")
    var picDesc3: String = "",
    @SerializedName("Px")
    var longitude: Double = 0.0,
    @SerializedName("Py")
    var latitude: Double = 0.0,
    @SerializedName("Class")
    var `class`: String = "",
    @SerializedName("Map")
    var map: String = "",
    @SerializedName("Parkinginfo")
    var parkingInfo: String = ""
)