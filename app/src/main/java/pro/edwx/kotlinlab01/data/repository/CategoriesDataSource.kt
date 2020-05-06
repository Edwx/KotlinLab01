package pro.edwx.kotlinlab01.data.repository

import pro.edwx.kotlinlab01.data.OperationCallback
import pro.edwx.kotlinlab01.data.local.entity.Category
import pro.edwx.kotlinlab01.data.model.CategoryResponse

interface CategoriesDataSource {

    fun getCategories(callback: OperationCallback<List<Category>>)

    /*fun insertCategories(categoriesResponse: List<CategoryResponse>, callback: OperationCallback<List<Category>>)

    fun deleteAllCategories(callback: OperationCallback<Boolean>)*/
}