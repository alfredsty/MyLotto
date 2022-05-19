package com.example.mylotto

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.mylotto.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var idx = -1
    private var listLotto = arrayOfNulls<String>(30)
    private var lastSaved:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()

        binding.btnNew.setOnClickListener{
            if(binding.cbIsSame.isChecked){
                getLottoNumber1()
            } else{
                getLottoNumber2()
            }
            binding.btnSave.isEnabled = true

        }
        binding.btnSave.setOnClickListener{
            val lotto = IntArray(6)
            lotto[0] = binding.tvLotto1.text.toString().toInt()
            lotto[1] = binding.tvLotto2.text.toString().toInt()
            lotto[2] = binding.tvLotto3.text.toString().toInt()
            lotto[3] = binding.tvLotto4.text.toString().toInt()
            lotto[4] = binding.tvLotto5.text.toString().toInt()
            lotto[5] = binding.tvLotto6.text.toString().toInt()

            var strListLotto = ""
            for(i in lotto.indices){
                strListLotto += lotto[i].toString()
                if(i != lotto.size -1) strListLotto += ","
            }


            if(strListLotto.equals(lastSaved)){ // 방금 저장한 번호 일 경우
                Toast.makeText(this,"이미 저장했음", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            idx++
            if(idx == 30){ // 저장공간이 꽉 찼을 경우
                Toast.makeText(this, "저장공간이 없음" ,Toast.LENGTH_SHORT).show()
                idx--
                return@setOnClickListener
            }
            listLotto[idx] =strListLotto
            lastSaved = strListLotto
            Toast.makeText(this,"저장완료!!",Toast.LENGTH_SHORT).show()
            Log.d("디버깅",strListLotto)

            binding.btnShowList.isEnabled = true

            savePref()
        }



        binding.btnShowList.setOnClickListener{
            var strListLotto = ""
            for(i in 0..idx){
                strListLotto += listLotto[i]
                if(i != idx) strListLotto += ","
            }
            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("strListLotto",strListLotto)
            startActivity(intent)
        }
    }

    private fun savePref() {

        var strListLotto:String = ""
        for(i in 0..idx){
            strListLotto += listLotto[i]
            if(i != idx) strListLotto += "|"
        }
        val sp = getSharedPreferences(KEY_PREF,Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putInt(KEY_INDEX,idx)
        editor.putString(KEY_LASTSAVED,lastSaved)
        editor.putString(KEY_LOTTO,strListLotto)
        editor.apply()

    }
    private fun loadPref(){
        val sp = getSharedPreferences(KEY_PREF,Context.MODE_PRIVATE)
        if(sp.contains(KEY_INDEX)){
            idx= sp.getInt(KEY_INDEX,-1)
            lastSaved = sp.getString(KEY_LASTSAVED,null)
            val strListlotto: String? = sp.getString(KEY_LOTTO, null)
            val arrListLotto: List<String>? = strListlotto?.split("|")
            for(i in 0..idx){
                listLotto[i] = arrListLotto?.get(i)
            }
            binding.btnSave.isEnabled = true
        }
    }

    private fun initViews(){
        binding.btnSave.isEnabled = false
        binding.btnShowList.isEnabled = false
        binding.tvLotto1.setBackgroundResource(R.drawable.circle_white)
        binding.tvLotto2.setBackgroundResource(R.drawable.circle_white)
        binding.tvLotto3.setBackgroundResource(R.drawable.circle_white)
        binding.tvLotto4.setBackgroundResource(R.drawable.circle_white)
        binding.tvLotto5.setBackgroundResource(R.drawable.circle_white)
        binding.tvLotto6.setBackgroundResource(R.drawable.circle_white)
    }

    private fun getLottoNumber1(){
        val rnd = Random()
        val lotto = IntArray(6)
        //for(i in 0 until 6){
        for(i in lotto.indices){
            lotto[i] = rnd.nextInt(45)+1
        }
        setTvNumber(lotto[0],binding.tvLotto1)
        setTvNumber(lotto[1],binding.tvLotto2)
        setTvNumber(lotto[2],binding.tvLotto3)
        setTvNumber(lotto[3],binding.tvLotto4)
        setTvNumber(lotto[4],binding.tvLotto5)
        setTvNumber(lotto[5],binding.tvLotto6)
    }

    private fun setTvNumber(i : Int, tv: TextView){
        tv.text = i.toString()
        when(i){
            in 1..10 -> {tv.background = ContextCompat.getDrawable(this,R.drawable.circle_red)}
            in 11..20 -> {tv.background = ContextCompat.getDrawable(this,R.drawable.circle_green)}
            in 21..30 -> {tv.background = ContextCompat.getDrawable(this,R.drawable.circle_blue)}
            in 31..40 -> {tv.background = ContextCompat.getDrawable(this,R.drawable.circle_mint)}
            else -> {tv.background = ContextCompat.getDrawable(this,R.drawable.circle_purple)}
        }
    }

    private fun getLottoNumber2(){
        val rnd = Random()
        val lotto = IntArray(6)
        while(true){
            var isSame = false
            for(i in lotto.indices){
                lotto[i] = rnd.nextInt(45)+1
            }
            for(i in lotto.indices){
                for(j in lotto.indices){
                    if(i != j){
                        if(lotto[i] == lotto[j]){
                            isSame = true
                        }
                    }
                }
            }
        //   if(isSame == false)
            if(!isSame) {
                break
            }
        }
        setTvNumber(lotto[0],binding.tvLotto1)
        setTvNumber(lotto[1],binding.tvLotto2)
        setTvNumber(lotto[2],binding.tvLotto3)
        setTvNumber(lotto[3],binding.tvLotto4)
        setTvNumber(lotto[4],binding.tvLotto5)
        setTvNumber(lotto[5],binding.tvLotto6)
    }
    companion object {
        private const val KEY_PREF = "lotto_pref"
        private const val KEY_LOTTO = "lotto_list"
        private const val KEY_INDEX = "list_index"
        private const val KEY_LASTSAVED = "last_saved_value"


    }
}





//class MainActivity : AppCompatActivity() {
//    private val tv_lotto_1: TextView by lazy {
//        findViewById<TextView>(R.id.tv_lotto_1)
//    }
//    private val tv_lotto_2: TextView by lazy {
//        findViewById<TextView>(R.id.tv_lotto_2)
//    }
//    private val tv_lotto_3: TextView by lazy {
//        findViewById<TextView>(R.id.tv_lotto_3)
//    }
//    private val tv_lotto_4: TextView by lazy {
//        findViewById<TextView>(R.id.tv_lotto_4)
//    }
//    private val tv_lotto_5: TextView by lazy {
//        findViewById<TextView>(R.id.tv_lotto_5)
//    }
//    private val tv_lotto_6: TextView by lazy {
//        findViewById<TextView>(R.id.tv_lotto_6)
//    }
//    private val  cb_is_same: CheckBox by lazy{
//        findViewById<CheckBox>(R.id.cb_is_same)
//    }
//    private val btn_new: Button by lazy {
//        findViewById<Button>(R.id.btn_new)
//    }
//    private val btn_save: Button by lazy {
//        findViewById<Button>(R.id.btn_save)
//    }
//    private val btn_show_list: Button by lazy {
//        findViewById<Button>(R.id.btn_show_list)
//    }
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        btn_show_list.setOnClickListener {
//            val intent = Intent(this, ResultActivity::class.java)
//            startActivity(intent)
//        }
//    }
//}




























