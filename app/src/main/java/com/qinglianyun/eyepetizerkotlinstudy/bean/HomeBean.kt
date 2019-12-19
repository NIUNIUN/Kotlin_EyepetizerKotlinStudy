package com.tt.lvruheng.eyepetizer.mvp.model.bean

import java.io.Serializable

data class HomeBean(
    var nextPageUrl: String?, var nextPublishTime: Long,
    var newestIssueType: String?, var dialog: Any?,
    var issueList: List<IssueListBean>?
) {

    data class IssueListBean(
        var releaseTime: Long, var type: String?,
        var date: Long, val total: Int, var publishTime: Long, var count: Int,
        var itemList: List<ItemListBean>?, val nextPageUrl: String
    ) {

        data class ItemListBean(var type: String?, var data: DataBean?, var tag: String?) {
            data class DataBean(
                var dataType: String?,
                var id: Int,
                var title: String?,
                var description: String?,
                var image: String?,
                var actionUrl: String?,
                var adTrack: Any?,
                var isShade: Boolean,
                var label: Any?,
                var labelList: Any?,
                var header: Header,
                var category: String?,
                var duration: Long?,
                var playUrl: String,
                var cover: CoverBean?,
                var author: AuthorBean?,
                var releaseTime: Long?,
                var consumption: ConsumptionBean?,
                val tags: ArrayList<Tag>,
                val itemList: ArrayList<ItemListBean>
            ) : Serializable {
                data class Tag(
                    val id: Int,
                    val name: String,
                    val actionUrl: String,
                    val adTrack: Any
                ) : Serializable

                data class CoverBean(
                    var feed: String?, var detail: String?,
                    var blurred: String?, var sharing: String?, var homepage: String?
                )

                data class ConsumptionBean(
                    var collectionCount: Int,
                    var shareCount: Int,
                    var replyCount: Int
                )

                data class AuthorBean(var icon: String)

                data class Header(
                    val id: Int,
                    val icon: String,
                    val iconType: String,
                    val description: String,
                    val title: String,
                    val font: String,
                    val cover: String,
                    val label: Label,
                    val actionUrl: String,
                    val subtitle: String,
                    val labelList: ArrayList<Label>
                ) :
                    Serializable {
                    data class Label(
                        val text: String,
                        val card: String,
                        val detial: Any,
                        val actionUrl: Any
                    )
                }
            }
        }
    }
}

fun HomeBean.IssueListBean.ItemListBean.coverToVideoBean(): VideoBean {
    var videoBean = data?.run {
        var feed = cover?.feed as String
        var blurred = cover?.blurred
        var shared = consumption?.shareCount
        var reply = consumption?.replyCount
        var collect = consumption?.collectionCount
        VideoBean(
            feed,
            title,
            description,
            duration,
            playUrl,
            category,
            blurred,
            collect,
            shared,
            reply,
            System.currentTimeMillis()
        )
    }
    return videoBean!!
}

// 扩展属性
var HomeBean.IssueListBean.ItemListBean.isText:String
    get() {
       return  this.type!!
    }
   set(value) {
       this.type = value
   }



