package com.example.applanguagepgm
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.applanguagepgm.databinding.ActivityMainBinding
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainActivity : AppCompatActivity(),OnClickListener {
    private lateinit var mBinding: ActivityMainBinding
    private  lateinit var  mAdapter:LanguageAdapter
    private lateinit var  mGridLayoutManager: GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)


        mBinding.btnSave.setOnClickListener {
            val listLanguage= LanguageEntity(nameLanguage= mBinding.etName.text.toString())
            Thread{LanguageApplication.database.languageDao().addLanguage(listLanguage)}.start()
            mAdapter.addLanguage(listLanguage)
        }

        initRecyclerView()
        mBinding.fab.setOnClickListener { launchEditFragment() }
    }

    private fun launchEditFragment(){
        val fragment=EditLangFragment()
        val fragmentManager=supportFragmentManager
        val fragmentTransaction=fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.contreinerMain,fragment)
        fragmentTransaction.addToBackStack(null)// Me permite regresar al main activity
        fragmentTransaction.commit()
    }

    private fun initRecyclerView() {
        mAdapter=LanguageAdapter(mutableListOf(),this)
        this.getListLanguage()
        mGridLayoutManager=GridLayoutManager(this,1)
        mBinding.recyclerView.apply{
            setHasFixedSize(true)
            layoutManager=mGridLayoutManager
            adapter=mAdapter
        }
    }


    private fun getListLanguage() {
        doAsync { val languageList=LanguageApplication.database.languageDao().getListLanguage()
        uiThread { mAdapter.setListLanguage(languageList) }
        }
    }

    override fun onFavoriteLanguage(languageEntity: LanguageEntity) {
        languageEntity.isFavorite=! languageEntity.isFavorite
        doAsync {
            LanguageApplication.database.languageDao().updateLanguage(languageEntity)
            uiThread { mAdapter.updateLanguage(languageEntity) }
        }
    }

    override fun onDeleteLang(languageEntity: LanguageEntity) {
        doAsync {
             LanguageApplication.database.languageDao().deleteLanguage(languageEntity)
            uiThread { mAdapter.deleteLang(languageEntity) }
        }
    }


}