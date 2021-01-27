package dev.chu.recyclerviewkotlin.flowerList

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.chu.recyclerviewkotlin.data.DataSource
import dev.chu.recyclerviewkotlin.data.Flower
import kotlin.random.Random

class FlowersListViewModel(
    private val dataSource: DataSource
) : ViewModel() {

    val flowersLiveData = dataSource.getFlowerList()

    /* If the name and description are present, create new Flower and add it to the datasource */
    fun insertFlower(flowerName: String?, flowerDescription: String?) {
        if (flowerName == null || flowerDescription == null) {
            return
        }

        val image = dataSource.getRandomFlowerImageAsset()
        val newFlower = Flower(
            Random.nextLong(),
            flowerName,
            image,
            flowerDescription
        )

        dataSource.addFlower(newFlower)
    }
}

class FlowersListViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FlowersListViewModel::class.java)) {
            return FlowersListViewModel(dataSource = DataSource.get(context.resources)) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}