package dev.baseio.slackserver.data

import kotlinx.coroutines.flow.Flow

interface UsersDataSource {
  fun saveUser(skUser: SkUser): SkUser
  fun getUsers(workspaceId: String): Flow<List<SkUser>>
  fun getUser(userId: String, workspaceId: String): SkUser?
}