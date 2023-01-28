package com.example.cardbininfo.fragments

import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.cardbininfo.R
import com.example.cardbininfo.binDataClasses.Bin
import com.example.cardbininfo.databinding.FragmentBinInfoBinding

// Класс фрагмента с полученной информацией о Bin
class BinInfoFragment : Fragment() {

    private var _binding: FragmentBinInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBinInfoBinding.inflate(inflater)

        // Объявляем элемент Bin и получаем информацию из bundle
        var binInfo: Bin? = null
        arguments?.apply {
            // Для новой версии SDK старый метод получения - deprecated, поэтому вызываем
            // в зависимости от версии свой метод, подавляя deprecation для старого варианта
            binInfo = if (SDK_INT >= 33)
                getParcelable(BIN_PARAM, Bin::class.java)
            else
                @Suppress("DEPRECATION") getParcelable(BIN_PARAM)
        }
        // Если данные были получены, то заполняем поля элементов
        binInfo?.apply {

            // Определяется блок с платёжной системой
            binding.scheme.setText(
                scheme?.replaceFirstChar { it.uppercase() },
                brand
            )

            // Определяется блок с типом карты
            binding.type.setText(
                when (type) {
                    "debit" -> getString(R.string.debit)
                    "credit" -> getString(R.string.credit)
                    else -> type
                }
            )
            binding.type.isSecondVisible = prepaid ?: false

            // Определяется блок с характеристиками номера карты
            if (number?.length == null)
                binding.length.visibility = View.GONE
            else {
                binding.length.setText(getString(R.string.length, (number?.length ?: 0)))
                binding.length.isSecondVisible = number?.luhn ?: false
            }

            // Определяется блок с банком
            if (bank?.name == null)
                binding.bankGroup.visibility = View.GONE
            else {
                binding.bank.setText(bank?.name, bank?.city)
                binding.bankUrl.text = bank?.url
                binding.bankPhone.text = bank?.phone
            }

            // Определяется блок со страной
            if (country?.name == null)
                binding.countryGroup.visibility = View.GONE
            else {
                binding.country.setText(country?.emoji, country?.name)
                // Создаём html строку с координатами и передаём в view
                val coordinatesString =
                    "<a href=\"geo:${country?.latitude},${country?.longitude}\">" +
                            getString(
                                R.string.coordinates,
                                country?.latitude, country?.longitude
                            ) + "</a>"
                binding.countryCoordinates.text =
                    Html.fromHtml(coordinatesString, Html.FROM_HTML_MODE_COMPACT)
                // Подключаем переход по ссылкам у view с координатами
                binding.countryCoordinates.movementMethod = LinkMovementMethod.getInstance()
            }
        }
        return binding.root
    }

    // Освобождаем binding, чтобы избежать утечек памяти
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        // Ключ для передачи Bin через bundle
        const val BIN_PARAM = "bin_param"
    }
}