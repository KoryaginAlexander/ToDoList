package com.example.todolist.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.domain.model.TodoItem
import com.example.todolist.domain.usecase.GetTodosUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TodoDetailViewModel(
    private val getTodosUseCase: GetTodosUseCase
) : ViewModel() {

    private val _todo = MutableStateFlow<TodoItem?>(null)
    val todo: StateFlow<TodoItem?> = _todo.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun loadTodo(id: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val todos = getTodosUseCase()
                _todo.value = todos.find { it.id == id }
            } finally {
                _isLoading.value = false
            }
        }
    }
}

