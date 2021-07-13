package com.alome.mvp.Misc

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import com.alome.mvp.Activities.MovieDetails
import com.alome.mvp.R
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class Utils {

    companion object{
         fun setStatusBarTranslucent(makeTranslucent: Boolean = true, ctx:Activity) {
            if (makeTranslucent) {
                ctx.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            } else {
                ctx.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            }
        }
        fun subscribeOnBackground(function: () -> Unit) {
            Single.fromCallable {
                function()
            }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        }

         fun showNoInternet(context: Context, show:Boolean) :Dialog {
            val dialog = Dialog(context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.no_internet)
             if (show){
                 dialog.show()
             }  else {
                 dialog.dismiss()
             }
             return dialog
        }


    }

}