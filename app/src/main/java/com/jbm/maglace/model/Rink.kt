package com.jbm.maglace.model

import com.google.gson.annotations.SerializedName
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

    fun getConditionCode(): Int {

        when (condition) {
            Constants().CONDITION_EXCELLENTE -> return 3
            Constants().CONDITION_BONNE -> return 2
            Constants().CONDITION_MAIVAISE -> return 1
            else -> return 0
        }
    }

    @Override
    override fun toString(): String {
        return "ID = " + id + ", name = " + name + ", state = " + condition
    }
}

/*
        "id": 544,
        "nom": "Patinoire avec bandes, René-Patenaude (PSE)",
        "description": "Patinoire avec bandes",
        "genre": "PSE",
        "ouvert": false,
        "deblaye": null,
        "arrose": null,
        "resurface": null,
        "condition": "N/A",
        "parc": "René-Patenaude",
        "adresse": "80, rue Ouimet Est",
        "tel": null,
        "ext": null,
        "lat": 45.5738806049,
        "lng": -73.6913233561,
        "slug": "rene-patenaude"
 */