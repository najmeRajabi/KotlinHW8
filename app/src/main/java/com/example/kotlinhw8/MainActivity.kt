package com.example.kotlinhw8

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
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

        initView()
        checkInfoExist()


        binding.btnSaveInfo.setOnClickListener {
            if(checkInfo()){
                initFiled()
                saveInfo()
                gotoShowInfoActivity()
            }
        }
    }

    private fun initView() {
        binding.edtName.setHint(addStarToHint(getString(R.string.nameText)))
        binding.edtAddress.setHint(addStarToHint(getString(R.string.address)))
        binding.edtBornLocation.setHint(addStarToHint(getString(R.string.bornLocation)))
        binding.edtNationalCode.setHint(addStarToHint(getString(R.string.nationalCode_Text)))
        binding.edtPostCode.setHint(addStarToHint(getString(R.string.postCode)))
        binding.genderTxv.setText(addStarToHint(getString(R.string.gender)))
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
            if (setInfoInEdt(GENDER)== Gender.زن .toString()){
                binding.femaleCheckBtn.isChecked = true
            }else{
                binding.maleCheckBtn.isChecked = true
            }

            gotoShowInfoActivity()
        }
    }

    private fun setInfoInEdt(field:String):String? {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("InfoSharedPreference", Context.MODE_PRIVATE)
        return sharedPreferences.getString(field,"")
    }

    private fun addStarToHint(hint:String): SpannableStringBuilder {
        var star = getString(R.string.redStar)
        val builder = SpannableStringBuilder(hint+star)
        builder.setSpan(ForegroundColorSpan(Color.RED),hint.length,
            builder.length,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        return builder

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