package dev.baseio.slackserver.data

import kotlinx.coroutines.flow.Flow

interface UsersDataSource {
  fun saveUser(skUser: SkUser): SkUser
  fun getUsers(workspaceId: String): Flow<SkUser>
  suspend fun getUser(userId: String, workspaceId: String): SkUser?
}