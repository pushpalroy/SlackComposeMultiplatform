package dev.baseio.slackserver.data.impl

import at.favre.lib.crypto.bcrypt.BCrypt
import dev.baseio.slackserver.data.AuthDataSource
import dev.baseio.slackserver.data.SkAuthUser
import dev.baseio.slackserver.data.SkUser
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactive.awaitSingleOrNull
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.reactivestreams.findOne
import java.util.*

class AuthDataSourceImpl(private val slackCloneDB: CoroutineDatabase) : AuthDataSource {
  override suspend fun login(email: String, password: String, workspaceId: String): SkUser? {
    val user = slackCloneDB.getCollection<SkUser>().collection
      .findOne(
        SkUser::email eq email,
        SkUser::workspaceId eq workspaceId
      )
    user.awaitFirstOrNull()?.let { user ->
      slackCloneDB.getCollection<SkAuthUser>().collection
        .findOne(SkAuthUser::uuid eq user.uuid)
        .awaitFirstOrNull()?.let {
          val result: BCrypt.Result = BCrypt.verifyer().verify(password.toCharArray(), it.password)
          if (result.verified) {
            return user
          }
        }
    }
    return null
  }

  override fun register(email: String, password: String, user: SkUser): SkUser? {
    //save the user details
    slackCloneDB
      .slackschemaQueries
      .insertUser(
        user.uuid,
        user.workspaceId,
        user.gender,
        user.name,
        user.location,
        user.email,
        user.username,
        user.userSince,
        user.phone,
        user.avatarUrl
      )
    // save the auth
    val bcryptHashString: String = BCrypt.withDefaults().hashToString(12, password.toCharArray())

    slackCloneDB.slackschemaQueries.insertAuth(
      UUID.randomUUID().toString(),
      user.uuid,
      bcryptHashString
    )
    return slackCloneDB.slackschemaQueries.getUser(user.uuid).executeAsOneOrNull()
  }
}