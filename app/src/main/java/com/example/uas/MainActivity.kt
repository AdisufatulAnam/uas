package com.example.uas

import android.R
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
//pada baris code ini merupakan barisan dari inisialisai dari variable yang akan digunakan pada program ini.
class MainActivity : AppCompatActivity() {
    var buttonLampu1: ToggleButton? = null
    var buttonLampu2: ToggleButton? = null
    var statuspir: TextView? = null
    var statusLdr: TextView? = null
    var valueButton: String? = null
    var valueLdr: String? = null
    var valueLampu1: String? = null
    var valueLampu2: String? = null
    var dref: DatabaseReference? = null
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_item)
        buttonLampu1 = findViewById<View>(R.id.toggle) as ToggleButton
        buttonLampu2 = findViewById<View>(R.id.toggle) as ToggleButton
        statusLdr = findViewById<View>(R.id.cut) as TextView
        statuspir = findViewById<View>(R.id.cut) as TextView
        dref = FirebaseDatabase.getInstance().getReference()
        dref!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                valueLdr = dataSnapshot.child("Node1/ldr").getValue().toString()
                statusLdr!!.text = valueLdr
                valueButton = dataSnapshot.child("Node1/pir").getValue().toString()
                if (valueButton == "0") statuspir!!.text = "no motion"
                    else statuspir!!.text = "motion detect "
                valueLampu1 = dataSnapshot.child("Node1/led").getValue().toString()
                if (valueLampu1 == "0") buttonLampu1!!.isChecked =
                    false else buttonLampu1!!.isChecked =
                    true
                valueLampu2 = dataSnapshot.child("Node1/led1").getValue().toString()
                if (valueLampu2 == "0") buttonLampu2!!.isChecked =
                    false else buttonLampu2!!.isChecked =
                    true
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
        buttonLampu1!!.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                val lampu1Ref: DatabaseReference =
                    FirebaseDatabase.getInstance().getReference("Node1/led")
                lampu1Ref.setValue(1)
            } else {
                val lampu1Ref: DatabaseReference =
                    FirebaseDatabase.getInstance().getReference("Node1/led")
                lampu1Ref.setValue(0)
            }
        }
        buttonLampu2!!.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                val lampu2Ref: DatabaseReference =
                    FirebaseDatabase.getInstance().getReference("Node1/led1")
                lampu2Ref.keepSynced(true)
                lampu2Ref.setValue(1)
            } else {
                val lampu2Ref: DatabaseReference =
                    FirebaseDatabase.getInstance().getReference("Node1/led1")
                lampu2Ref.keepSynced(true)
                lampu2Ref.setValue(0)
            }
        }
    }
}