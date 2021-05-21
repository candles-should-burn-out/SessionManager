interface SessionManager {
    /**
     * Creates new session for userId
     * and returns sessionId
     */
    fun createSession(userId: Int): String

    /**
     * Returns userId by sessionId if session is active
     * or null otherwise
     */
    fun validateSession(sessionId: String): Int?

    /**
     * Kills sessions that were created more than 10 minutes ago
     * and for which method validateSession() has not been called for 10 minutes
     */
    fun expireOldSessions()
}