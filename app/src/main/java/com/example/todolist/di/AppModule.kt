package com.example.todolist.di

import android.content.Context
import com.example.todolist.data.local.TodoJsonDataSource
import com.example.todolist.data.repository.TodoRepositoryImpl
import com.example.todolist.domain.repository.TodoRepository
import com.example.todolist.domain.usecase.GetTodosUseCase
import com.example.todolist.domain.usecase.ToggleTodoUseCase

object AppModule {
    @Volatile
    private var repositoryInstance: TodoRepository? = null
    
    fun provideTodoRepository(context: Context): TodoRepository {
        return repositoryInstance ?: synchronized(this) {
            repositoryInstance ?: TodoRepositoryImpl(TodoJsonDataSource(context)).also {
                repositoryInstance = it
            }
        }
    }

    fun provideGetTodosUseCase(repository: TodoRepository): GetTodosUseCase {
        return GetTodosUseCase(repository)
    }

    fun provideToggleTodoUseCase(repository: TodoRepository): ToggleTodoUseCase {
        return ToggleTodoUseCase(repository)
    }
}

