package fr.isen.aurelien.androiderestaurant.network

import com.google.gson.annotations.SerializedName
import java.io.Serializable


class Plate (
    @SerializedName("name_fr") val name: String,
    @SerializedName("images") val image: List<String>,
    @SerializedName("prices") val prices: List<Price>,
    @SerializedName("ingredients") val ingredients: List<Ingredients>

): Serializable
