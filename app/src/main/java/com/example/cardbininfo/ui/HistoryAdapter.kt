package com.example.cardbininfo.ui

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import com.example.cardbininfo.R
import com.example.cardbininfo.database.AppDatabase
import com.example.cardbininfo.database.HistoryBin
import com.example.cardbininfo.databinding.HistoryViewElementBinding

// Класс адаптера для листа с историей запросов
class HistoryAdapter(
    _binNumbers: List<HistoryBin>,
    private val onClick: (String) -> Unit, // Лямбда, обрабатывающая строку выбранного элемента
) : Adapter<HistoryViewHolder>() {
    // Разворачиваем список запросов, чтобы новые были в начале и делаем его изменяемым
    private val binNumbers = _binNumbers.reversed().toMutableList()

    // Создаём binding, и возвращаем его в элемент списка
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = HistoryViewElementBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return HistoryViewHolder(binding)
    }

    // Формируем элемент при прикреплении
    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        // Назначаем текст
        holder.binding.historyNumber.text = binNumbers[position].number

        holder.binding.root.apply {
            // При щелчке по элементу передаём его в лямбду, которая его обработает
            setOnClickListener { onClick(binNumbers[position].number) }

            // При долгом удержании удаляем элемент
            setOnLongClickListener {

                // Создаём и показываем диалог для подтверждения
                AlertDialog.Builder(context)
                    .setMessage(context.getString(R.string.delete_entry_question))
                    .setPositiveButton(context.getString(R.string.delete)) { _, _ ->
                        // При подтверждении получаем удаляемую запись и в корутине удаляем из базы
                        val deletedEntry = binNumbers[position]
                        CoroutineScope(Dispatchers.Default).launch {
                            AppDatabase.getInstance(context).historyBinDao().delete(deletedEntry)
                        }
                        // Удаляем из базы элементов и сообщаем об изменении адаптеру
                        binNumbers.removeAt(position)
                        notifyItemRemoved(position)
                    }
                    .setNegativeButton(context.getString(R.string.cancel), null)
                    .create()
                    .show()
                return@setOnLongClickListener true
            }
        }
    }

    override fun getItemCount(): Int = binNumbers.size
}
