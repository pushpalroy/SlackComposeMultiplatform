package dev.baseio.slackserver.data

import kotlinx.coroutines.flow.Flow

interface WorkspaceDataSource {
  fun getWorkspaces(): Flow<List<SkWorkspace>>
  fun saveWorkspace(skWorkspace: SkWorkspace): SkWorkspace
  fun findWorkspacesForEmail(email: String): List<SkWorkspace>
  fun findWorkspaceForName(name: String): SkWorkspace?
}


data class SkWorkspace(
  val uuid: String,
  val name: String,
  val domain: String,
  val picUrl: String?,
  val lastSelected: Boolean = false
)