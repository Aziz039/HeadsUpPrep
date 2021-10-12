package com.example.headsupprep

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var clMain: ConstraintLayout
    private lateinit var rv_headsUp: RecyclerView
    private lateinit var bt_add: Button
    private lateinit var ti_name: TextInputEditText
    private lateinit var bt_search: Button

    private val listOfCelebrities = ArrayList<celebrityDetails>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initVars()

        getAllCelebritis()
    }

    private fun initVars() {
        clMain = findViewById(R.id.clMain)
        rv_headsUp = findViewById(R.id.rv_headsUp)
        bt_add = findViewById(R.id.bt_add)
        ti_name = findViewById(R.id.ti_name)
        bt_search = findViewById(R.id.bt_search)

        bt_add.setOnClickListener { handleAdd() }
        bt_search.setOnClickListener { handleSearch() }
    }

    private fun handleAdd() {
        val intent = Intent(this, AddCelebrity::class.java)
        startActivity(intent)
    }

    private fun handleSearch() {
        if (ti_name.text.isNullOrBlank()) {
            Toast.makeText(clMain.context, "Insert a celebrity name..", Toast.LENGTH_SHORT).show()
        } else {
            val wantedCelebrity: String = ti_name.text.toString()
            val selectedCelebrity = listOfCelebrities.filter { it.name == wantedCelebrity }
            if (selectedCelebrity.isEmpty()) {
                Toast.makeText(clMain.context, "There is no celebrity with this name..", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(clMain.context, SearchCelebrity::class.java)
                intent.putExtra("wantedCelebrity_pk", selectedCelebrity[0].pk)
                intent.putExtra("wantedCelebrity_name", selectedCelebrity[0].name)
                intent.putExtra("wantedCelebrity_taboo1", selectedCelebrity[0].taboo1)
                intent.putExtra("wantedCelebrity_taboo2", selectedCelebrity[0].taboo2)
                intent.putExtra("wantedCelebrity_taboo3", selectedCelebrity[0].taboo3)
                startActivity(intent)
            }
        }
    }

    private fun getAllCelebritis() {
        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
        val getAll: Call<ArrayList<celebrityDetails>> = apiInterface?.getAll()!!

        getAll?.enqueue(object: Callback<ArrayList<celebrityDetails>> {
            override fun onResponse(
                call: Call<ArrayList<celebrityDetails>>,
                response: Response<ArrayList<celebrityDetails>>
            ) {
                Log.d("LogMainActivity", "Fetched successfully..")
                for (celebrity in response.body()!!) {
                    val tempCelebrity = celebrityDetails(

                        celebrity.name!!,
                        celebrity.taboo1!!,
                        celebrity.taboo2!!,
                        celebrity.taboo3!!,
                        celebrity.pk
                    )
                    listOfCelebrities.add(tempCelebrity)
                }
                addToRV(listOfCelebrities)
            }
            override fun onFailure(call: Call<ArrayList<celebrityDetails>>, t: Throwable) {
                Log.d("LogMainActivity", "Connection failed.. $t")
                Toast.makeText(clMain.context, "Connection failed..", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun addToRV(itemToAdd: ArrayList<celebrityDetails>) {
        rv_headsUp.adapter = RecyclerAdapter(itemToAdd)
        rv_headsUp.layoutManager = LinearLayoutManager(this)
    }
}

