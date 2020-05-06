package pro.edwx.kotlinlab01.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "CATEGORIES")
data class Category (
    @PrimaryKey(autoGenerate = true)
    @NotNull
    val categoryId: Int?,

    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "name")
    val name: String?,

    @ColumnInfo(name = "description")
    val description: String?
)