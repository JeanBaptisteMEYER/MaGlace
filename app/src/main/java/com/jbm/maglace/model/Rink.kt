package com.jbm.maglace.model

import android.graphics.drawable.Drawable
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

    fun getConditionDrawable(): Int {
        when (condition) {
            Constants().CONDITION_EXCELLENTE -> return R.drawable.ic_three_star
            Constants().CONDITION_BONNE -> return R.drawable.ic_two_star
            Constants().CONDITION_MAIVAISE -> return R.drawable.ic_one_star
            else -> return R.drawable.ic_no_star
        }
    }

    fun getTypeMarkerDrawable(): Int {
        when (type) {
            Constants().TYPE_PSE -> return R.drawable.ic_hockey_map_marker
            else -> return R.drawable.ic_skate_map_marker
        }
    }

    @Override
    override fun toString(): String {
        return "ID = " + id + ", name = " + name + ", state = " + condition
    }
}