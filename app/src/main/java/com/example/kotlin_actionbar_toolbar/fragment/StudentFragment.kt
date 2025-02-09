package com.example.kotlin_actionbar_toolbar.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import com.example.kotlin_actionbar_toolbar.R
import com.example.kotlin_actionbar_toolbar.databinding.FragmentStudentBinding
import com.example.kotlin_actionbar_toolbar.viewmodel.StudentViewModel

class StudentFragment() : Fragment() {
    private lateinit var binding: FragmentStudentBinding
    private var listener: OnStudentEditListener? = null

    companion object{
        fun getInstance(listener: OnStudentEditListener): StudentFragment {
            val fragment = StudentFragment()
            fragment.listener = listener
            return fragment
        }
    }

//    @Suppress("DEPRECATION")
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setHasOptionsMenu(true) // notify fragment that it has menu items
//    }
//
//    @Suppress("DEPRECATION")
//    @Deprecated("Deprecated in Java")
//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        super.onCreateOptionsMenu(menu, inflater)
//        inflater.inflate(R.menu.student_menu, menu)
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentStudentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMenu()
        val viewmodel = ViewModelProvider(requireActivity())[StudentViewModel::class.java]
        viewmodel.students.observe(viewLifecycleOwner){
            binding.textFullName.text = getString(R.string.text_full_name,it.fullName)
            binding.textAge.text = getString(R.string.text_age,it.age)
        }
    }

    private fun initMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.student_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    // TODO: Handle action bar item clicks here.
                    R.id.action_edit -> {
                        listener?.onStudentEdit()
                        true
                    }
                    R.id.action_search -> {
                        // TODO: Handle search action
                        true
                    }
                    R.id.action_setting -> {
                        // TODO: Handle setting action
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    interface OnStudentEditListener {
        fun onStudentEdit()
    }
}