package dev.chu.recyclerviewkotlin.addFlower

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import dev.chu.recyclerviewkotlin.R

const val FLOWER_NAME = "name"
const val FLOWER_DESCRIPTION = "description"

class AddFlowerActivity : AppCompatActivity() {

    private lateinit var addFlowerName: TextInputEditText
    private lateinit var addFlowerDescription: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_flower)

        findViewById<Button>(R.id.done_button).setOnClickListener {
            addFlower()
        }
        addFlowerName = findViewById(R.id.add_flower_name)
        addFlowerDescription = findViewById(R.id.add_flower_description)
    }

    /* The onClick action for the done button. Closes the activity and returns the new flower name
    and description as part of the intent. If the name or description are missing, the result is set
    to cancelled. */

    private fun addFlower() {
        if (addFlowerName.text.isNullOrEmpty() || addFlowerDescription.text.isNullOrEmpty()) {
            setResult(Activity.RESULT_CANCELED, Intent())
        } else {
            setResult(Activity.RESULT_OK, Intent().apply {
                val name = addFlowerName.text.toString()
                val description = addFlowerDescription.text.toString()
                putExtra(FLOWER_NAME, name)
                putExtra(FLOWER_DESCRIPTION, description)
            })
        }
        finish()
    }
}