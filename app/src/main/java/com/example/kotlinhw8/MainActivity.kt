package com.example.kotlinhw8

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.text.isDigitsOnly
import com.example.kotlinhw8.databinding.ActivityMainBinding

const val NAME="name"
const val ADDRESS="address"
const val BORNLOCATION="bornLocation"
const val NATIONALCODE="nationalCode"
const val POSTCODE="postcode"
const val GENDER="gender"

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var name :String = ""
    var nationalCode :String = ""
    var address :String = ""
    var bornLocation :String = ""
    var postCode :String = ""
    lateinit var gender:Gender

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        checkInfoExist()
        addStarToHint()
        binding.btnSaveInfo.setOnClickListener {
            if(checkInfo()){
                initFiled()
                saveInfo()
                gotoShowInfoActivity()
            }
        }
    }

    private fun checkInfoExist() {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("InfoSharedPreference", Context.MODE_PRIVATE)
        if (!sharedPreferences.getString(NAME,"").isNullOrBlank()){
            binding.edtName.setText(setInfoInEdt(NAME))
            binding.edtAddress.setText(setInfoInEdt(ADDRESS))
            binding.edtBornLocation.setText(setInfoInEdt(BORNLOCATION))
            binding.edtNationalCode.setText(setInfoInEdt(NATIONALCODE))
            binding.edtPostCode.setText(setInfoInEdt(POSTCODE))
            gotoShowInfoActivity()
        }
    }

    private fun setInfoInEdt(field:String):String? {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("InfoSharedPreference", Context.MODE_PRIVATE)
        return sharedPreferences.getString(field,"")
    }

    private fun addStarToHint() {
        var star = getString(R.string.redStar)

//        binding.edtName.currentHintTextColor = getColor(R.color.red)
    }

    private fun initFiled() {
        name = binding.edtName.text.toString()
        bornLocation = binding.edtBornLocation.text.toString()
        address = binding.edtAddress.text.toString()
        nationalCode= binding.edtNationalCode.text.toString()
        postCode= binding.edtPostCode.text.toString()

        if (binding.femaleCheckBtn.isChecked){
            gender = Gender.زن
        }else if (binding.maleCheckBtn.isChecked){
            gender= Gender.مرد
        }
    }

    private fun saveInfo() {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("InfoSharedPreference", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(NAME , name)
        editor.putString(ADDRESS , address)
        editor.putString(BORNLOCATION , bornLocation)
        editor.putString(NATIONALCODE , nationalCode)
        editor.putString(POSTCODE , postCode)
        editor.putString(GENDER , gender.toString())
        editor.apply()
    }

    private fun gotoShowInfoActivity() {
        val intent = Intent(this,ShowInfoActivity::class.java)
        startActivity(intent)
    }

    private fun checkInfo(): Boolean {
        var result = false
        val name = binding.edtName.text
        val nationalCode = binding.edtNationalCode.text
        if (name.isNullOrBlank()){
            binding.edtName.error = getString(R.string.enter_name)
        }
        else if (nationalCode.isNullOrBlank() ||
                nationalCode?.trim()?.length != 10 ||
                !nationalCode.isDigitsOnly()){
            binding.edtNationalCode.error = getString(R.string.enterNationalCode)
        }
        else if (binding.edtBornLocation.text.isNullOrBlank()){
            binding.edtBornLocation.error = getString(R.string.enterBornLocation)
        }
        else if (binding.edtAddress.text.isNullOrBlank()){
            binding.edtAddress.error = getString(R.string.enterAddress)
        }
        else if (binding.edtPostCode.text.isNullOrBlank() ||
                !binding.edtPostCode.text?.isDigitsOnly()!!){
            binding.edtPostCode.error = getString(R.string.enterPostCode)
        }else if (!binding.femaleCheckBtn.isChecked &&
                    !binding.maleCheckBtn.isChecked){
            binding.genderTxv.error =getString(R.string.chooseGender)
        }
        else{
            result = true
        }
        return result
    }
}

enum class Gender{
    زن , مرد
}