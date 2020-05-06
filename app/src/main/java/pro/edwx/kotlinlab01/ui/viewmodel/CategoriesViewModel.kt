package pro.edwx.kotlinlab01.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pro.edwx.kotlinlab01.data.OperationCallback
import pro.edwx.kotlinlab01.data.local.entity.Category
import pro.edwx.kotlinlab01.data.repository.CategoriesDataSource

class CategoriesViewModel(private val categoriesRepository: CategoriesDataSource) : ViewModel() {

    private val _categories = MutableLiveData<List<Category>>().apply { value = emptyList() }
    val categories: LiveData<List<Category>> get() = _categories

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _empty = MutableLiveData<Boolean>()
    val empty: LiveData<Boolean> = _empty

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun getCategories() {
        _loading.value = true

        categoriesRepository.getCategories(object: OperationCallback<List<Category>>{
            override fun onError(error: String?) {
                _loading.postValue(false)
                _error.postValue(error)
            }

            override fun onSuccess(data: List<Category>) {
                _loading.postValue(false)
                _categories.postValue(data)
            }
        })
    }
}