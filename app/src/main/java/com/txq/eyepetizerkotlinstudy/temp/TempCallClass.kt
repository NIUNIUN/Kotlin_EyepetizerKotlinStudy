package com.txq.eyepetizerkotlinstudy.temp

import android.content.Context
import android.util.AttributeSet
import android.widget.EditText
import android.widget.TextView
import com.txq.eyepetizerkotlinstudy.R
import com.tt.lvruheng.eyepetizer.mvp.model.bean.VideoBean
import kotlinx.coroutines.*
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

/** https://blog.csdn.net/u010017719/article/details/90405591
 *
 * Kotlin 什么是幕后字段？
 *     https://juejin.im/post/5b95321ae51d450e6475b7c6#heading-1
 * Created by tang_xqing on 2019/12/2.
 */

/**
 * https://www.imooc.com/article/285493
 * https://kaixue.io/
 *
 * 协程：从执行机制上，协程和回调没有什么区别。
 *     好处：简化代码；更加安全。  可以和Retrofit配合使用。
 *
 */

/**
 * TempCallClass实现SimpleCall接口，新建一个委托类作为委托对象
 */
class TempCallClass : SimpleCall by SimpleBulid() {

    val content: String by lazy {
        "china"
    }

    fun call(ctx: Context) {


        var mCode: Int = -1
        var mss: String by Delege()
        mss = "123423"
        println("委托属性 输出$mss ${Thread.currentThread().name}")

        var list = arrayListOf<String>()
        list.filterNotNull()

        var tempAdapter: TempAdapter =
            TempAdapter(ctx)
        tempAdapter.justPage("ab", "china", "apple", "orige")
        tempAdapter.justA("命名参数", age = 19)

        tempAdapter.setListener {
            onFailFun { code, msg ->
                mCode = code
//                mss = msg
//                println("onFailFun() call with $code $msg")
            }

            onSuccessFun {
                return@onSuccessFun "fsdfe"
            }
        }

        tempAdapter.getItem(1024)

        println("输出   ${fParamen(oneParamete)}")
        println("输出   ${twoParamete.invoke("go-", 3)}")
    }

    /**
     * 不带接收者类型和带接收者类型表达式可以相互转换。 A.(B)->C  (A,B)->C
     *
     * 将接收者作为第一个参数传递，类似扩展参数1.foo(2)，在函数体内部访问接收者对象的成员。
     * 不能理解为什么可以相互转换，查看字节码也不知道为什么可以相互转换？
     *
     */
    var oneParamete: String.(Int) -> String = { other -> this.repeat(other) }
    var twoParamete: (String, Int) -> String =
        oneParamete  // 主要是在这里，将onParamere方法赋值给twoParamete，所以外面调用twoParamete其实是调用oneParamete
    var threeParamete: (String, Int) -> String = { str, oth -> str.repeat(oth) }

    fun fParamen(ff: (String, Int) -> String): String {
        "sfe".oneParamete(3)
        return ff("oneParamete===hello", 3)
    }

    fun getNumber(): Int {
        return 14
    }
}

class SimpleBulid : SimpleCall {
    private lateinit var onSuccessVal: (String) -> String
    private lateinit var onFailVal: (Int, String) -> Unit

    fun onSuccessFun(lister: (String) -> String) {
        onSuccessVal = lister
    }

    fun onFailFun(lister: (Int, String) -> Unit) {
        onFailVal = lister
    }

    override fun onSuccess(result: String) {
        onSuccessVal.invoke(result)
    }

    override fun onFail(code: Int, msg: String) {
        // 调用onFailVal()方法，等价于onFailVal(code,msg)。通过函数变量调用自身
        onFailVal.invoke(code, msg)
    }
}

interface SimpleCall {
    fun onSuccess(result: String)
    fun onFail(code: Int, msg: String)
}

class TempAdapter(ctx: Context) {
    var mCtx: Context
    lateinit var mListener: SimpleCall

    init {
        mCtx = ctx
    }


    /**
     * A.(B)->C 待接收者类型的函数表达式  A表示接收者的类型，B表示参数类型，C表示返回值类型
     * 不是很明白，下面方法为什么没有指定参数的类型。而且在调用的地方是直接传入A的方法？
     */
    fun setListener(simple: SimpleBulid.() -> Unit) {
        var build1 =
            SimpleBulid()      // 创建接收者对象
        build1.simple()     // 将该接收者对象传给lambda。  不理解为什么是将接口者对象传给lambda？而且查看字节码文件确实是lambda.invoke(接收者对象)
        mListener = build1
    }

    fun getItem(position: Int) {
        var holder =
            ViewHolder(
                mCtx
            )
        mListener.onSuccess("成功 -$position")
        mListener.onFail(-100, "失败 -$position")
    }

    fun justPage(vararg str: String) {
        str.forEach { println("可变参数  $it") }
    }

    fun justA(str: String, name: String = "Tom", age: Int) {
        println("命名参数 $str ,$name,$age")
    }

