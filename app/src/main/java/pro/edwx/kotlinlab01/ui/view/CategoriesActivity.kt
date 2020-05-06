package pro.edwx.kotlinlab01.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_categories.*
import pro.edwx.kotlinlab01.R
import pro.edwx.kotlinlab01.data.local.entity.Category
import pro.edwx.kotlinlab01.di.Injection
import pro.edwx.kotlinlab01.ui.adapter.CategoriesAdapter
import pro.edwx.kotlinlab01.ui.viewmodel.CategoriesViewModel

class CategoriesActivity : AppCompatActivity() {

    private lateinit var adapter: CategoriesAdapter
    private lateinit var viewModel: CategoriesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)

        setUpViewModel()
        setUpAdapter()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.categories, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.action_about) {
            Toast.makeText(this, "Powered by Edwar A. Gaspar", Toast.LENGTH_SHORT).show()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun setUpAdapter() {
        adapter = CategoriesAdapter(viewModel.categories.value?: emptyList()) { category: Category, i: Int ->
            Toast.makeText(this,
                "Categoría: ${category.name}, Posición: $i", Toast.LENGTH_LONG).show()
        }
        rvCategories.layoutManager = LinearLayoutManager(this)
        rvCategories.adapter = adapter
    }

    override fun onResume() {
        super.onResume()

        rvCategories.visibility = View.GONE
        tvEmpty.visibility = View.GONE

        viewModel.getCategories()
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(this, Injection.provideCategoriesViewModelFactory())
            .get(CategoriesViewModel::class.java)

        viewModel.categories.observe(this@CategoriesActivity, Observer {
            tvEmpty.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE

            adapter.setItems(it)

            rvCategories.visibility = View.VISIBLE
        })

        viewModel.loading.observe(this@CategoriesActivity, Observer {
            pbLoading.visibility = if (it) View.VISIBLE else View.GONE
        })
    }
}
