package com.example.libraryapp.repository

import com.example.libraryapp.room.BookEntity
import com.example.libraryapp.room.BooksDB

class Repository(val booksDB: BooksDB) {

    suspend fun addBookToRoom(bookEntity: BookEntity) {
        booksDB.bookDAO().addBook(bookEntity)
    }

    fun getAllBooks() = booksDB.bookDAO().getAllBooks()

    suspend fun deleteBookFromRoom(bookEntity: BookEntity){
        booksDB.bookDAO().deleteBook(bookEntity = bookEntity)
    }

    suspend fun updateBook(bookEntity: BookEntity){
        booksDB.bookDAO().updateBook(bookEntity = bookEntity)
    }

}