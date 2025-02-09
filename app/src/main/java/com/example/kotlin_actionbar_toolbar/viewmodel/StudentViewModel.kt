package com.example.kotlin_actionbar_toolbar.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin_actionbar_toolbar.model.Student

class StudentViewModel : ViewModel() {
    private val _students = MutableLiveData<Student>()
    val students: LiveData<Student> = _students

    init {
        val student = Student("John Doe", 20)
        _students.value = student
    }
    fun updateStudent(student: Student) {
        _students.value = student
    }
}