package com.github.oitc.parser.ui

import com.github.oitc.parser.generated.ParseException
import com.github.oitc.parser.generated.TokenMgrError
import com.github.oitc.parser.ui.domain.ProcessCodeInput
import com.github.oitc.parser.ui.model.ErrorHighlight
import com.github.oitc.parser.ui.model.MainScreenState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.isActive
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
    private var codeExecutionOutputFlow = MutableStateFlow("")
    private var codeExecutionErrorFlow = MutableStateFlow(ErrorHighlight())

    init {
        viewModelCoroutineScope.launch {
            codeInputFlow.onEach { resetScreenStateErrorHighlight() }.debounce(CODE_INPUT_DEBOUNCE_MS)
                .collectAsCodeInput()
        }

        viewModelCoroutineScope.launch {
            codeExecutionOutputFlow.collectAsCodeExecutionOutput()
        }

        viewModelCoroutineScope.launch {
            codeExecutionErrorFlow.drop(1).collectAsCodeExecutionError()
        }
    }

    private var codeExecutionJob: Job? = null

    private suspend fun Flow<String>.collectAsCodeInput() = collect { codeInput ->
        codeExecutionJob?.cancel()
        codeExecutionJob = viewModelCoroutineScope.launch {
            var output = try {
                if (isActive) {
                    ProcessCodeInput.invoke(codeInput)
                } else {
                    ""
                }
            } catch (ex: ParseException) {
                codeExecutionErrorFlow.emit(ex.toErrorHighlight(codeInput))
                ex.toString()
            } catch (ex: TokenMgrError) {
                ex.toString()
            } catch (ex: Exception) {
                "Unknown exception: $ex"
            }

            codeExecutionOutputFlow.emit(output)
        }
    }

    private suspend fun Flow<String>.collectAsCodeExecutionOutput() = collect { codeExecutionOutput ->
        _screenState.update { currentScreenState ->
            currentScreenState.copy(
                codeExecutionOutput = codeExecutionOutput, errorHighlight = null
            )
        }
    }

    private suspend fun Flow<ErrorHighlight>.collectAsCodeExecutionError() = collect { errorHighlight ->
        _screenState.update { currentScreenState ->
            currentScreenState.copy(
                errorHighlight = errorHighlight
            )
        }
    }

    private fun resetScreenStateErrorHighlight() {
        _screenState.update { currentScreenState ->
            currentScreenState.copy(
                errorHighlight = null
            )
        }
    }
}