    class ViewHolder(ctx: Context) {
        val tv: TextView
        val et: EditText

        init {
            tv = TextView(ctx)
            et = EditText(ctx)
        }

        fun ge(context: Context) {
            var mss: MyEditText =
                MyEditText(context)
        }
    }
}

/**
 * lambda表达式与匿名函数()都是函数字面值，即未声明函数
 */

class MyEditText @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.editTextStyle,
    defStyleRes: Int = 0
) :
    EditText(context, attrs, defStyleAttr, defStyleRes) {
    var etName: String = "fs"
        set(value) {
            field = value
        }
        get() = field

    var empty: Boolean
        get() = etName.isNullOrEmpty()
        set(value) {
            etName = "true"
        }
}

/**
 * 类委托：装饰者模式
 *   作用：
 *   步骤：1、类委托需要实现接口；2、被委托类和委托类都需要实现公共接口
 *   字节码文件：委托类中生成了被委托类的对象实例，而且公共接口方法内部调用被委托类的方法
 *
 * 委托属性: 类似给属性找代理。  在创建属性时检查属性是否一致性
 *   作用：用于多个类中属性做相同的判断，减少重复代码。例如：姓名、地址是否符合格式。
 *   步骤：1、创建委托类 2、创建getValue()、setValue()
 *   字节码文件：属性的类型变为委托类
 *   kotliln库提供了两个接口：ReadOnlyProperty、 ReadWriteProperty。
 */
class Delege {
    var delegeName: String = ""
        private set

    var age: Int by Delegates.observable(1024) { property, oldValue, newValue ->

        println("委托监听Delegates.observable  数据发生改变 oldValue=$oldValue , newValue=$newValue")
    }

    var address: String by Delegates.vetoable("123456789@163.com") { property, oldValue, newValue ->
        println("委托监听Delegates.vetoable  数据发生改变 oldValue=$oldValue , newValue=$newValue")
        /**
         * 必须有返回值--Boolean类型。 是否执行赋值操作。true--赋值 false--放弃赋值
         * 可以在赋值之前，判断该属性是否一致性。
         */
        oldValue != newValue
    }

//    lateinit var ss:Boolean    // lateinit只支持引用类型

    var sex: Boolean by Delegates.notNull<Boolean>()     // 支持引用类型、基本数据类型

    /**
     * thisRef：委托属性对象，类型与委托属性对象一致。也就是外部属性委托所在的类
     * property: 目标属性，类型必须于KProperty或委托属性对象一致
     */
    operator fun getValue(thisRef: Any?, proprety: KProperty<*>): String {
        println("委托属性：getValue")
        return delegeName
    }

    operator fun setValue(thisRef: Any?, proprety: KProperty<*>, value: String) {
        println("委托属性：setValue")
        this.delegeName = value
    }
}

/**
 * 1）async()和launch()的区别
 *  async() 的执行结果通过，.await()获取
 *  async():不会阻塞外层代码往下执行。
 *
 * 2）withContext() 用于在协程中切换线程。会阻塞外层代码往下执行。
 *
 * 3）suspend(): 非阻塞式挂起函数。 将协程从当前线程脱离（挂起），当前线程不需要管协程的代码。
 *          线程执行到suspend(),暂时不执行剩余的协程代码，继续往下执行；而协程，脱离线程后在特定的线程环境下执行。（例如：在主线中被挂起，子线程中执行剩下的协程代码）
 *      1、如何自定义suspend()函数？自定义suspend()作用是什么
 *
 * 4）CoroutineContext ：
 *     Dispatchers.Main：主线程(UI线程)
 *     Dispatchers.IO：子线程(线程池)
 *     Dispatchers.Default：（线程池）适合CPU密集，例如：计算。是不是可用于数据库存取数据
 *     Dispatchers.Unconfined (直接执行)
 *
 * 5) 非阻塞式挂起：协程在切换线程时挂起。
 */
fun createCoroutin() {
//    runBlocking {
//        // coroutineScope() 函数，应该运行在挂起函数或协程中。  挂起函数是非阻塞式的。
//        coroutineScope{
//           var user =  async { 1 }
//            var age = async {  }
////           suspendingMerge(user,age)  // 将两个请求结果可挂起合并
//            withContext(Dispatchers.IO){
//
//            }
//
//            launch {
//
//            }
//
//            user.await()
//
//        }
//    }
//    corfun()

    CoroutineStudy().reqestNet()

    /*CoroutineScope(Dispatchers.IO).launch {
        println("协程代码-1 ${Thread.currentThread().name}")
        // 运行在主线程

        withContext(Dispatchers.IO) {
            // 运行在子线程。使用async() 不会阻塞外层代码往下执行呢？
            Thread.sleep(1000)
            println("协程代码-2 ${Thread.currentThread().name}")
        }

        async(Dispatchers.IO) {
            // 运行在子线程。使用withContext() 会阻塞外层的代码不往下执行。为什么？
            Thread.sleep(1000)
            println("协程代码-3 ${Thread.currentThread().name}")
        }

        println("协程代码-4 ${Thread.currentThread().name}")
    }*/
}


