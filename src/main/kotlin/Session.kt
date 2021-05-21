@file:Suppress("MemberVisibilityCanBePrivate")

import java.util.*

class Session(val userId: Int) {
    val id = UUID.randomUUID().toString();
    val createTime = System.currentTimeMillis()
    var lastUpdate = createTime
    var olderSession: Session? = null
    var newerSession: Session? = null

    fun update() {
        lastUpdate = System.currentTimeMillis()
    }

    fun isExpire(timeout: Long) = (System.currentTimeMillis() - lastUpdate) > timeout
}