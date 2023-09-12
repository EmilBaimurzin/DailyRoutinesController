package com.routines.game.ui.cover

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.routines.game.R
import com.routines.game.core.library.GameFragment
import com.routines.game.databinding.FragmentCoverBinding

class FragmentCover : GameFragment<FragmentCoverBinding>(FragmentCoverBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.start.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentMain_to_fragmentTasks)
        }

        binding.privacyText.setOnClickListener {
            requireActivity().startActivity(
                Intent(
                    ACTION_VIEW,
                    Uri.parse("https://www.google.com")
                )
            )
        }
    }
}