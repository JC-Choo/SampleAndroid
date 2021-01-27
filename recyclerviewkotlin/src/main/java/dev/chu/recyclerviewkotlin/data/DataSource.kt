package dev.chu.recyclerviewkotlin.data

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class DataSource(resources: Resources) {

    companion object {
        private var INSTANCE: DataSource? = null

        fun get(resources: Resources): DataSource {
            return synchronized(DataSource::class) {
                val newInstance = INSTANCE ?: DataSource(resources)
                INSTANCE = newInstance
                return newInstance
            }
        }
    }

    private val initialFlowerList = flowerList(resources)
    private val flowersLiveData = MutableLiveData(initialFlowerList)

    /* Add flower to live data and posts value. */
    fun addFlower(flower: Flower) {
        val currentList = flowersLiveData.value
        if (currentList == null) {
            flowersLiveData.postValue(listOf(flower))
        } else {
            val updatedList = currentList.toMutableList()
            updatedList.add(0, flower)
            flowersLiveData.postValue(updatedList)
        }
    }

    /* Removes flower from liveData and posts value. */
    fun removeFlower(flower: Flower) {
        val currentList = flowersLiveData.value
        if (currentList != null) {
            val updatedList = currentList.toMutableList()
            updatedList.remove(flower)
            flowersLiveData.postValue(updatedList)
        }
    }

    /* Returns flower given an ID. */
    fun getFlowerForId(id: Long): Flower? {
        flowersLiveData.value?.let { flowers ->
            return flowers.firstOrNull{ it.id == id}
        }

        return null
    }

    fun getFlowerList(): LiveData<List<Flower>> {
        return flowersLiveData
    }

    /* Returns a random flower asset for flowers that are added. */
    fun getRandomFlowerImageAsset(): Int? {
        val randomNumber = (initialFlowerList.indices).random()
        return initialFlowerList[randomNumber].image
    }
}