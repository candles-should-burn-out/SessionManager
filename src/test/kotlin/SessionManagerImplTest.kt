import org.junit.jupiter.api.Test
import java.util.*
import kotlin.collections.ArrayList

class SessionManagerImplTest {

    @Test
    fun createSessionTest() {
        val manager = SessionManagerImpl(SESSION_EXPIRY_TIMEOUT)

        val l = ArrayList<Long>()

        System.err.println("A -> ${UUID.randomUUID()}") // Прогрев, влияет на время первого создания Session
        System.err.println("A -> ${UUID.randomUUID()}")

        for (i: Int in 1..4_000_000) {
            val startTime = System.currentTimeMillis()
            manager.createSession(i)
            val endTime = System.currentTimeMillis()
            val time = endTime - startTime
            l += time
        }

        //Тест для демонстрации, поэтому assert нет
        "CreateSession --> [${l[0]}, ${l[1]}, ${l[2]}, ... ${l.average()} ... ${l[l.size - 3]}, ${l[l.size - 2]}, ${l[l.size - 1]}]".println()
    }

    @Test
    fun expireOldSessionsTest() {
        val timeout = 7_000L
        val manager = SessionManagerImpl(timeout)

        "Create 1_000_000 ...".println()

        for (i: Int in 1..1_000_000) {
            manager.createSession(i)
        }

        "Sleep $timeout ...".println()

        Thread.sleep(20_000)

        "Create 3_000_000 ...".println()

        for (i: Int in 1..3_000_000) {
            manager.createSession(i)
        }

        "Expire 1_000_000 ...".println()

        var start = System.currentTimeMillis()
        manager.expireOldSessions()
        val time1 = System.currentTimeMillis() - start
        val size1 = manager.getSessionCount()

        "Sleep $timeout ...".println()

        Thread.sleep(20_000)

        "Expire 3_000_000 ...".println()

        start = System.currentTimeMillis()
        manager.expireOldSessions()
        val time2 = System.currentTimeMillis() - start
        val size2 = manager.getSessionCount()
        val t = time2 / time1.toDouble()

        "expireOldSessions --> [1_000_000] Time: $time1 Size: $size1".println()
        "expireOldSessions --> [3_000_000] Time: $time2 Size: $size2".println()
        "expireOldSessions --> [3_000_000 / 1_000_000] Time: $t".println()
        assert(t > 2.7 || t < 3.3)
    }
}