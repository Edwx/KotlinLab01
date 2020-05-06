package pro.edwx.sheetsapp.data.network

import pro.edwx.kotlinlab01.data.model.CategoryResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface Api {

    @GET("categoria")
    fun getCategories(): Call<List<CategoryResponse>>
}