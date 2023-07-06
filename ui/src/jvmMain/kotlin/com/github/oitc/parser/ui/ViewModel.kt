package com.github.oitc.parser.ui

import com.github.oitc.parser.generated.ParseException
import com.github.oitc.parser.generated.TokenMgrError
import com.github.oitc.parser.ui.domain.ProcessCodeInput
import com.github.oitc.parser.ui.model.MainScreenState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.Exception

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

                    var output = try {
                        ProcessCodeInput.invoke(codeInput)
                    } catch (ex: ParseException) {
                        ex.toString()
                    } catch (ex: TokenMgrError) {
                        ex.toString()
                    } catch (ex: Exception) {
                        "Unknown exception: $ex"
                    }

                    _screenState.update { currentScreenState ->
                        currentScreenState.copy(
                            codeExecutionOutput = output
                        )
                    }
                }
        }
    }
}