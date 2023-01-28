package com.example.cardbininfo.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.view.isVisible

import com.example.cardbininfo.R
import com.example.cardbininfo.databinding.InfoBlockViewBinding

// Класс кастомного view для блоков информации
class InfoBlockView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding = InfoBlockViewBinding.inflate(LayoutInflater.from(context))

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.InfoBlockView,
            0, 0
        ).apply {
            try {
                // Заполняем текст, полученный из xml родительского layout
                setText(
                    getString(R.styleable.InfoBlockView_main_text),
                    getString(R.styleable.InfoBlockView_second_text),
                    getString(R.styleable.InfoBlockView_title_text)
                )
            } finally {
                recycle()
            }
        }
        addView(binding.root)
    }

    // Виден ли дополнительный аргумент
    var isSecondVisible: Boolean
        get() = binding.secondValue.isVisible
        set(value) {
            binding.secondValue.isVisible = value
        }

    // Функция назначения текста
    fun setText(
        main: String? = null, // Главный элемент
        second: String? = null, // Дополнительный элемент
        title: String? = null // Заголовок
    ) {
        main?.let { binding.mainValue.text = it }
        second?.let { binding.secondValue.text = it }
        title?.let { binding.title.text = it }
    }
}