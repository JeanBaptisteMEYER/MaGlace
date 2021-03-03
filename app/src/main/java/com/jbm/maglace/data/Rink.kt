package com.jbm.maglace.data

import com.google.gson.annotations.SerializedName

class Rink {
    @SerializedName ("id") var id = 0
    @SerializedName ("nom") var nom = ""
    @SerializedName ("description") var description =  ""
    @SerializedName ("genre") var genre =  ""
    @SerializedName ("ouvert") var ouvert =  false
    @SerializedName ("deblaye") var deblaye =  false
    @SerializedName ("arrose") var arrose =  false
    @SerializedName ("resurface") var resurface =  false
    @SerializedName ("condition") var condition =  ""
    @SerializedName ("parc") var parc =  ""
    @SerializedName ("adresse") var adresse =  ""
    @SerializedName ("tel") var tel = ""
    @SerializedName ("ext") var ext =  ""
    @SerializedName ("lat") var lat =  0.0
    @SerializedName ("lng") var lng =  0.0
    @SerializedName ("slug") var slug =  ""

    @Override
    override fun toString(): String {
        return "ID = " + id + ", name = " + nom + ", state = " + condition
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