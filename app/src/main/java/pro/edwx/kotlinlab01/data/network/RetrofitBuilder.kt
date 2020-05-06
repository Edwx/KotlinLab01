package pro.edwx.sheetsapp.data.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import pro.edwx.kotlinlab01.util.Constant
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    private val retrofitBuilder: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }

    val apiService: Api by lazy {
        retrofitBuilder.build().create(Api::class.java)
    }

    private val retrofitBuilder2: Retrofit.Builder by lazy {
        Retrofit.Builder().baseUrl(Constant.BASE_URL)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
    }

    val apiService2: Api by lazy {
        retrofitBuilder2.build().create(Api::class.java)
    }
}