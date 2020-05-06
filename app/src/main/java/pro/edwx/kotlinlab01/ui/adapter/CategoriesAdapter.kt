package pro.edwx.kotlinlab01.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pro.edwx.kotlinlab01.R
import pro.edwx.kotlinlab01.data.local.entity.Category
import java.text.MessageFormat

class CategoriesAdapter(
    private var categories: List<Category>,
    val listener: (Category, Int) -> Unit
): RecyclerView.Adapter<CategoriesAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val tvName: TextView = itemView.findViewById(R.id.tvName)
        private val tvDescription: TextView = itemView.findViewById(R.id.tvDescription)

        fun bind(category: Category) = with(itemView) {
            tvName.text = MessageFormat.format("Categoría: {0}", category.name.toString())
            tvDescription.text = MessageFormat.format("Descripción: {0}", category.description.toString())

            setOnClickListener {
                listener(category, adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_categories_item, parent, false)
        return ItemViewHolder(view)
    }

    fun setItems(items: List<Category>) {
        categories = items
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(categories[position])
    }
}