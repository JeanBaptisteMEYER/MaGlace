package com.jbm.maglace.model

import com.google.gson.annotations.SerializedName

class District {
    @SerializedName("nom_arr") var nom_arr = ""
    @SerializedName ("cle") var cle = ""
    @SerializedName ("date_maj") var dateMaj = ""
    @SerializedName ("patinoires") var patinoires = listOf<Rink>()
}

/*
"nom_arr": "Ville-Marie",
    "cle": "vma",
    "date_maj": "2021-03-03T13:29:42.000Z",
    "patinoires":
 */