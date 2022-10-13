package dev.baseio.slackserver.data.impl

import com.squareup.sqldelight.Query
import com.squareup.sqldelight.runtime.coroutines.asFlow

import dev.baseio.SlackCloneDB
import dev.baseio.slackdata.protos.SKWorkspaceChannelRequest
import dev.baseio.slackserver.data.SkUser
import dev.baseio.slackserver.data.UsersDataSource
import kotlinx.coroutines.flow.Flow
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

class UsersDataSourceImpl(private val slackCloneDB: CoroutineDatabase) : UsersDataSource {
  override suspend fun getUser(userId: String, workspaceId: String): SkUser? {
    return slackCloneDB.getCollection<SkUser>()
      .findOne(SkUser::uuid eq userId,SkUser::workspaceId eq workspaceId)
  }

  override fun getUsers(workspaceId: String): Flow<SkUser> {
    TODO("Not yet implemented")
  }

  override fun saveUser(skUser: SkUser): SkUser {
    TODO("Not yet implemented")
  }
}