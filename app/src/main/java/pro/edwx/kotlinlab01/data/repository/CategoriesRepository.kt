package pro.edwx.kotlinlab01.data.repository

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import pro.edwx.kotlinlab01.App
import pro.edwx.kotlinlab01.data.OperationCallback
import pro.edwx.kotlinlab01.data.local.dao.CategoryDao
import pro.edwx.kotlinlab01.data.local.entity.Category
import pro.edwx.kotlinlab01.data.model.CategoryResponse
import pro.edwx.sheetsapp.data.network.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class CategoriesRepository : CategoriesDataSource {

    var categoryDao: CategoryDao? = null

    init {
        categoryDao = App.database.categoryDao()
    }

    override fun getCategories(callback: OperationCallback<List<Category>>) {
        getRemoteCategories(object : OperationCallback<List<CategoryResponse>>{
            override fun onSuccess(data: List<CategoryResponse>) {

                insertAllRemoteData(data, object: OperationCallback<Boolean> {
                    override fun onSuccess(data: Boolean) {
                        getLocalCategories(object : OperationCallback<List<Category>> {
                            override fun onSuccess(data: List<Category>) {
                                callback.onSuccess(data)
                            }

                            override fun onError(error: String?) {
                                callback.onError(error)
                            }
                        })
                    }

                    override fun onError(error: String?) {
                        callback.onError(error)
                    }
                })
            }

            override fun onError(error: String?) {
                getLocalCategories(object: OperationCallback<List<Category>> {
                    override fun onSuccess(data: List<Category>) {
                        callback.onSuccess(data)
                    }

                    override fun onError(error: String?) {
                        callback.onError(error)
                    }
                })
            }
        })
    }

    fun insertAllRemoteData(data: List<CategoryResponse>, callback: OperationCallback<Boolean>) {
        CoroutineScope(Dispatchers.IO).launch {
            for (item: CategoryResponse in data) {
                insertWithoutExist(item)
            }

            async {
                callback.onSuccess(true)
            }
        }
    }

    private fun insertWithoutExist(categoryRemote: CategoryResponse) {
        val categoryLocal = categoryDao?.loadCategoryById(categoryRemote._id)

        if (!categoryLocal?.name.equals(categoryRemote.nombre)
            || categoryLocal?.description.equals(categoryRemote.descripcion)) {
            try {
                categoryDao!!.deleteCategoryById(categoryRemote._id)

                val category = Category(null,
                    categoryRemote._id,
                    categoryRemote.nombre,
                    categoryRemote.descripcion)

                categoryDao!!.insert(category)
            } catch (ex: Exception) {
                Log.e("edwx", ex.toString())
            }
        }
    }

    private fun getLocalCategories(callback: OperationCallback<List<Category>>) {
        CoroutineScope(Dispatchers.IO).launch {
            val categories = categoryDao?.getAll()

            try {
                callback.onSuccess(categories?: emptyList())
            } catch (ex: Exception) {
                callback.onError(ex.message.toString())
            }
        }
    }

    private fun getRemoteCategories(callback: OperationCallback<List<CategoryResponse>>) {
        RetrofitBuilder.apiService.getCategories()
            .enqueue(object : Callback<List<CategoryResponse>> {
                override fun onFailure(call: Call<List<CategoryResponse>>, t: Throwable) {
                    callback.onError(t.message)
                }

                override fun onResponse(
                    call: Call<List<CategoryResponse>>,
                    response: Response<List<CategoryResponse>>
                ) {
                    if (!response.isSuccessful) {
                        callback.onError("No se logró sincronizar la relación de categorías.")
                        return
                    }

                    response.body()?.let {
                        callback.onSuccess(it)
                    }
                }
            })
    }

    /*override fun insertCategories(
        categoriesResponse: List<CategoryResponse>,
        callback: OperationCallback<List<Category>>
    ) {
        val result: ArrayList<Category> = arrayListOf()

        CoroutineScope(Dispatchers.IO).launch {
            for (item: CategoryResponse in categoriesResponse) {
                val category = Category(null, item._id, item.nombre, item.descripcion)
                val id = categoryDao?.insert(category)

                if (id!! > 0) {
                    result.add(category)
                }
            }

            callback.onSuccess(result)
        }
    }

    override fun deleteAllCategories(callback: OperationCallback<Boolean>) {
        CoroutineScope(Dispatchers.IO).launch {
            categoryDao?.deleteAll()
            callback.onSuccess(true)
        }
    }*/
}