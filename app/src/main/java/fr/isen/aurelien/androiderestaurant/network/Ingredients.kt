package fr.isen.aurelien.androiderestaurant.network

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Ingredients (
    @SerializedName("name_fr") val ingredients: String,

): Serializable