package com.example.mainproject

import android.app.Activity
import android.content.Context
import android.text.TextUtils.indexOf
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_my_profile.*
import java.io.File
import java.lang.Exception

//обновляет текстовые файлы в памяти приложения иншалла
fun update_text_files(files_list: List<String>, update_list: List<String>, activity_1 : Activity ) {
    // сюда подаются названия файлов вот так: file.txt - это строка
    try {
        var j = 0
        val path = activity_1.getFilesDir()// нашли путь к внутренней директории приложения
        for (k in files_list) {
            File(path, k).delete() //удалили старыый файл
            activity_1.openFileOutput(k, Context.MODE_PRIVATE)
                .write(update_list[j].toByteArray()) // создалли и записали новый
            j++
        }
        Toast.makeText(activity_1, "Данные успешно сохранены!", Toast.LENGTH_LONG).show()
    }
    catch (e:Exception) {Toast.makeText(activity_1, "Что-то не так!", Toast.LENGTH_LONG).show()}


}

