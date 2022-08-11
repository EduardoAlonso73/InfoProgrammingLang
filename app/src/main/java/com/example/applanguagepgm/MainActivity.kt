package com.example.applanguagepgm
import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.applanguagepgm.databinding.ActivityMainBinding
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainActivity : AppCompatActivity(),OnClickListener,MainAux {
    private lateinit var mBinding: ActivityMainBinding
    private  lateinit var  mAdapter:LanguageAdapter
    private lateinit var  mGridLayoutManager: GridLayoutManager
    private  var isView: Boolean=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)



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
    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
              ----------- Menu action ------------
      -*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-* */

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_option, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_view -> {
                isView=true
                Toast.makeText(this,"Hola View ",Toast.LENGTH_SHORT).show()
                mBinding.fab.hide()
            }
            R.id.action_edit-> {
                isView=false
                Toast.makeText(this,"Hola Edit",Toast.LENGTH_SHORT).show()
                mBinding.fab.show()

            }
        }
        return super.onOptionsItemSelected(item)
    }
    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
      ----Function the inteface OnClinckListener ------
    -*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-* */

    override fun onClick(language: LanguageEntity) {

        when (isView){
            true ->{
                Toast.makeText(this,"View '👀 ",Toast.LENGTH_SHORT).show()
            }
            else ->{
                Toast.makeText(this,"Edit ✍",Toast.LENGTH_SHORT).show()
            }
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


    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
          ---------- Function MainAux ----------
  -*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-* */


    override fun addLanguage(languageEntity: LanguageEntity) {
        mAdapter.addLanguage(languageEntity)
    }


}