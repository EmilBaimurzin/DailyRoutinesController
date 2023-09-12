package com.routines.game.ui.new_task

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.routines.game.core.library.shortToast
import com.routines.game.databinding.FragmentNewTaskBinding
import com.routines.game.ui.other.ActivityViewModel
import com.routines.game.ui.other.ViewBindingFragment
import java.util.Calendar

class FragmentNewTask :
    ViewBindingFragment<FragmentNewTaskBinding>(FragmentNewTaskBinding::inflate) {
    private val viewModel: NewTaskViewModel by viewModels()
    private val callbackViewModel: ActivityViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.timeET.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            var finalText = ""
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { view, year, monthOfYear, dayOfMonth ->
                    finalText = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)

                    val c = Calendar.getInstance()
                    val hour = c.get(Calendar.HOUR_OF_DAY)
                    val minute = c.get(Calendar.MINUTE)
                    val timePickerDialog = TimePickerDialog(
                        requireContext(),
                        { view, hourOfDay, minute ->
                            finalText = "$finalText $hourOfDay:$minute"
                            viewModel.createDate(dayOfMonth, monthOfYear + 1)
                            viewModel.hours = hourOfDay
                            viewModel.minutes = minute
                            viewModel.day = dayOfMonth
                            viewModel.month = monthOfYear
                            binding.timeET.text = finalText
                        },
                        hour,
                        minute,
                        false
                    )
                    timePickerDialog.setOnCancelListener {
                        binding.timeET.text = ""
                        viewModel.hours = 0
                        viewModel.minutes = 0
                        viewModel.day = 0
                        viewModel.month = 0
                    }

                    timePickerDialog.show()
                },
                year,
                month,
                day
            )
            datePickerDialog.setOnCancelListener {
                binding.timeET.text = ""
                viewModel.hours = 0
                viewModel.minutes = 0
                viewModel.day = 0
                viewModel.month = 0
            }
            datePickerDialog.show()
        }

        binding.create.setOnClickListener {
            val title = getTitle()
            val time = getTime()
            val description = getDescription()

            if (title.isNotBlank() && description.isNotBlank() && time.isNotBlank()) {
                viewModel.createTask(
                    viewModel.day,
                    viewModel.month + 1,
                    title,
                    description,
                    viewModel.hours.toString() + if (viewModel.hours > 12) " PM" else " AM"
                )
                findNavController().popBackStack()
                callbackViewModel.callback?.invoke()
            } else {
                shortToast(requireContext(), "Field must be filled")
            }
        }
    }

    private fun getTitle(): String = binding.nameET.text.toString()
    private fun getTime(): String = binding.timeET.text.toString()
    private fun getDescription(): String = binding.descriptionET.text.toString()

}