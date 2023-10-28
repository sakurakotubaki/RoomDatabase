package com.example.libraryapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.libraryapp.repository.Repository
import com.example.libraryapp.room.BookEntity
import com.example.libraryapp.room.BooksDB
import com.example.libraryapp.screens.UpdateScreen
import com.example.libraryapp.ui.theme.LibraryAppTheme
import com.example.libraryapp.viewmodel.BookViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LibraryAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    val mContext = LocalContext.current
                    val db = BooksDB.getInstance(mContext)
                    val repository = Repository(db)
                    val myViewModel = BookViewModel(repository = repository)

                    // Navigation
                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = "MainScreen"){
                        composable("MainScreen"){
                            MainScreen(viewModel = myViewModel, navController = navController)
                        }
                        composable("UpdateScreen/{bookId}"){
                            val bookId = it.arguments?.getString("bookId")
                            // 画面遷移の用の引数を追加して、MainScreenに戻ってこれるようにした。
                            UpdateScreen(viewModel = myViewModel, bookId = bookId!!, navController = navController)
                        }


                }
            }
        }
    }
}
// Material3Apiが必要なのでつける
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: BookViewModel, navController: NavHostController) {

    var inputBook by remember {
        mutableStateOf("")
    }
    // Formを中央揃え
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        OutlinedTextField(
            value = inputBook,
            onValueChange = {
                            enteredText -> inputBook = enteredText
            },
            label = { Text(text = "Enter the Book Name") },
            placeholder = { Text(text = "Enter the Book Name") }
        )

        Button(
            onClick = {
                // idは、0として渡しても自動生成される
                viewModel.addBook(BookEntity(id = 0, title = inputBook))
            }, colors = ButtonDefaults.buttonColors(Color.Blue)) {
            Text(text = "Insert Book into DB")
        }

        // ここで、BookListを呼び出す
        BooksList(viewModel = viewModel, navController = navController)
        
    }
}

@Composable
fun BookCard(viewModel: BookViewModel, book:BookEntity, navController: NavController) {
    
    Card(modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth()) {
        
        Row(verticalAlignment = Alignment.CenterVertically) {


           Text(text = "" + book.id, fontSize = 24.sp,
           modifier = Modifier.padding(start = 12.dp, end = 12.dp))

            Text(text = book.title, fontSize = 24.sp,
            modifier = Modifier.padding(start = 12.dp, end = 12.dp),
            color = Color.Black)

            IconButton(onClick = { viewModel.deleteBook(book = book) }) {
                Icon(imageVector = Icons.Default.Delete,
                    contentDescription = "Delete")
            }
            
            IconButton(onClick = {
                navController.navigate("UpdateScreen/${book.id}")
            }) {
                Icon(imageVector = Icons.Default.Edit,
                    contentDescription = "Edit")
            }
        }
        
    }
}

@Composable
fun BooksList(viewModel: BookViewModel, navController: NavHostController) {
    val books by viewModel.books.collectAsState(initial = emptyList())

    Column(Modifier.padding(16.dp)) {
        Text(text = "My Library", fontSize = 24.sp, color = Color.Red)

        LazyColumn() {
            items(items = books) { item ->
                BookCard(
                    viewModel = viewModel,
                    book = item,
                    navController
                )


            }
        }
    }
  }
}