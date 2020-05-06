package pro.edwx.kotlinlab01.data.local.dao

import androidx.room.*
import org.jetbrains.annotations.NotNull
import pro.edwx.kotlinlab01.data.local.entity.Category

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(category: Category): Long

    @Update
    fun update(category: Category)

    @Query("SELECT * FROM CATEGORIES WHERE id = :id")
    fun loadCategoryById(id: String): Category

    @Query("DELETE FROM CATEGORIES WHERE id = :id")
    fun deleteCategoryById(id: String)

    @Query("DELETE FROM CATEGORIES")
    fun deleteAll()

    @Query("SELECT * FROM CATEGORIES")
    fun getAll(): List<Category>

}