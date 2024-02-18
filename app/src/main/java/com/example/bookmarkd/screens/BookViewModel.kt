package com.example.bookmarkd.screens

import android.net.http.HttpException
import android.os.Build
import android.text.Spannable.Factory
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bookmarkd.data.BookRepository
import com.example.bookmarkd.data.DefaultBookRepository
import com.example.bookmarkd.model.Book
import kotlinx.coroutines.launch
import java.io.IOException
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import com.example.bookmarkd.BookApplication


sealed interface BookListUiState{
    data class Success(val books: List<Book>): BookListUiState
    object Error: BookListUiState
    object Loading: BookListUiState
}

sealed interface BookUiState{
    data class Success(val book: Book): BookUiState
    object Error: BookUiState
    object Loading: BookUiState
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
class BookListViewModel(private val bookListRepository: BookRepository): ViewModel()
{
    var bookListUiState: BookListUiState by mutableStateOf(BookListUiState.Loading)
        private set

    init {
        getBooks()
    }

    //will add more functions when everything is tested properly
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun getBooks(query: String = "jazz"){//have it as jazz right now until i get the search functions implemented
        viewModelScope.launch{
            bookListUiState = BookListUiState.Loading
            bookListUiState = try{
                val books = bookListRepository.getBooks(query)
                if (books == null) {
                    BookListUiState.Error
                }else if (books.isEmpty()) {
                    BookListUiState.Success(emptyList())
                }else {
                    BookListUiState.Success(books)
                }
            } catch (e: IOException){
                BookListUiState.Error
            } catch (e: HttpException){
                BookListUiState.Error
            }
        }
    }

    companion object{
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as BookApplication)
                val bookRepository = application.container.bookRepository
                BookListViewModel(bookRepository)
            }
        }
    }
}

class BookViewModel(private val bookRepository: BookRepository): ViewModel()
{
    var bookUiState: BookUiState by mutableStateOf(BookUiState.Loading)
        private set


    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun getBook(id: String){
        viewModelScope.launch {
            bookUiState = BookUiState.Loading
            bookUiState = try{
                val book = bookRepository.getBook(id)
                if(book == null){
                    BookUiState.Error
                }else{
                    BookUiState.Success(book)
                }
            }catch (e: IOException){
                BookUiState.Error
            } catch (e: HttpException){
                BookUiState.Error
            }
        }
    }

    companion object{
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as BookApplication)
                val bookRepository = application.container.bookRepository
                BookViewModel(bookRepository)
            }
        }
    }
}