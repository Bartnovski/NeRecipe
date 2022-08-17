package ru.netology.nerecipe.demo

import androidx.core.net.toUri
import androidx.lifecycle.MutableLiveData
import ru.netology.nerecipe.models.Step

class StepDemo {

    companion object {
        val data = List(3) { index ->
            Step(
                id = index + 1,
                stepContent = "Шаг №${index + 1}\n" +
                        "Привет! Это новая Нетология! Когда-то Нетология начиналась с интенсивов" +
                        "по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике" +
                        "и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных" +
                        "профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть" +
                        "сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша" +
                        " миссия - помочь встать на путь роста и начать цепочку перемен →",
                stepImagePath = "file:///mnt/sdcard/Pictures/солянка.jpg".toUri()
            )
        }
        val stepData = MutableLiveData(data)
    }
}