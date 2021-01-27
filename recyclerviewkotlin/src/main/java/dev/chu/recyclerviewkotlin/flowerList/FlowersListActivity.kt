package dev.chu.recyclerviewkotlin.flowerList

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.chu.recyclerviewkotlin.R
import dev.chu.recyclerviewkotlin.addFlower.AddFlowerActivity
import dev.chu.recyclerviewkotlin.addFlower.FLOWER_DESCRIPTION
import dev.chu.recyclerviewkotlin.addFlower.FLOWER_NAME
import dev.chu.recyclerviewkotlin.data.Flower
import dev.chu.recyclerviewkotlin.flowerDetail.FlowerDetailActivity

const val FLOWER_ID = "flower id"

class FlowersListActivity: AppCompatActivity() {

    private val newFlowerActivityRequestCode = 1
    private val flowersListViewModel by viewModels<FlowersListViewModel> {
        FlowersListViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /* Instantiates headerAdapter and flowersAdapter. Both adapters are added to concatAdapter.
        which displays the contents sequentially */
        val headerAdapter = HeaderAdapter()
        val flowersAdapter = FlowersAdapter { flower -> adapterOnClick(flower) }
        val concatAdapter = ConcatAdapter(headerAdapter, flowersAdapter)

        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.adapter = concatAdapter

        flowersListViewModel.flowersLiveData.observe(this, {
            it?.let {
                flowersAdapter.submitList(it as MutableList<Flower>)
                headerAdapter.updateFlowerCount(it.size)
            }
        })

        val fab: View = findViewById(R.id.fab)
        fab.setOnClickListener {
            fabOnClick()
        }
    }

    /* Opens FlowerDetailActivity when RecyclerView item is clicked. */
    private fun adapterOnClick(flower: Flower) {
        startActivity(Intent(this, FlowerDetailActivity()::class.java).apply {
            putExtra(FLOWER_ID, flower.id)
        })
    }

    /* Adds flower to flowerList when FAB is clicked. */
    private fun fabOnClick() {
        startActivityForResult(Intent(this, AddFlowerActivity::class.java), newFlowerActivityRequestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        /* Inserts flower into viewModel. */
        if (requestCode == newFlowerActivityRequestCode && resultCode == Activity.RESULT_OK) {
            intentData?.let { data ->
                val flowerName = data.getStringExtra(FLOWER_NAME)
                val flowerDescription = data.getStringExtra(FLOWER_DESCRIPTION)

                flowersListViewModel.insertFlower(flowerName, flowerDescription)
            }
        }
    }
}