package com.example.rtcs


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class GigInsert : AppCompatActivity() {

    private lateinit var btnInsert: Button
    private lateinit var gigTitle:EditText
    private lateinit var  gigPrice:EditText
    private lateinit var  category:Spinner
    private lateinit var gigDescription:EditText

    private lateinit var dbRef:DatabaseReference

//    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.gig_insert)

        gigTitle = findViewById(R.id.gigtitle2)
        gigPrice=findViewById(R.id.gigprice)
        gigDescription=findViewById(R.id.gigdescription)
        category=findViewById(R.id.spinnerCategory)
        btnInsert=findViewById(R.id.insertBtn)

        dbRef = FirebaseDatabase.getInstance().getReference("GigData")

        btnInsert.setOnClickListener {
            saveGigData()
        }



    }

    private fun saveGigData(){

        val gigTitle1 =gigTitle.text.toString()
        val gigDescription1 = gigDescription.text.toString()
        val gigPrice1 =gigPrice.text.toString()
        val gigCategory1=category.selectedItem.toString()

        if (gigTitle1.isEmpty()){
            gigTitle.error ="please enter the gig title"
            
        }
        if (gigDescription1.isEmpty()){
            gigDescription.error ="please enter the gig Description"

        }
        if (gigPrice1.isEmpty()){
            gigPrice.error ="please enter the gig price in dollars"

        }
        if (gigCategory1.isEmpty()){
            val errorText = category.selectedView as TextView
            errorText.error = "Please select a category"

        }

        val gigId = dbRef.push().key!!

        val gig = GigModel(gigId,gigTitle1,gigCategory1,gigDescription1,gigPrice1)

        dbRef.child(gigId).setValue(gig)
            .addOnCompleteListener {
                Toast.makeText(this, "Your gig created  successfully", Toast.LENGTH_LONG).show()

                gigTitle.text.clear()
                gigDescription.text.clear()
                gigPrice.text.clear()
                category.setSelection(0)

                val intent = Intent(this@GigInsert, Optionclz::class.java)
                startActivity(intent)
                finish()


            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }

    }
}