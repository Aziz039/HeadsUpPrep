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

class AddCelebrity : AppCompatActivity() {
    private lateinit var clAdd: ConstraintLayout
    private lateinit var ti_name: TextInputEditText
    private lateinit var ti_taboo1: TextInputEditText
    private lateinit var ti_taboo2: TextInputEditText
    private lateinit var ti_taboo3: TextInputEditText
    private lateinit var bt_add: Button
    private lateinit var bt_back: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_celebrity)

        initVars()
    }

    private fun initVars() {
        clAdd = findViewById(R.id.clAdd)
        ti_name = findViewById(R.id.ti_name)
        ti_taboo1 = findViewById(R.id.ti_taboo1)
        ti_taboo2 = findViewById(R.id.ti_taboo2)
        ti_taboo3 = findViewById(R.id.ti_taboo3)
        bt_add = findViewById(R.id.bt_add)
        bt_back = findViewById(R.id.bt_back)

        bt_add.setOnClickListener { handleAdd() }
        bt_back.setOnClickListener { handleBack() }
    }

    private fun handleAdd() {
        if (ti_name.text.isNullOrBlank() ||
            ti_taboo1.text.isNullOrBlank() ||
            ti_taboo2.text.isNullOrBlank() ||
            ti_taboo3.text.isNullOrBlank()) {
            Toast.makeText(clAdd.context, "Empty fields..", Toast.LENGTH_SHORT).show()
        } else {

            val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
            if (apiInterface != null) {
                val newCelebrity = celebrityDetails(ti_name.text.toString(), ti_taboo1.text.toString(),
                    ti_taboo2.text.toString(), ti_taboo3.text.toString(), 0)
                apiInterface.addCelebrity(newCelebrity).enqueue(object :
                    Callback<celebrityDetails> {
                    override fun onResponse(
                        call: Call<celebrityDetails>,
                        response: Response<celebrityDetails>
                    ) {
                        Log.d("LogAddActivity", "Result $response")
                        Log.d("LogAddActivity", "Celebrity was added successfully")
                        handleBack()
                    }

                    override fun onFailure(call: Call<celebrityDetails>, t: Throwable) {
                        Log.d("LogMainActivity", "Connection failed.. $t")
                        Toast.makeText(clAdd.context, "Connection failed..", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
    }


    private fun handleBack() {
        val intent = Intent(clAdd.context, MainActivity::class.java)
        startActivity(intent)
    }
}



























