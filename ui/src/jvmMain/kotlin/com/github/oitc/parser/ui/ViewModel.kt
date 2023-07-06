package com.github.oitc.parser.ui

import com.github.oitc.parser.ui.data.MainScreenState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ViewModel(private val viewModelCoroutineScope: CoroutineScope) {

    fun onCodeInputUpdated(codeInput: String) {
        viewModelCoroutineScope.launch {
            codeInputFlow.emit(codeInput)
        }
    }

    private companion object {
        val INITIAL_STATE = MainScreenState(codeExecutionOutput = "")
        const val CODE_INPUT_DEBOUNCE_MS = 1000L
    }

    private val _screenState = MutableStateFlow(INITIAL_STATE)
    val screenState: StateFlow<MainScreenState> = _screenState.asStateFlow()

    private var codeInputFlow = MutableStateFlow("")

    init {
        viewModelCoroutineScope.launch {
            codeInputFlow
                .debounce(CODE_INPUT_DEBOUNCE_MS)
                .collect { codeInput ->

                    // TODO process code input

                    _screenState.update { currentScreenState ->
                        val output = codeInput
                            .takeIf { it.isNotBlank() }
                            ?.let { "Input code length is ${codeInput.length}" }
                            .orEmpty()
                        currentScreenState.copy(
                            codeExecutionOutput = output
                        )
                    }
                }
        }
    }
}