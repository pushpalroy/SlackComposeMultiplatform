package dev.baseio.slackserver.data

import com.mongodb.reactivestreams.client.MongoDatabase
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

object Database {
  private val client = KMongo.createClient() //use coroutine extension
  val slackDB: MongoDatabase = client.getDatabase("slackDB") //normal java driver usage
}