package com.example.kotlinhw8

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kotlinhw8.databinding.ActivityShowInfoBinding

class ShowInfoActivity : AppCompatActivity() {
    lateinit var binding: ActivityShowInfoBinding
    var name :String? = ""
    var nationalCode :String? = ""
    var address :String? = ""
    var bornLocation :String? = ""
    var postCode :String? = ""
    var gender :String?=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowInfoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        name = getFromShared(NAME)
        nationalCode = getFromShared(NATIONALCODE)
        address = getFromShared(ADDRESS)
        bornLocation = getFromShared(BORNLOCATION)
        postCode = getFromShared(POSTCODE)
        gender = getFromShared(GENDER)

        setInfo()

        binding.btnEditInfo.setOnClickListener { edit() }
        binding.btnNewUser.setOnClickListener { newUser() }
    }

    private fun newUser() {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("InfoSharedPreference", Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }

    private fun edit() {
        finish()
    }

    @SuppressLint("SetTextI18n") /// .... این رو لطفا توضیح بدین
    private fun setInfo() {
        binding.txvName.text =getString(R.string.name_)+ name
        binding.txvAddress.text = getString(R.string.adress_)+address
        binding.txvBornLocation.text = getString(R.string.bornL)+bornLocation
        binding.txvGender.text = getString(R.string.gender_)+gender
        binding.txvPostCode.text = getString(R.string.postCode_)+postCode
        binding.txvNationalCode.text = getString(R.string.nationalCode)+nationalCode
    }

    private fun getFromShared(field:String): String? {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("InfoSharedPreference", Context.MODE_PRIVATE)
        return sharedPreferences.getString(field,"")
    }
}