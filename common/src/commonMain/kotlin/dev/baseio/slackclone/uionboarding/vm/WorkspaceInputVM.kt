package dev.baseio.slackclone.uionboarding.vm

import dev.baseio.slackdata.protos.KMSKWorkspace
import dev.baseio.slackdomain.usecases.auth.LoginUseCase
import dev.baseio.slackdomain.usecases.auth.UseCaseRegisterUser
import dev.baseio.slackdomain.usecases.workspaces.FindWorkspacesUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ViewModel

class WorkspaceInputVM(
    private val loginUseCase: LoginUseCase,
    private val useCaseRegisterUser: UseCaseRegisterUser,
    private val findWorkspacesUseCase: FindWorkspacesUseCase
) : ViewModel() {
    val uiState = MutableStateFlow<UiState>(UiState.Empty)
    var workspace = MutableStateFlow("")

    fun onNextClick() {
        if (uiState.value is UiState.Workspace) {
            viewModelScope.launch(CoroutineExceptionHandler { coroutineContext, throwable ->
                uiState.value = UiState.Exception(throwable)
            }) {
                val email = (uiState.value as UiState.Workspace).email
                val password = (uiState.value as UiState.Workspace).password
                val workspaceIdd = (uiState.value as UiState.Workspace).kmskWorkspace.uuid
                uiState.value = UiState.Loading
                loginUseCase.invoke(email!!, password!!, workspaceIdd)
                uiState.value = UiState.LoggedIn
            }
        } else {
            viewModelScope.launch(CoroutineExceptionHandler { coroutineContext, throwable ->
                uiState.value = UiState.Exception(throwable)
            }) {
                uiState.value = UiState.Loading
                val name = findWorkspacesUseCase.byName(workspace.value)
                uiState.value = UiState.Workspace(name)
            }
        }
    }

    fun onLogin() {
        onNextClick()
    }

    fun onRegister() {
        viewModelScope.launch(CoroutineExceptionHandler { coroutineContext, throwable ->
            uiState.value = UiState.Exception(throwable)
        }) {
            val email = (uiState.value as UiState.Workspace).email
            val password = (uiState.value as UiState.Workspace).password
            val workspaceIdd = (uiState.value as UiState.Workspace).kmskWorkspace.uuid
            uiState.value = UiState.Loading
            useCaseRegisterUser.invoke(email!!, password!!, workspaceIdd)
            uiState.value = UiState.LoggedIn
        }
    }

    sealed class UiState {
        object Empty : UiState()
        object Loading : UiState()
        data class Workspace(
            val kmskWorkspace: KMSKWorkspace,
            var email: String? = null,
            var password: String? = null
        ) :
            UiState()

        object LoggedIn : UiState()
        data class Exception(val throwable: Throwable) : UiState()
    }
}