package com.example.kotlinhw8

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.text.isDigitsOnly
import com.example.kotlinhw8.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnSaveInfo.setOnClickListener {
            if(checkInfo()){
                gotoShoInfoActivity()
            }
        }
    }

    private fun gotoShoInfoActivity() {
        var intent = Intent(this,ShowInfoActivity::class.java)
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