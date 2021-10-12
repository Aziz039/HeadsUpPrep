package com.example.headsupprep

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchCelebrity : AppCompatActivity() {
    private lateinit var clSearch: ConstraintLayout
    private lateinit var ti_name: TextInputEditText
    private lateinit var ti_taboo1: TextInputEditText
    private lateinit var ti_taboo2: TextInputEditText
    private lateinit var ti_taboo3: TextInputEditText
    private lateinit var bt_update: Button
    private lateinit var bt_delete: Button
    private lateinit var bt_back: Button

    private var celebrity_pk: Int = 0
    private var celebrity_name: String = ""
    private var celebrity_taboo1: String = ""
    private var celebrity_taboo2: String = ""
    private var celebrity_taboo3: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_celebrity)
        initVars()

    }

    private fun initVars() {
        celebrity_pk = intent.extras?.getInt("wantedCelebrity_pk")!!
        celebrity_name = intent.extras?.getString("wantedCelebrity_name")!!
        celebrity_taboo1 = intent.extras?.getString("wantedCelebrity_taboo1")!!
        celebrity_taboo2 = intent.extras?.getString("wantedCelebrity_taboo2")!!
        celebrity_taboo3 = intent.extras?.getString("wantedCelebrity_taboo3")!!

        clSearch = findViewById(R.id.clSearch)
        ti_name = findViewById(R.id.ti_name)
        ti_taboo1 = findViewById(R.id.ti_taboo1)
        ti_taboo2 = findViewById(R.id.ti_taboo2)
        ti_taboo3 = findViewById(R.id.ti_taboo3)
        bt_update = findViewById(R.id.bt_update)
        bt_delete = findViewById(R.id.bt_delete)
        bt_back = findViewById(R.id.bt_back)

        ti_name.setText(celebrity_name)
        ti_taboo1.setText(celebrity_taboo1)
        ti_taboo2.setText(celebrity_taboo2)
        ti_taboo3.setText(celebrity_taboo3)

        bt_update.setOnClickListener { handleUpdate() }
        bt_delete.setOnClickListener { handleDelete() }
        bt_back.setOnClickListener { handleBack() }
    }

    private fun handleUpdate() {
        if (ti_name.text.isNullOrBlank() ||
            ti_taboo1.text.isNullOrBlank() ||
            ti_taboo2.text.isNullOrBlank() ||
            ti_taboo3.text.isNullOrBlank()) {
            Toast.makeText(clSearch.context, "Empty fields..", Toast.LENGTH_SHORT).show()
        } else {
            val updatedCelebrity = celebrityDetails(ti_name.text.toString(), ti_taboo1.text.toString(),
                ti_taboo2.text.toString(), ti_taboo3.text.toString(), 0)
            val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
            apiInterface?.updateCelebrity(celebrity_pk, updatedCelebrity)?.enqueue(object : Callback<celebrityDetails> {
                override fun onResponse(
                    call: Call<celebrityDetails>,
                    response: Response<celebrityDetails>
                ) {
                    Log.d("LogSearchActivity", "Celebrity was updated successfully")
                    handleBack()
                }

                override fun onFailure(call: Call<celebrityDetails>, t: Throwable) {
                    Log.d("LogSearchActivity", "Connection failed.. $t")
                    Toast.makeText(clSearch.context, "Connection failed..", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun handleDelete() {
        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
        if (apiInterface != null) {
            apiInterface.deleteCelebrity(celebrity_pk).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    Log.d("LogSearchActivity", "Celebrity was deleted successfully")
                    handleBack()
                }
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.d("LogSearchActivity", "Connection failed.. $t")
                    Toast.makeText(clSearch.context, "Connection failed..", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun handleBack() {
        val intent = Intent(clSearch.context, MainActivity::class.java)
        startActivity(intent)
    }
}

