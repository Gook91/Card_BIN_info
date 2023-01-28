package com.example.cardbininfo.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import com.example.cardbininfo.R
import com.example.cardbininfo.database.AppDatabase
import com.example.cardbininfo.ui.HistoryAdapter

class HistoryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Запускаем корутину, в которой заполняем историю
        CoroutineScope(Dispatchers.Main).launch {
            val historyBinFlow =
                AppDatabase.getInstance(requireContext()).historyBinDao().getAllStrings()
            // Получаем данные из потока и создаём адаптер с ними
            historyBinFlow.collect { binList ->
                val historyList = view.findViewById<RecyclerView>(R.id.history_list)
                historyList.adapter = HistoryAdapter(binList) { setTextInUserEdit(it) }
                historyList.addItemDecoration(
                    DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
                )
            }
        }
    }

    // Устанавливаем текст в поле запроса в Activity
    private fun setTextInUserEdit(string: String) {
        activity?.findViewById<EditText>(R.id.user_bin_text)?.setText(string)
    }
}