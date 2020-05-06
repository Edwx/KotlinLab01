package pro.edwx.kotlinlab01.data.model

import java.io.Serializable

data class CategoryResponse (
    val _id: String,
    val nombre: String,
    val descripcion: String,
    val __v: Int
): Serializable