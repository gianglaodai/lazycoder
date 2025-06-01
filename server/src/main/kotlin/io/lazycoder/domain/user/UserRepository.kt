package io.lazycoder.domain.user

interface UserRepository {
    suspend fun findAll(): List<User>
    suspend fun findById(id: Int): User?
    suspend fun create(user: User): Int
    suspend fun update(id: Int, user: User)
    suspend fun delete(id: Int)
}