package com.example.cardbininfo.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.cardbininfo.R

class WelcomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_welcome, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Находим кнопку по id и назначаем ей слушатель щелчка - старый способо без binding
        val button: Button = view.findViewById(R.id.to_history_button)
        button.setOnClickListener {
            findNavController().navigate(R.id.from_welcome_to_history)
        }
    }
}