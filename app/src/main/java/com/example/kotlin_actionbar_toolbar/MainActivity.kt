package com.example.kotlin_actionbar_toolbar

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.kotlin_actionbar_toolbar.databinding.ActivityMainBinding
import com.example.kotlin_actionbar_toolbar.fragment.EditStudentFragment
import com.example.kotlin_actionbar_toolbar.fragment.StudentFragment

class MainActivity : AppCompatActivity(), StudentFragment.OnStudentEditListener {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction()
            .add(R.id.container_layout, StudentFragment.getInstance(this))
            .setReorderingAllowed(true)
            .commit()
    }

    override fun onStudentEdit() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container_layout, EditStudentFragment::class.java, null)
            .setReorderingAllowed(true)
            .addToBackStack(null)
            .commit()
    }
}