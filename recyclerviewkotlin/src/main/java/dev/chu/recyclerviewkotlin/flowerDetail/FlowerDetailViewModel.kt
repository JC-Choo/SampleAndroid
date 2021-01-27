package dev.chu.recyclerviewkotlin.flowerDetail

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.chu.recyclerviewkotlin.data.DataSource
import dev.chu.recyclerviewkotlin.data.Flower

class FlowerDetailViewModel(private val dataSource: DataSource) : ViewModel() {

    /* Queries dataSource to returns a flower that corresponds to an id. */
    fun getFlowerForId(id: Long): Flower? {
        return dataSource.getFlowerForId(id)
    }

    /* Queries dataSource to remove a flower. */
    fun removeFlower(flower: Flower) {
        dataSource.removeFlower(flower)
    }
}

class FlowerDetailViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FlowerDetailViewModel::class.java)) {
            return FlowerDetailViewModel(dataSource = DataSource.get(context.resources)) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}