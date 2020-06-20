package com.example.itunesearch.data.rest


/**
 * Created by Rajat Sangrame on 20/6/20.
 * http://github.com/rajatsangrame
 */
interface ApiCallback {

    fun success()
    fun failure(msg: String?)
}