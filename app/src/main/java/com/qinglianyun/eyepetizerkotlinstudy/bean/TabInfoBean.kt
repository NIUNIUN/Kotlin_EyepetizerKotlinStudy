package com.qinglianyun.eyepetizerkotlinstudy.bean

/**
 * Created by tang_xqing on 2019/11/26.
 */
data class TabInfoBean(val tabInfo: TabInfo) {
    data class TabInfo(val tabList: ArrayList<Tab>)

    data class Tab(val id: Long, val name: String, val apiUrl: String)
}