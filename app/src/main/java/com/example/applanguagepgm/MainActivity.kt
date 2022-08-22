package com.example.applanguagepgm
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.applanguagepgm.databinding.ActivityMainBinding
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

class MainActivity : AppCompatActivity(),OnClickListener,MainAux {
    private lateinit var mBinding: ActivityMainBinding
    private  lateinit var  mAdapter:LanguageAdapter
    private lateinit var  mGridLayoutManager: GridLayoutManager
    private  var isView: Boolean=true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        initRecyclerView()
        mBinding.fab.setOnClickListener { launchEditFragment() }
        mBinding.fab.hide()
    }

    private fun launchEditFragment(args: Bundle?=null) {
        val fragment=EditLangFragment()
        if(args!=null)fragment.arguments=args

        val fragmentManager=supportFragmentManager
        val fragmentTransaction=fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.contreinerMain,fragment)
        fragmentTransaction.addToBackStack(null)// Me permite regresar al main activity
        fragmentTransaction.commit()
    }

    private fun launchViewFragment(args: Bundle?=null) {
        val fragmentView=ViewLangFragment()

        if(args!=null)fragmentView.arguments=args

        val fragmentManager=supportFragmentManager
        val fragmentTransaction=fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.contreinerMain,fragmentView)
        fragmentTransaction.addToBackStack(null)// Me permite regresar al main activity
        fragmentTransaction.commit()
    }

    private fun initRecyclerView() {
        mAdapter=LanguageAdapter(mutableListOf(),this)
        this.getListLanguage()
        mGridLayoutManager=GridLayoutManager(this,resources.getInteger(R.integer.main_column))
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
                mBinding.fab.hide()
            }
            R.id.action_edit-> {
                isView=false
                mBinding.fab.show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
      ----Function the inteface OnClinckListener ------
    -*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-* */

    override fun onClickItem(langId: Long) {

        if(isView){
            val arg=Bundle()
            arg.putLong(getString(R.string.arg_id),langId)
            launchViewFragment(arg)
        } else {
            val arg=Bundle()
            arg.putLong(getString(R.string.arg_id),langId)
            launchEditFragment(arg)
        }


    }

    override fun onFavoriteLanguage(languageEntity: LanguageEntity) {
        languageEntity.isFavorite=! languageEntity.isFavorite
        doAsync {
            LanguageApplication.database.languageDao().updateLanguage(languageEntity)
            uiThread { updateLang(languageEntity) }
        }
    }

    override fun onDeleteLang(languageEntity: LanguageEntity) {
        if(!isView){
            val alertDialogBuilder = AlertDialog.Builder(this)
            with(alertDialogBuilder){
                setTitle("Delete lang")
                setIcon(R.drawable.ic_delete)
                setMessage("Do you want detele ?")
                setCancelable(false)
                setNegativeButton("Cancelar",null)
                setPositiveButton("Delete"){_,i ->
                    doAsync {
                        LanguageApplication.database.languageDao().deleteLanguage(languageEntity)
                        uiThread { mAdapter.deleteLang(languageEntity) }
                    }
                }
            }.also {
                val alertDialog = alertDialogBuilder.create()
                alertDialog.show()
            }

        }
    }


    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
          ---------- Function MainAux ----------
  -*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-* */


    override fun addLanguage(languageEntity: LanguageEntity) {
        mAdapter.addLanguage(languageEntity)
    }

    override fun updateLang(languageEntity: LanguageEntity) {
        mAdapter.updateLanguage(languageEntity)
    }

    override fun startIntent(intent: Intent) {
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            Toast.makeText(this, "No se encontro una app compatible", Toast.LENGTH_SHORT).show()
        }
    }


}