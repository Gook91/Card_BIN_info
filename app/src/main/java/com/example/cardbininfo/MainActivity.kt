package com.example.cardbininfo

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.navigation.findNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import com.example.cardbininfo.binDataClasses.Bin
import com.example.cardbininfo.database.AppDatabase
import com.example.cardbininfo.database.HistoryBin
import com.example.cardbininfo.databinding.ActivityMainBinding
import com.example.cardbininfo.fragments.BinInfoFragment
import com.example.cardbininfo.retrofit.RetrofitBin

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Берём Dao из базы данных
        val historyBinDao = AppDatabase.getInstance(applicationContext).historyBinDao()

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Делаем кнопку проверки спрятанной и показываем её, только когда символов больше 4
        binding.userBinEnter.isEndIconVisible = false
        binding.userBinText.doOnTextChanged { text, _, _, _ ->
            binding.userBinEnter.isEndIconVisible = (text?.length ?: 0) >= 4
        }

        // Получаем по кнопке информацию о Bin и передаём во фрагмент
        binding.userBinEnter.setEndIconOnClickListener {
            val binNumber = binding.userBinText.text.toString()

            // С помощью retrofit получаем информацию и обрабатываем в объекте  callback
            RetrofitBin.binInfoApi.getBinInfo(binNumber).enqueue(
                object : Callback<Bin> {
                    // Если запрос успешный
                    override fun onResponse(call: Call<Bin>, response: Response<Bin>) {
                        val binInfo = response.body()
                        // Если Bin - пустой, выдаём пользователю сообщение, что Bin не найден
                        if (binInfo == null)
                            Toast.makeText(
                                applicationContext,
                                getString(R.string.bin_not_found),
                                Toast.LENGTH_SHORT
                            ).show()
                        // Иначе сохраняем в истории, создаём bundle и переходим с ним во фрагмент
                        else {
                            val bundle =
                                Bundle().apply { putParcelable(BinInfoFragment.BIN_PARAM, binInfo) }
                            findNavController(R.id.fragment_container).navigate(
                                R.id.new_bin_fragment,
                                bundle
                            )
                            // Сохраняем успешный запрос в базу данных
                            CoroutineScope(Dispatchers.Default).launch {
                                historyBinDao.insert(HistoryBin(number = binNumber))
                            }
                            // Прячем клавиатуру после успешного запроса
                            val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE)
                                    as InputMethodManager
                            inputMethodManager.hideSoftInputFromWindow(
                                binding.userBinText.windowToken, 0
                            )
                        }
                    }

                    // Если запрос неуспешный, то сохраняем информацию в лог
                    // и показываем сообщение об ошибке
                    override fun onFailure(call: Call<Bin>, t: Throwable) {
                        Log.e(LOG_TAG, t.localizedMessage, t)
                        Toast.makeText(
                            applicationContext,
                            getString(R.string.error_response),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
        }
    }

    // Освобождаем binding, чтобы избежать утечек памяти
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        // Тэг для отслеживания ошибок через лог
        const val LOG_TAG = "My_bin_log"
    }
}