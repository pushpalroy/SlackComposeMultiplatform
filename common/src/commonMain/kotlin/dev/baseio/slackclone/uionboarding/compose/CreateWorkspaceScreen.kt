package dev.baseio.slackclone.uionboarding.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.baseio.slackclone.LocalWindow
import dev.baseio.slackclone.commonui.theme.*
import dev.baseio.slackclone.navigation.ComposeNavigator
import dev.baseio.slackclone.uidashboard.compose.WindowSize
import dev.baseio.slackclone.uidashboard.compose.getWindowSizeClass
import dev.baseio.slackclone.uionboarding.vm.WorkspaceCreateVM

@Composable
fun CreateWorkspaceScreen(composeNavigator: ComposeNavigator, viewModel: WorkspaceCreateVM) {
  val scaffoldState = rememberScaffoldState()
  val size = getWindowSizeClass(LocalWindow.current)
  PlatformSideEffects.GettingStartedScreen()

  Scaffold(
    backgroundColor = SlackCloneColor,
    contentColor = SlackCloneColorProvider.colors.textSecondary,
    modifier = Modifier.fillMaxSize(), scaffoldState = scaffoldState, snackbarHost = {
      scaffoldState.snackbarHostState
    }
  ) { innerPadding ->
    Box(modifier = Modifier.padding(innerPadding)) {
      SlackCloneSurface(
        color = SlackCloneColor,
        modifier = Modifier
          .padding(28.dp)
      ) {
        when (size) {
          WindowSize.Phones -> CreateWorkspacePhoneLayout(composeNavigator, viewModel)
          else -> {
            CreateWorkspaceLargeScreenLayout(composeNavigator, viewModel)
          }
        }
      }
    }

  }
}

@Composable
fun Title() {
  Text(
    "Create a new workspace",
    style = SlackCloneTypography.h4.copy(
      fontWeight = FontWeight.Bold,
      color = SlackCloneColorProvider.colors.appBarTextTitleColor
    )
  )
}

@Composable
fun SubTitle() {
  Text(
    "To make a workspace from scratch, please confirm your email address.",
    style = SlackCloneTypography.h6.copy(color = SlackCloneColorProvider.colors.appBarTextSubTitleColor)
  )
}

@Composable
fun Heading() {
  Column(Modifier.fillMaxWidth()) {
    Title()
    SubTitle()
  }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun WorkspaceCreateForm(viewModel: WorkspaceCreateVM, composeNavigator: ComposeNavigator) {
  val email by viewModel.email.collectAsState()
  val name by viewModel.domain.collectAsState()
  val password by viewModel.password.collectAsState()
  val error by viewModel.error.collectAsState()
  val loading by viewModel.loading.collectAsState()
  Column(Modifier.fillMaxSize()) {
    EmailTF(Modifier.padding(8.dp), email) { emailNew ->
      viewModel.email.value = emailNew
    }
    PasswordTF(Modifier.padding(8.dp), password) { passwordNew ->
      viewModel.password.value = passwordNew
    }
    WorkspaceView(Modifier.padding(8.dp), name, viewModel)
    Spacer(Modifier.size(8.dp))
    ErrorText(Modifier.padding(8.dp), error)
    if (loading) CircularProgressIndicator(color = SlackGreen) else CreateWorkspaceButton(composeNavigator, viewModel)
  }


}

@Composable
fun ErrorText(modifier: Modifier, error: Throwable?) {
  Text(error?.message ?: "", style = SlackCloneTypography.subtitle1.copy(color = Color.White), modifier = modifier)
}

@Composable
private fun WorkspaceView(modifier: Modifier, name: String, viewModel: WorkspaceCreateVM) {
  Row(
    modifier = modifier,
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.Start
  ) {
    Icon(Icons.Default.Build, contentDescription = null, modifier = Modifier.padding(8.dp))
    TextHttps()
    WorkspaceTF(name) { nameNew ->
      viewModel.domain.value = nameNew
    }
    TextSlackCom()
  }
}

@Composable
fun CreateWorkspaceButton(composeNavigator: ComposeNavigator, viewModel: WorkspaceCreateVM) {
  Button(
    onClick = {
      viewModel.createWorkspace(composeNavigator)
    },
    Modifier
      .fillMaxWidth()
      .height(40.dp),
    colors = ButtonDefaults.buttonColors(backgroundColor = SlackGreen,
      contentColor = Color.White)
  ) {
    Text(
      text = "Create Workspace",
      style = SlackCloneTypography.subtitle1.copy(fontWeight = FontWeight.Bold)
    )
  }
}

@Composable
fun CreateWorkspaceLargeScreenLayout(composeNavigator: ComposeNavigator, viewModel: WorkspaceCreateVM) {
  Row(Modifier.fillMaxSize()) {
    Column(
      Modifier.weight(1f, fill = true).fillMaxHeight(), verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      Heading()
      WorkspaceCreateForm(viewModel, composeNavigator)
    }
    Column(
      Modifier.weight(1f, fill = true).padding(24.dp).fillMaxHeight(),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      Image(
        modifier = Modifier.size(256.dp),
        painter = PainterRes.gettingStarted(),
        contentDescription = null,
        contentScale = ContentScale.Fit
      )
    }
  }

}

@Composable
fun CreateWorkspacePhoneLayout(composeNavigator: ComposeNavigator, viewModel: WorkspaceCreateVM) {
  Column(
    modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    Heading()
    Spacer(Modifier.size(8.dp))
    WorkspaceCreateForm(viewModel, composeNavigator)
  }
}
