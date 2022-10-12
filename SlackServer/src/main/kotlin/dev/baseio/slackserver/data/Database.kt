package dev.baseio.slackserver.data

import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

object Database {
  private val client = KMongo.createClient().coroutine //use coroutine extension
  val slackDB = client.getDatabase("slackDB") //normal java driver usage
}