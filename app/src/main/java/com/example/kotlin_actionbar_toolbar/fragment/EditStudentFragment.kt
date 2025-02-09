package com.example.kotlin_actionbar_toolbar.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import com.example.kotlin_actionbar_toolbar.R
import com.example.kotlin_actionbar_toolbar.databinding.FragmentEditStudentBinding
import com.example.kotlin_actionbar_toolbar.model.Student
import com.example.kotlin_actionbar_toolbar.viewmodel.StudentViewModel

class EditStudentFragment : Fragment(), View.OnFocusChangeListener {
    private lateinit var binding: FragmentEditStudentBinding
    private lateinit var viewModel: StudentViewModel
    private lateinit var fullName: EditText
    private lateinit var age: EditText
    private var isEditing = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentEditStudentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[StudentViewModel::class.java]
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fullName = binding.editName
        age = binding.editAge
        viewModel.students.observe(viewLifecycleOwner) {
            fullName.setText( it.fullName)
            age.setText(it.age.toString())
        }
        fullName.onFocusChangeListener = this
        age.onFocusChangeListener = this
        binding.toolbarEdit.inflateMenu(R.menu.student_detail_menu)
        updateToolbar()
        binding.toolbarEdit.title = "Edit Student"
        binding.toolbarEdit.setNavigationIcon(R.drawable.ic_arrow_back_24)
        binding.toolbarEdit.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        binding.toolbarEdit.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_done -> {
                    val student = Student(fullName.text.toString(), age.text.toString().toInt())
                    viewModel.updateStudent(student)
                    requireActivity().supportFragmentManager.popBackStack()
                    true
                }
                else -> false
            }
        }
        // initMenu()
    }

    private fun updateToolbar() {
        val itemMenuDone = binding.toolbarEdit.menu.findItem(R.id.action_done)
        itemMenuDone.isVisible = isEditing
    }

    private fun initMenu() {
        val menuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.student_detail_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_done -> {
                        val student = Student(fullName.text.toString(), age.text.toString().toInt())
                        viewModel.updateStudent(student)
                        requireActivity().supportFragmentManager.popBackStack()
                        true
                    }
                    else -> false
                }
            }

            override fun onPrepareMenu(menu: Menu) {
                super.onPrepareMenu(menu)
                val menuItem = menu.findItem(R.id.action_done)
                menuItem.isVisible = isEditing
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onFocusChange(view: View?, hasFocus: Boolean) {
        if(hasFocus){
            isEditing = true
            updateToolbar()
            // requireActivity().invalidateMenu()
        }
    }
}