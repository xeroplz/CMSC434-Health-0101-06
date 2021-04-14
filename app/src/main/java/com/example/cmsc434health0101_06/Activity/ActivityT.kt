package com.example.cmsc434health0101_06.Activity

data class ActivityT(
    var activityName: String,
    var activityDate:String,
    var activityType: String,
    var activityDuration: Int,
    var activityWeight: Int,
    var activityRep: Int
)

data class Activities(
        val activities: ArrayList<ActivityT>
)

