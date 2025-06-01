package io.lazycoder.infras.frameworks

import io.lazycoder.domain.user.UserRepository
import io.lazycoder.domain.user.UserService
import io.lazycoder.infras.user.UserRepositoryExposed
import org.koin.dsl.module

val userModule = module {
    single<UserRepository> { UserRepositoryExposed() }

    single { UserService(get<UserRepository>()) }
}