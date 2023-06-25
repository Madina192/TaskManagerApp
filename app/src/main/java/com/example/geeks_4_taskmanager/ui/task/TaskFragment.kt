package com.example.geeks_4_taskmanager.ui.task

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.geeks_4_taskmanager.App
import com.example.geeks_4_taskmanager.R
import com.example.geeks_4_taskmanager.databinding.FragmentTaskBinding
import com.example.geeks_4_taskmanager.model.Task

class TaskFragment : Fragment() {
    private lateinit var binding: FragmentTaskBinding
    private var task: Task? = null
    private lateinit var data: Task
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null) {
            task = requireArguments().getSerializable("task") as Task
        }

        fillEditTexts()
        buttonHandleClick()
    }

    private fun fillEditTexts() {
        if (task != null) {
            binding.etTitle.setText(task!!.title)
            binding.etDescr.setText(task!!.desc)
            binding.btnSave.text = getString(R.string.update)
        } else {
            binding.btnSave.text = getString(R.string.save)
        }
    }

    private fun buttonHandleClick() {
        binding.btnSave.setOnClickListener {
            data = Task(
                title = binding.etTitle.text.toString(),
                desc = binding.etDescr.text.toString()
            )
            if (task != null) {
                updateTask()
            } else {
                saveTask()
            }
            findNavController().navigateUp()
        }
    }

    private fun saveTask() {
        task = Task(data.id, data.title, data.desc)
        App.db.taskDao().insert(task!!)
    }

    private fun updateTask() {
        task!!.title = data.title
        task!!.desc = data.desc
        App.db.taskDao().update(task!!)
    }
}