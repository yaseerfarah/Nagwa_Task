package com.yasser.nagwa.Data

import android.content.Context
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.yasser.nagwa.Model.DataItemModel
import io.reactivex.Single
import org.json.JSONArray
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset


class Repostitory(
    val context: Context
) {



    fun getItemsData():Single<List<DataItemModel>>{

       return Single.create<List<DataItemModel>> {
            try {
                var list= mutableListOf<DataItemModel>()
                 var m_jArry:JSONArray= JSONArray(loadJSONFromAsset())
                val gson = GsonBuilder().create()
                for(i in 0..m_jArry.length()-1){
                    var dataItem=gson.fromJson(JsonParser().parse(m_jArry.getJSONObject(i).toString()),DataItemModel::class.java)
                    list.add(dataItem)
                }
                if (!it.isDisposed){
                    it.onSuccess(list)
                }

            }catch (e:Exception){
                if (!it.isDisposed){
                    it.onError(e)
                }
            }
        }


    }




    @Throws(IOException::class)
    fun loadJSONFromAsset(): String?  {
        var json: String? = null

        val inFile: InputStream = context.assets.open("response.json")
        val size: Int = inFile.available()
        val buffer = ByteArray(size)
        inFile.read(buffer)
        inFile.close()
        val charset: Charset = Charsets.UTF_8
        json = String(buffer, charset)
        return json
    }





}