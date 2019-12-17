package com.blueberrysolution.pinelib19.sqlite.utils


import java.io.BufferedReader
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

import android.content.res.AssetManager
import com.blueberrysolution.pinelib19.activity.A
import com.blueberrysolution.pinelib19.addone.share_preferences.Sp
import com.blueberrysolution.pinelib19.debug.G

class AssistFile {
    /**
     * 初始化本地数据库 返回是否是第一次初始化
     */
    fun initDatabase(ASSETS_DB_NAME: String): Boolean {
        var ASSETS_DB_NAME = ASSETS_DB_NAME
        val shareName = "初始化数据库$ASSETS_DB_NAME"
        val 数据库是否初始化 = Sp.i.get(shareName, false)
        if ((!数据库是否初始化)!!) {
            if (!ASSETS_DB_NAME.endsWith(".db")) {
                ASSETS_DB_NAME = "$ASSETS_DB_NAME.db"
            }

            copy(
                ASSETS_DB_NAME,
                "/data/data/" + A.c().packageName + "/databases/",
                ASSETS_DB_NAME
            )
            Sp.i.put(shareName, true)
            G.i("数据库初始化成功！")
            return true
        }
        return false;
    }

    /**
     * // 将assets目录的文件复制到SD卡指定位置，指定文件名
     *
     * @param ASSETS_NAME
     * @param savePath
     * @param saveName
     */
    fun copy(ASSETS_NAME: String, savePath: String, saveName: String) {
        val filename = "$savePath/$saveName"

        val dir = File(savePath)

        if (!dir.exists()) dir.mkdir()
        try {
            if (!File(filename).exists()) {
                val inputStream = A.c().resources.assets
                    .open(ASSETS_NAME)
                val fos = FileOutputStream(filename)
                val buffer = ByteArray(7168)
                var count = 0
                do {
                    count = inputStream.read(buffer)
                    if (count <= 0) break;
                    fos.write(buffer, 0, count)
                }
                while (true);

                fos.close()
                inputStream.close()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


}
