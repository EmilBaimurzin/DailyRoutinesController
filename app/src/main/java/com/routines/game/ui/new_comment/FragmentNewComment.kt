package com.routines.game.ui.new_comment

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.routines.game.core.library.shortToast
import com.routines.game.databinding.FragmentNewCommentBinding
import com.routines.game.ui.other.ActivityViewModel
import com.routines.game.ui.other.ViewBindingFragment
import java.util.Calendar

class FragmentNewComment: ViewBindingFragment<FragmentNewCommentBinding>(FragmentNewCommentBinding::inflate) {
    private val viewModel: NewCommentViewModel by viewModels()
    private val callbackViewModel: ActivityViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.commentedDateET.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            var finalText = ""
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { view, year, monthOfYear, dayOfMonth ->
                    finalText = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                    viewModel.createDate(dayOfMonth, monthOfYear + 1)
                    viewModel.day = dayOfMonth
                    viewModel.month = monthOfYear
                    binding.commentedDateET.text = finalText
                },
                year,
                month,
                day
            )
            datePickerDialog.setOnCancelListener {
                binding.commentedDateET.text = ""
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
                )
                findNavController().popBackStack()
                callbackViewModel.callback?.invoke()
            } else {
                shortToast(requireContext(), "Field must be filled")
            }
        }
    }

    private fun getTitle(): String = binding.commentedNameET.text.toString()
    private fun getTime(): String = binding.commentedDateET.text.toString()
    private fun getDescription(): String = binding.descriptionET.text.toString()
}