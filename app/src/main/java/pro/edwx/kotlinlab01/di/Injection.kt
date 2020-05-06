package pro.edwx.kotlinlab01.di

import androidx.lifecycle.ViewModelProvider
import pro.edwx.kotlinlab01.data.repository.CategoriesDataSource
import pro.edwx.kotlinlab01.data.repository.CategoriesRepository
import pro.edwx.kotlinlab01.ui.viewmodel.CategoriesViewModelFactory

object Injection {

    private val categoriesDataSource: CategoriesDataSource = CategoriesRepository()
    private val categoriesViewModelFactory = CategoriesViewModelFactory(categoriesDataSource)

    fun providerCategoriesRepository(): CategoriesDataSource {
        return categoriesDataSource
    }

    fun provideCategoriesViewModelFactory(): ViewModelProvider.Factory {
        return categoriesViewModelFactory
    }
}