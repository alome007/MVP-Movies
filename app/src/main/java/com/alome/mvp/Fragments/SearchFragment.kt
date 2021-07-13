package com.alome.mvp.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.alome.mvp.Adapters.SearchAdapter
import com.alome.mvp.Misc.Loader
import com.alome.mvp.Model.Movies
import com.alome.mvp.R
import com.alome.mvp.ViewModel.MainActivityViewModel
import com.alome.mvp.ViewModel.SearchViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import java.util.ArrayList
import android.view.inputmethod.EditorInfo

import android.widget.TextView.OnEditorActionListener
import com.google.android.material.bottomsheet.BottomSheetDialog

import android.content.DialogInterface

import android.app.Dialog
import android.content.Context
import com.google.android.material.bottomsheet.BottomSheetBehavior

import android.content.DialogInterface.OnShowListener
import android.content.SharedPreferences
import android.view.*
import android.widget.*

import androidx.annotation.Nullable
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import com.alome.mvp.Misc.Constants
import com.alome.mvp.Misc.Constants.Companion.BLACKLISTED_MOVIES
import com.alome.mvp.Misc.Constants.Companion.MOVIE
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class SearchFragment: BottomSheetDialogFragment() {
   lateinit var  container:View
   lateinit var search_box:EditText
   lateinit var noResult:TextView
   lateinit var recyclerView: RecyclerView
   lateinit var adapter:SearchAdapter
   lateinit var close:ImageView
   lateinit var mainContainer:NestedScrollView
    var movies = ArrayList<Movies>()

    override fun onCreateView(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        container = inflater.inflate(R.layout.search_fragment, parent, false)
        initUI();

        //handle click listener on Keyboard search icon
        search_box.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                initViewModel()
                true
            } else false
        })

        close.setOnClickListener{
            dismiss()
        }
        return container;
    }

    @SuppressLint("UseRequireInsteadOfGet")
    private fun initUI() {
        search_box = container.findViewById(com.alome.mvp.R.id.search_box)
        recyclerView = container.findViewById(com.alome.mvp.R.id.search_recycler_view)
        adapter = SearchAdapter(context!!, movies)
        recyclerView.layoutManager= LinearLayoutManager(context,RecyclerView.VERTICAL, false)
        recyclerView.adapter = adapter
        close = container.findViewById(R.id.close)
        mainContainer = container.findViewById(R.id.container)
        noResult = container.findViewById(R.id.no_result)
    }


    private fun initViewModel() {
        noResult.visibility = View.GONE
        val loader = Loader()
        loader.isCancelable=false
        loader.show(requireFragmentManager(), "loading")
        val viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        viewModel.getResultObserver().observe(this,  {
            loader.dismiss()
            if (it!=null){
                if(it.results.size==0){

                    Toast.makeText(context, getString(R.string.no_item_returned), Toast.LENGTH_LONG).show()
                    noResult.visibility = View.VISIBLE
                }
                val movie = checkBlackListed(MOVIE);
                if (it.results.contains(movie)){
                    //remove from list so it does not show up
                    it.results.remove(movie)
                }
                noResult.visibility = View.GONE
                adapter.setupData(it.results)

            }   else {
                // Result is null
                Snackbar.make(mainContainer, getString(R.string.empty_result), Snackbar.LENGTH_LONG).show()

            }
        })
        viewModel.makeAPICall(search_box.text.toString())
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        dialog.setOnShowListener {

            val bottomSheetDialog = it as BottomSheetDialog
            val parentLayout =
                bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            parentLayout?.let { it ->
                val behaviour = BottomSheetBehavior.from(it)
                setupFullHeight(it)
                behaviour.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        return dialog
    }

    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
    }

    @SuppressLint("UseRequireInsteadOfGet")
    fun checkBlackListed(key: String?): Movies? {
        val prefs = context!!.getSharedPreferences(BLACKLISTED_MOVIES, Context.MODE_PRIVATE)
        val gson = Gson()
        val json = prefs.getString(key, "")
        val type: Type = object : TypeToken<Movies?>() {}.type
        return gson.fromJson(json, type)
    }

}