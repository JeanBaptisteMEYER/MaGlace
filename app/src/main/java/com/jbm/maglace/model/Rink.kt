package com.jbm.maglace.model

import com.google.gson.annotations.SerializedName
import com.jbm.maglace.R
import com.jbm.maglace.utils.Constants

class Rink {
    @SerializedName ("id") var id = 0
    @SerializedName ("nom") var name = ""
    @SerializedName ("description") var description =  ""
    @SerializedName ("genre") var type =  ""
    @SerializedName ("ouvert") var open =  false
    @SerializedName ("deblaye") var clear =  false
    @SerializedName ("arrose") var watered =  false
    @SerializedName ("resurface") var resurface =  false
    @SerializedName ("condition") var condition =  ""
    @SerializedName ("parc") var park =  ""
    @SerializedName ("adresse") var address =  ""
    @SerializedName ("tel") var tel = ""
    @SerializedName ("ext") var ext =  ""
    @SerializedName ("lat") var lat =  0.0
    @SerializedName ("lng") var lng =  0.0
    @SerializedName ("slug") var slug =  ""
    var distFromUser: Float = 0.toFloat() // in Km

    //TODO move to databinding
    fun getListDrawable(): Int {
        when (type) {
            Constants().TYPE_PSE -> return getHockeyDrawable()
            Constants().TYPE_PPL -> return getSkateDrawable()
            else -> return getParkDrawable()
        }
    }

    fun getHockeyDrawable(): Int {
        when (condition) {
            Constants().CONDITION_EXCELLENTE -> return R.drawable.ic_hockey_green
            Constants().CONDITION_BONNE -> return R.drawable.ic_hockey_orange
            Constants().CONDITION_MAIVAISE -> return R.drawable.ic_hockey_red
            else -> return R.drawable.ic_hockey
        }
    }

    fun getSkateDrawable(): Int {
        when (condition) {
            Constants().CONDITION_EXCELLENTE -> return R.drawable.ic_skate_green
            Constants().CONDITION_BONNE -> return R.drawable.ic_skate_orange
            Constants().CONDITION_MAIVAISE -> return R.drawable.ic_skate_red
            else -> return R.drawable.ic_skate
        }
    }

    fun getParkDrawable(): Int {
        when (condition) {
            Constants().CONDITION_EXCELLENTE -> return R.drawable.ic_park_green
            Constants().CONDITION_BONNE -> return R.drawable.ic_park_orange
            Constants().CONDITION_MAIVAISE -> return R.drawable.ic_park_red
            else -> return R.drawable.ic_park
        }
    }

    fun getMarkerDrawable(): Int {
        when (type) {
            Constants().TYPE_PSE -> return getMarkerPse()
            Constants().TYPE_PPL -> return getMarkerPpl()
            else -> return getMarkerPp()
        }
    }

    fun getMarkerPse(): Int {
        when (condition) {
            Constants().CONDITION_EXCELLENTE -> return R.drawable.ic_hockey_marker_green
            Constants().CONDITION_BONNE -> return R.drawable.ic_hockey_marker_orange
            Constants().CONDITION_MAIVAISE -> return R.drawable.ic_hockey_marker_red
            else -> return R.drawable.ic_hockey_marker
        }
    }

    fun getMarkerPpl(): Int {
        when (condition) {
            Constants().CONDITION_EXCELLENTE -> return R.drawable.ic_skate_marker_green
            Constants().CONDITION_BONNE -> return R.drawable.ic_skate_marker_orange
            Constants().CONDITION_MAIVAISE -> return R.drawable.ic_skate_marker_red
            else -> return R.drawable.ic_skate_marker
        }
    }

    fun getMarkerPp(): Int {
        when (condition) {
            Constants().CONDITION_EXCELLENTE -> return R.drawable.ic_park_marker_green
            Constants().CONDITION_BONNE -> return R.drawable.ic_park_marker_orange
            Constants().CONDITION_MAIVAISE -> return R.drawable.ic_park_marker_red
            else -> return R.drawable.ic_park_marker
        }
    }

    @Override
    override fun toString(): String {
        return "ID = " + id + ", name = " + name + ", state = " + condition
    }
}