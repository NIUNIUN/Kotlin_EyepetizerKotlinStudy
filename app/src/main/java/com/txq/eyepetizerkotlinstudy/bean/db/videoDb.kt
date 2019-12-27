package com.txq.eyepetizerkotlinstudy.bean.db

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.*
import android.arch.persistence.room.migration.Migration
import android.content.Context
import com.txq.eyepetizerkotlinstudy.BuildConfig
import com.tt.lvruheng.eyepetizer.mvp.model.bean.VideoBean
import io.reactivex.Observable
import io.reactivex.internal.operators.observable.ObservableAll
import java.util.concurrent.Callable

/**
 * Created by tang_xqing on 2019/12/4.
 */

@Entity(tableName = "video")
data class VideoTb(
    @PrimaryKey val vid: String,
    @SuppressWarnings
    @Embedded val videoBean: VideoBean,
    @ColumnInfo val filePath: String
)

@Dao
interface VideoDao {
    @Insert
    fun addVideo(video: VideoTb)

    @Delete
    fun deleteVideo(video: VideoTb)

    @Query("SELECT * FROM video")
    fun getAllVideo(): List<VideoTb>

    @Query("SELECT * FROM video where vid =:url")
    fun getVideoByUrl(url: String): VideoTb

    @Update
    fun updateVideo(video: VideoTb)
}

/**
 * 用于升级数据库。
 * 内部编写sql语句
 */
private class MIGRATION1_2 : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
//        database.query()
    }
}


@Database(entities = arrayOf(VideoTb::class), version = 1)
abstract class VideoDatabase : RoomDatabase() {

    abstract fun videoDao(): VideoDao

    companion object {
        private const val DB_NAME = "video_db.db"
        private var INSTANCE: VideoDatabase? = null
        fun getInstance(context: Context): VideoDatabase {
            if (null == INSTANCE) {
                synchronized(VideoDatabase::class.java) {
                    if (null == INSTANCE) {
                        var builder = Room.databaseBuilder(
                            context.applicationContext, VideoDatabase::class.java,
                            DB_NAME
                        )
//                            .allowMainThreadQueries()     // 允许在主线程中执行。Room默认是不允许在主线程执行操作
//                            .addMigrations(MIGRATION1_2())  // 升级数据库

                        if (!BuildConfig.DEBUG) {
                            builder.fallbackToDestructiveMigration()
                        }
                        INSTANCE = builder.build()
                    }
                }
            }
            return INSTANCE!!
        }
    }
}

/**
 * Room数据库操作不能在子线程中执行。Room支持RxJava2，对insert、delete、update操作进行封装以便支持RxJava
 */
class VideoDbManager private constructor() {
    private lateinit var mContext: Context

    companion object {
        private val MANAGER: VideoDbManager = VideoDbManager()
        fun getInstance(context: Context): VideoDbManager {
            MANAGER.init(context)
            return MANAGER
        }
    }

    private fun init(ctx: Context) {
        mContext = ctx.applicationContext
    }

    fun addVideo(video: VideoTb): Observable<Boolean> {
        return Observable.fromCallable(object : Callable<Boolean> {
            override fun call(): Boolean {
                println("addVideo 保存数据库")
                VideoDatabase.getInstance(mContext).videoDao().addVideo(video)
                return true
            }
        })

//       return Observable.create(object :ObservableOnSubscribe<Boolean>{
//            override fun subscribe(e: ObservableEmitter<Boolean>?) {
//                e.onNext(true)
//            }
//        })
    }

    fun getAllvideo(): Observable<List<VideoTb>> {
        return ObservableAll.fromCallable(object : Callable<List<VideoTb>> {
            override fun call(): List<VideoTb> {
                return VideoDatabase.getInstance(mContext).videoDao().getAllVideo()
            }
        })
    }

    fun getVideoByUri(uri: String): Observable<VideoTb> {
        return Observable.fromCallable(object : Callable<VideoTb> {
            override fun call(): VideoTb {
                return VideoDatabase.getInstance(mContext).videoDao().getVideoByUrl(uri)
            }
        })
    }

    fun updateVideo(video: VideoTb): Observable<Boolean> {
        return Observable.fromCallable(object : Callable<Boolean> {
            override fun call(): Boolean {
                VideoDatabase.getInstance(mContext).videoDao().updateVideo(video)
                return true
            }
        })
    }

    fun deleteVideo(video: VideoTb): Observable<Boolean> {
        return Observable.fromCallable(object : Callable<Boolean> {
            override fun call(): Boolean {
                VideoDatabase.getInstance(mContext).videoDao().deleteVideo(video)
                return true
            }
        })
    }
}