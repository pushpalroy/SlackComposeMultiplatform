package dev.baseio.slackserver.data

import kotlinx.coroutines.flow.Flow

interface ChannelsDataSource {
  fun getChannels(workspaceId:String): Flow<List<SkChannel>>
  fun insertChannel(channel: SkChannel): SkChannel
}

data class SkChannel(
  val uuid: String? = null,
  val workspaceId:String,
  val name: String? = null,
  val createdDate: Long? = null,
  val modifiedDate: Long? = null,
  val isMuted: Boolean? = null,
  val isPrivate: Boolean? = null,
  val isStarred: Boolean? = false,
  val isShareOutSide: Boolean? = false,
  val isOneToOne: Boolean?,
  val avatarUrl: String?
)