package dev.baseio.slackserver.data

import kotlinx.coroutines.flow.Flow

interface MessagesDataSource {
  fun saveMessage(request: SkMessage): SkMessage
  fun getMessages(workspaceId: String, channelId: String): Flow<List<SkMessage>>
}

data class SkMessage(
  val uuid: String,
  val workspaceId: String,
  val channelId: String,
  val message: String,
  val receiver: String,
  val sender: String,
  val createdDate: Long,
  val modifiedDate: Long,
  var senderInfo: SkUser? = null
)

data class SKLastMessage(
  val channel: SkChannel,
  val message: SkMessage
)