package com.alome.mvp.Misc

import android.app.Activity
import android.view.WindowManager
import com.alome.mvp.Activities.MovieDetails

class Utils {

    companion object{
         fun setStatusBarTranslucent(makeTranslucent: Boolean = true, ctx:Activity) {
            if (makeTranslucent) {
                ctx.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            } else {
                ctx.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            }
        }
    }

}