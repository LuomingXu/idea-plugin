import com.github.luomingxu.idea.entity.RESTNavigationItem
import com.github.luomingxu.idea.util.GetAllApi
import com.github.luomingxu.idea.util.Util
import kotlinx.coroutines.*
import okhttp3.internal.wait
import java.awt.image.MultiPixelPackedSampleModel
import java.util.concurrent.CompletionService
import java.util.concurrent.CountDownLatch
import java.util.concurrent.ExecutorCompletionService
import java.util.concurrent.Executors
import kotlin.concurrent.thread
import kotlin.system.measureTimeMillis

class test {

    companion object {
        var a = "123"
    }

    init {
        test()
    }

    fun test() {
        thread {
            Thread.sleep(1000)
            a = "eq"
        }.start()
    }
}


private suspend fun intValue1(): Int {
    delay(1000)
    return 1
}

private suspend fun intValue2(): Int {
    delay(2000)
    return 2
}

//fun main() = runBlocking {
//
//    val elapsedTime = measureTimeMillis {
//        val value1 = async { intValue1() }
//        val value2 =async { intValue2()}
//
//        println("the result is ${value1.await() + value2.await()}")
//    }
//
//    println("the elapsedTime is $elapsedTime")
//}

//size = int(math.ceil(all_data.__len__() / float(cpu_use_number)))
//map_temp: MutableMapping[str, AV] = {}
//
//res: List[ApplyResult] = list()
//for key, value in all_data.items():
//map_temp[key] = value
//if map_temp.__len__() % size == 0:
//res.append(pool.apply_async(func = analyze, args = (q, map_temp,)))
//map_temp = {}
//res.append(pool.apply_async(func = analyze, args = (q, map_temp,)))
//pool.close()
//pool.join()

private fun test(l: MutableList<Int>): MutableList<Int> {
    l.forEach { print("$it,") }
    println("size: ${l.size}")
    l.add(2)
    return l
}

class CLAZZ {
    companion object

    var num: List<Int> = ArrayList()

    init {
        num = TEST.test1()
    }
}


object TEST {
    fun test1(): List<Int> = runBlocking {
        var list: MutableList<Int> = ArrayList()
        var l: MutableList<String> = ArrayList()
        for (i in 0..100) {
            list.add(i)
        }
        var size: Int = list.size / Util.CPU
        println("${Util.CPU}, $size")


        //创建自定义线程池
        val pool = Executors.newFixedThreadPool(8).asCoroutineDispatcher()
        var temp: MutableList<Int> = ArrayList()
        var res: MutableList<Int> = ArrayList()
        launch {
            for (i in list) {
                temp.add(i)
                if (temp.size % size == 0) {
                    val t: Deferred<List<Int>> = async(pool) {
                        println(Thread.currentThread().name)
                        test(temp)
                    }
                    res.addAll(t.await())
                    temp = ArrayList()
                }
            }
        }.join()
        pool.close()
        println("子协程完")
        res.forEach { println(it) }
        println(res.size)
        ArrayList()
    }
}

fun main() {

    val executor = Executors.newFixedThreadPool(5)
    val cs: ExecutorCompletionService<MutableList<Int>> = ExecutorCompletionService(executor)
    cs.submit{
        test(ArrayList())
    }
    cs.submit{
        test(ArrayList())
    }
    for (i in 0..1) {
        println("i: $i")
        println(cs.take().get())
    }

    println("end")
//    println(cs.take().get())
    executor.shutdown()


//   val t = CLAZZ()
//    println(t.num)
//    GetAllApi.getProjectAllApiMultiThread(null)
}
