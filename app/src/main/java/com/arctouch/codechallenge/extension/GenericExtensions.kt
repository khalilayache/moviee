package com.arctouch.codechallenge.extension

import android.content.Context
import android.widget.Toast

fun Toast.showToast(context: Context, message:String, duration: Int){
  this.cancel()
  Toast.makeText(context,message,duration).show()
}
