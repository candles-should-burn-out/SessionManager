
const val SESSION_EXPIRY_TIMEOUT = 10 * 60 * 1000L

fun main() {
    val s = SessionManagerImpl(SESSION_EXPIRY_TIMEOUT)
    val sid = s.createSession(1)
    val uid = s.validateSession(sid)
    s.expireOldSessions()
}