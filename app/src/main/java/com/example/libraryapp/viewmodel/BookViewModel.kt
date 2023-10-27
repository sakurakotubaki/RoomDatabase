package com.example.libraryapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.libraryapp.repository.Repository
import com.example.libraryapp.room.BookEntity
import kotlinx.coroutines.launch

// BookViewModelクラスを作成。これはViewModelクラスを継承する。
// このクラスは、Repositoryクラスを引数にとる。
class BookViewModel(val repository: Repository): ViewModel() {

    fun addBook(book: BookEntity){
        viewModelScope.launch {
            repository.addBookToRoom(book)
        }
    }


}