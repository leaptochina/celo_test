package com.pine.celo

import com.blueberrysolution.pinelib19.activity.BaseApp
import com.blueberrysolution.pinelib19.net.retrofit.RetrofitManager
import com.blueberrysolution.pinelib19.sqlite.mylib.BbsSqlite


class App : BaseApp() {

    var allowAd = true;
    var sendAnswerForStatistics = true;
    override var isDebug = false;
    override var releaseSignature = "0e07a3e5c0f2270447eb14c71665bf40";


    var howLongShouldTopicValid = 60;
    lateinit var sqlite: BbsSqlite;
    override var googleTransalteProjectId: String = "drivingtestandro-1570664192412"
    lateinit var database: DatabaseReference;
    lateinit var retrofitManager: RetrofitManager<RetrofitServices>

    override fun onLiveInit(){
        super.onLiveInit()

        Config.baseUrl =  "http://drivingtest.blueberrysolution.co.nz";
        Config.set();

        retrofitManager = RetrofitManager<RetrofitServices>(Config.baseUrl, RetrofitServices::class)
    }

    override fun onDebugInit() {
        super.onDebugInit()

        Config.baseUrl = "http://192.168.31.42";
        Config.set();

        retrofitManager = RetrofitManager<RetrofitServices>(Config.baseUrl, RetrofitServices::class)
    }

    override fun onCreate() {
        super.onCreate()
        App.app = this;

        //initDatabase0();
        A.n(PicassoHelper())



        if (isDebug){
            howLongShouldTopicValid = 60;
        }
        else{
            howLongShouldTopicValid = 24 * 3600;
        }

    }

    fun initDatabase0(){
        database = FirebaseDatabase.getInstance().reference
    }

    fun A.fdb():DatabaseReference {
        return database;
    }


    fun initDatabase() {
        val assistFile = AssistFile()
        val isFirstInit = assistFile.initDatabase("driving_test")

        sqlite = BbsSqlite("driving_test.db")

        if (isFirstInit){

            M(QuestionLists::class).createColumn("is_error", Int::class, "0");
            M(QuestionLists::class).createColumn("is_done", Int::class, "0");
            M(QuestionLists::class).createColumn("is_fav", Int::class, "0");
            M(QuestionLists::class).createColumn("this_status", Int::class, "0");

            M(Users::class).createColumn("valid_until", Int::class, "0");
            M(Explains::class).createColumn("i_like", Int::class, "0");



        }

    }

    companion object{
        var app: App? = null;
    }

}