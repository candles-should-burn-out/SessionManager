@file:Suppress("MemberVisibilityCanBePrivate")

class SessionManagerImpl(val timeout: Long) : SessionManager {

    private val sessions = HashMap<String, Session>(8_000_000)
    private var oldestSession: Session? = null
    private var newestSession: Session? = null

    override fun createSession(userId: Int): String {
        val session = Session(userId)
        session.olderSession = newestSession

        if (newestSession != null) {
            newestSession!!.newerSession = session
        }

        newestSession = session

        if (oldestSession == null) {
            oldestSession = session
        }

        sessions[session.id] = session

        return session.id
    }

    override fun validateSession(sessionId: String): Int? {
        val session = sessions[sessionId]

        if (session != null) {
            session.update()

            if (session.newerSession != null) { // ?? - v session - session
                session.newerSession!!.olderSession = session.olderSession

                if (session.olderSession != null) { // session - v session - session
                    session.olderSession!!.newerSession = session.newerSession
                } else { // null - v session - session
                    oldestSession = session.newerSession
                }

                newestSession!!.newerSession = session
                session.olderSession = newestSession
                session.newerSession = null

                newestSession = session
            } // ?? - v session - null

            return session.userId
        }

        return null
    }

    override fun expireOldSessions() {
        while (expireNextOldSession()) {
            // nothing
        }
    }

    private fun expireNextOldSession() = (oldestSession?.isExpire(timeout) == true).apply {
        if (this) {
            sessions.remove(oldestSession!!.id)
            oldestSession = oldestSession!!.newerSession

            if (oldestSession != null) {
                oldestSession!!.olderSession = null
            }
        }
    }

    //for tests
    fun getSessionCount() = sessions.size
}