package com.example.applanguagepgm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.applanguagepgm.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(),OnClickListener {
    private lateinit var mBinding: ActivityMainBinding
    private  lateinit var  mAdapter:LanguageAdapter
    private lateinit var  mGridLayoutManager: GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)


        mBinding.btnSave.setOnClickListener {
            val listLanguage= Language(nameLanguage=mBinding.etName.text.toString().toString())
            mAdapter.addLanguage(listLanguage)
        }

        initRecyclerView()
    }

    private fun initRecyclerView() {
        mAdapter=LanguageAdapter(mutableListOf(),this)
        mGridLayoutManager=GridLayoutManager(this,1)
        mBinding.recyclerView.apply{
            setHasFixedSize(true)
            layoutManager=mGridLayoutManager
            adapter=mAdapter
        }
    }
    
}