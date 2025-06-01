package io.lazycoder.domain.user

class UserService(private val repository: UserRepository) {
    suspend fun getById(id: Int): User? = repository.findById(id)
    suspend fun getAll(): List<User> = repository.findAll()
    suspend fun create(user: User): Int = repository.create(user)
    suspend fun update(id: Int, user: User) = repository.update(id, user)
    suspend fun delete(id: Int) = repository.delete(id)
}