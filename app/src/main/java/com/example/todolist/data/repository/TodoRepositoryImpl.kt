package com.example.todolist.data.repository

import com.example.todolist.data.local.TodoJsonDataSource
import com.example.todolist.data.model.TodoItemDto
import com.example.todolist.domain.model.TodoItem
import com.example.todolist.domain.repository.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TodoRepositoryImpl(
    private val dataSource: TodoJsonDataSource
) : TodoRepository {
    
    private var todos: MutableList<TodoItem> = mutableListOf()
    private var isInitialized = false

    override suspend fun getTodos(): List<TodoItem> = withContext(Dispatchers.IO) {
        if (!isInitialized) {
            val dtos = dataSource.getTodos()
            todos = dtos.map { it.toDomain() }.toMutableList()
            isInitialized = true
        }
        return@withContext todos.toList()
    }

    override suspend fun toggleTodo(id: Int) = withContext(Dispatchers.IO) {
        val index = todos.indexOfFirst { it.id == id }
        if (index != -1) {
            val todo = todos[index]
            todos[index] = todo.copy(isCompleted = !todo.isCompleted)
        }
    }

    private fun TodoItemDto.toDomain(): TodoItem {
        return TodoItem(
            id = id,
            title = title,
            description = description,
            isCompleted = isCompleted
        )
    }
}

