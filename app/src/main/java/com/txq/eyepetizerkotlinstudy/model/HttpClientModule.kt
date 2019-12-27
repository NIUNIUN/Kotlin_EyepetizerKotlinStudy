package com.txq.eyepetizerkotlinstudy.model

import com.txq.eyepetizerkotlinstudy.view.FoundFragment
import com.txq.eyepetizerkotlinstudy.view.HomeFragment
import com.txq.eyepetizerkotlinstudy.view.HotFragment
import com.txq.eyepetizerkotlinstudy.view.PersonalFragment
import org.kodein.di.Kodein
import org.kodein.di.generic.*

/**
 * Created by tang_xqing on 2019/12/25.
 */
/**
 * 将对应的依赖装入Kodein容器
 * 依赖所需的model，固定模式一个TAG，一个model
 */

/**
 * bind<T> 将T依赖装入Kodein容器
 */
//val FOUND_FRAGMENT_MODEL_TAG = "found_fragment_model"
//val foundFragmentModel = Kodein.Module(FOUND_FRAGMENT_MODEL_TAG) {
//    bind<FoundFragment>() with provider { FoundFragment.getInstance() }
//}
//
//val homeFragmentModel = Kodein.Module(FOUND_FRAGMENT_MODEL_TAG) {
//    // scoped 保证Activity层单例 AndroidLifecycleScope和AndroidComponentsWeakScope有啥区别？
//    bind<HomeFragment>() with scoped(AndroidLifecycleScope).singleton {
//        HomeFragment.getInstance()
//
//        // HomeFragment 用到Activity的Context对象
////        instance<MainActivity>().homeFragment()
////        instance<MainActivity>().homeFragment
//    }
//}

const val MAIN_MODEL_TAG  = "main_fragment_module"
val mainModule = Kodein.Module(MAIN_MODEL_TAG){
    bind<HomeFragment>() with provider { HomeFragment.getInstance() }
    bind<FoundFragment>() with provider { FoundFragment.getInstance() }
    bind<HotFragment>() with  provider { HotFragment.getInstance() }
    bind<PersonalFragment>() with provider { PersonalFragment.getInstance() }


    // 通过TAG来区分不同的依赖。例如：本地数据库、远程数据库。  使用时也通过TAG来区分。
    bind<PersonalFragment>(tag = "单例") with singleton { PersonalFragment.getInstance() }
}

