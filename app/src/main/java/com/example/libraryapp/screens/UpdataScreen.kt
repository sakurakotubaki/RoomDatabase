package com.example.libraryapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.libraryapp.room.BookEntity
import com.example.libraryapp.viewmodel.BookViewModel

// 更新用の画面
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateScreen(viewModel: BookViewModel, navController: NavController, bookId: String?) {

    var inputBook by remember {
        mutableStateOf("")
    }


    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(top = 22.dp, start = 6.dp, end = 6.dp)

    ) {

        Text(text = "Update The Existing", fontSize = 24.sp)

        OutlinedTextField(
            value = inputBook,
            onValueChange = { inputBook = it },
            label = { Text(text = "Update Book Name") },
            placeholder = { Text(text = "New Book Name") },
        )

        Button(onClick = {
            var newBook = BookEntity(bookId!!.toInt(), inputBook)
            viewModel.updateBook(newBook)
            // 画面を戻す
            navController.popBackStack()
        }, modifier = Modifier.padding(top = 16.dp),
        colors = ButtonDefaults.buttonColors(Color.Red)
            ) {
            Text(text = "Update Book")
        }

    }
}