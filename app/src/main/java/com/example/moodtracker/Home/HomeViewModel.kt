package com.example.moodtracker.Home

import androidx.annotation.StringRes
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import com.example.moodtracker.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

enum class States(
    @StringRes val text : Int,
) {
    STATE_1(R.string.state_1),
    STATE_2(R.string.state_2),
    STATE_3(R.string.state_3),
    STATE_4(R.string.state_4),
    STATE_5(R.string.state_5),

}

class HomeViewModel : ViewModel() {

    private val _mood = MutableStateFlow(2)
    val mood : StateFlow<Int> = _mood



    fun onMoodChanged(newMood : Int){
        _mood.value = newMood
    }

    fun moodTextRes(): Int =
        when (_mood.value) {
            0 -> States.STATE_1.text
            1 -> States.STATE_2.text
            2 -> States.STATE_3.text
            3 -> States.STATE_4.text
            4 -> States.STATE_5.text
            else -> States.STATE_3.text
        }

}