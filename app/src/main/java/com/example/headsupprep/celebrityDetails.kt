package com.example.headsupprep

import com.google.gson.annotations.SerializedName

data class celebrityDetails( val name: String, val taboo1: String, val taboo2: String, val taboo3: String, val pk: Int)

//class celebrityDetails {
//    var data: List<celebrityValue>? = null
//    class celebrityValue {
//        @SerializedName("pk")
//        var id: Int? = null
//
//        @SerializedName("name")
//        var name: String? = null
//
//        @SerializedName("taboo1")
//        var taboo1: String? = null
//
//        @SerializedName("taboo2")
//        var taboo2: String? = null
//
//        @SerializedName("taboo3")
//        var taboo3: String? = null
//
//
//        constructor(id: Int, name: String, taboo1: String,
//                    taboo2: String, taboo3: String) {
//            this.id = id
//            this.name = name
//            this.taboo1 = taboo1
//            this.taboo2 = taboo2
//            this.taboo3 = taboo3
//        }
//    }
//}
