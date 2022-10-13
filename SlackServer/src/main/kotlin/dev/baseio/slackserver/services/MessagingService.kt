package dev.baseio.slackserver.services

import dev.baseio.slackdata.protos.*
import dev.baseio.slackserver.data.MessagesDataSource
import dev.baseio.slackserver.data.SkMessage
import dev.baseio.slackserver.data.UsersDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlin.coroutines.CoroutineContext

class MessagingService(
  coroutineContext: CoroutineContext = Dispatchers.IO,
  private val messagesDataSource: MessagesDataSource,
  private val usersDataSource: UsersDataSource
) : MessagesServiceGrpcKt.MessagesServiceCoroutineImplBase(coroutineContext) {
  override suspend fun saveMessage(request: SKMessage): SKMessage {
    return messagesDataSource
      .saveMessage(request.toDBMessage())
      .toGrpc()
  }

  override fun subscribeToChanges(request: SKWorkspaceChannelRequest): Flow<SKMessage> {
    return messagesDataSource.registerForChanges(request).map {
      it.toGrpc()
    }
  }

  override fun getMessages(request: SKWorkspaceChannelRequest): SKMessages {
    val messages = messagesDataSource.getMessages(workspaceId = request.workspaceId, channelId = request.channelId)
      .map { skMessage ->
        val user = usersDataSource.getUser(skMessage.sender, skMessage.workspaceId)
        user?.let {
          skMessage.toGrpc().copy {
            senderInfo = it.toGrpc()
          }
        } ?: run {
          skMessage.toGrpc()
        }
      }
    return SKMessages.newBuilder()
      .addAllMessages(messages)
      .build()
  }
}

private fun SkMessage.toGrpc(): SKMessage {
  return SKMessage.newBuilder()
    .setUuid(this.uuid)
    .setCreatedDate(this.createdDate.toLong())
    .setModifiedDate(this.modifiedDate.toLong())
    .setWorkspaceId(this.workspaceId)
    .setChannelId(this.channelId)
    .setReceiver(this.receiver)
    .setSender(this.sender)
    .setText(this.message)
    .build()
}

private fun SKMessage.toDBMessage(): SkMessage {
  return SkMessage(
    uuid = this.uuid,
    workspaceId = this.workspaceId,
    channelId,
    text,
    receiver,
    sender,
    createdDate,
    modifiedDate
  )
}