fun corfun() {

    /**
     * 可以切换线程环境。newSingleThreadContext("MyThread")--> 启动一个新的线程
     * DefaultScheduler
     *
     * launch()函数：开启一个协程
     *
     */

    var job = GlobalScope.launch(Dispatchers.Default) {
        // GlobalScope：子线程全局协程作用域   coroutineScope：协程作用域
        // 启动一个新线程
        println("协程 输出前 ${Thread.currentThread().name}")
        delay(1000)   // 非阻塞函数，睡眠1秒。deley：挂起函数，挂起协程。只在协程中使用
        println("协程 输出后 ${Thread.currentThread().name}")

        var result = async {
            delay(5000)   // 挂起1秒后，才返回结果
        }
        println("拿到结果 输出---1")
        println("拿到结果 输出 ${result.await()}")   // await是阻塞时，只有执行完成后才往下执行。 用同步的代码执行异步请求。
        println("拿到结果 输出---2")
        withContext(coroutineContext) {
            // withContext 不会创建新的协程
        }
    }
/*
    thread {
        // thread：
        println("线程 输出前 ${Thread.currentThread().name}")
        Thread.sleep(1000)
        println("线程 输出后 ${Thread.currentThread().name}")

        runBlocking {
            // runBlocking：用于桥接阻塞与非阻塞的桥梁。运行在当前线程
            println("运行阻塞 输出前 ${Thread.currentThread().name}")
            delay(2000)
            println("运行阻塞 输出后 ${Thread.currentThread().name}")
        }
    }


    runBlocking {
        // runBlocking：用于桥接阻塞与非阻塞的桥梁。运行在当前线程，常规函数
        println("运行阻塞 输出前 ${Thread.currentThread().name}")
        delay(2000)
        println("运行阻塞 输出后 ${Thread.currentThread().name}")

        launch {
            // 新的协程
            delay(1000)
            println("运行阻塞-2 输出后 ${Thread.currentThread().name}")
        }

        coroutineScope {
            // 创建一个协程作用域
            launch {
                // 新的协程
//                    delay(500L)
                println("coroutineScope 输出前 ${Thread.currentThread().name}")
            }

//                delay(100L)
            println("coroutineScope 输出后 ${Thread.currentThread().name}")
        }
    }


    runBlocking {
        repeat(5) {
            // 启动大量的协程。运行在当前线程
//                    i ->    // i表示协程编号
            delay(500L)  // 每个协程睡眠1秒，并输出log
            println("${Thread.currentThread().name}  --> .")
        }
    }*/
}


/*
*  泛型：生产者、消费者。
*  声明型变：in-生产者 、out-消费者
 */

interface CompaintOut<out T> {
    fun printOut(): T
}

fun demoOut(sour: CompaintOut<Any>) {
    var ss: CompaintOut<Any> = sour
//    var yy:CompaintOut<>

    /**
     * 泛型为out T 称为协变。
     * 情况1、方法参数为<String>，接收者为<Any>。 没有问题
     * 情况2、方法参数为<Any> ，接收者为<String>。异常
     */
}


interface CompaintIn<in T> {
    // 泛型T只能作为参数输入，不能作为函数返回值
    fun printIn(result: T)
}

fun demoIn(x: CompaintIn<Number>) {
    var ss: CompaintIn<Double> = x
    var yy: CompaintIn<Any>? = null
    /**
     * 泛型为in T 称为逆变。
     * 情况1、方法参数为父类，接收者为子类，没有问题
     * 情况2、方法参数为子类，接收者为父类，异常。
     *
     * 泛型：声明处型变in、out有什么作用？
     * 泛型中什么是协变、逆变？
     */
//    printMutList(mutableListOf<String>())
    printList(listOf<String>())
}

fun printList(data: List<Any>) {
    var ss = listOf<String>()
    ss.filterNot { it.equals("empty") }
    data.forEach { println("型变 List --$it") }
}

fun printMutList(data: MutableList<Any>) {
    data.forEach { println("型变 MutableList --$it") }
}

fun main() {
    var strList = listOf<String>("a", "b", "c")
    var intList = listOf<Int>(1, 2, 3, 4, 5, 6)

    var dataList = listOf<VideoBean>()
    dataList.get(1).duration = 123
    var videoBea: VideoBean? = null
    var get = dataList.get(0)
    get = videoBea!!

    var strMutList = mutableListOf<String>("e", "f", "g")

    printList(strList)
//    printMutList(strMutList)

    /**
     * 上面两行print ,为什么printList()成功，printMutList()失败？
     *
     */

    var ss: CompaintOut<Double>? = null
    var yy: CompaintOut<Any>? = null
    /**
     * kotlin中协变与逆变与java中<? super T>，<? extend E> 作用类型
     */
}