package fr.isen.aurelien.androiderestaurant.network

data class Item (
    var id:Int,
    var name: String,
    var image:String,
    var quantity:Int,
    var price:Float
)