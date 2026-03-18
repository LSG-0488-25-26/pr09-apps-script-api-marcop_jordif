package com.example.dokkanapi.data.mapper

import com.example.dokkanapi.data.model.Card
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonPrimitive

object CardMapper {

    // Safely get string content from ANY JsonElement type
    private fun JsonElement.safeString(): String? = when (this) {
        is JsonPrimitive -> contentOrNull
        is JsonNull      -> null
        else             -> null
    }

    fun mapToCard(data: JsonObject): Card {

        fun str(vararg keys: String): String {
            for (key in keys) {
                val v = data[key]?.safeString()
                if (!v.isNullOrEmpty()) return v
            }
            return ""
        }

        fun strOrNull(vararg keys: String): String? {
            for (key in keys) {
                val v = data[key]?.safeString()
                if (!v.isNullOrEmpty()) return v
            }
            return null
        }

        // Parses "[\"foo\", \"bar\"]" strings OR actual JsonArrays
        fun list(vararg keys: String): List<String> {
            for (key in keys) {
                val element = data[key] ?: continue
                when (element) {
                    is JsonArray -> return element
                        .mapNotNull { it.safeString() }
                        .filter { it.isNotEmpty() }
                    is JsonPrimitive -> {
                        val raw = element.contentOrNull ?: continue
                        if (raw.startsWith("[")) {
                            return try {
                                Json.parseToJsonElement(raw).jsonArray
                                    .mapNotNull { it.safeString() }
                                    .filter { it.isNotEmpty() }
                            } catch (e: Exception) { emptyList() }
                        }
                    }
                    else -> continue
                }
            }
            return emptyList()
        }

        // Stats are "[\"2210\", \"8282\", \"10282\", \"13282\"]" — take last non-zero value
        fun statFromArray(vararg keys: String): Int {
            for (key in keys) {
                val element = data[key] ?: continue
                val raw = element.safeString() ?: continue
                if (raw.startsWith("[")) {
                    return try {
                        Json.parseToJsonElement(raw).jsonArray
                            .mapNotNull { it.safeString()?.toIntOrNull() }
                            .lastOrNull { it > 0 } ?: 0
                    } catch (e: Exception) { 0 }
                }
                raw.toIntOrNull()?.let { return it }
            }
            return 0
        }

        // Cost is "16/23" — take the first number (base cost)
        fun costFromSlash(key: String): Int {
            val raw = data[key]?.safeString() ?: return 0
            return raw.split("/").firstOrNull()?.trim()?.toIntOrNull() ?: 0
        }

        // ID is a number in JSON — handle both number and string forms
        fun id(): String {
            val element = data["ID"] ?: data["id"] ?: return ""
            return element.safeString()
                ?: (element as? JsonPrimitive)?.content
                ?: ""
        }

        return Card(
            id            = id(),
            title         = str("Title", "title"),
            name          = str("Character", "character", "name"),
            type          = str("Type", "type").ifEmpty { "UNKNOWN" },
            rarity        = str("Rarity", "rarity").ifEmpty { "UNKNOWN" },
            hp            = statFromArray("HP", "hp"),
            attack        = statFromArray("Attack", "atk", "attack"),
            defense       = statFromArray("Defense", "def", "defense"),
            cost          = costFromSlash("Cost"),
            leaderSkill   = str("Leader Skill", "leader_skill", "leaderSkill"),
            superAttack   = str("Super Attack Effect", "Super Attack Name", "super_attack", "superAttack"),
            passiveSkill  = str("Passive Skill", "passive_skill", "passiveSkill"),
            linkSkills    = list("Links", "link_skills", "linkSkills"),
            categories    = list("Categories", "categories"),
            jpRelease     = strOrNull("JP Release Date", "jp_release", "jpRelease"),
            globalRelease = strOrNull("Global Release Date", "global_release", "globalRelease")
        )
    }

    fun mapToCardList(dataList: List<JsonObject>): List<Card> {
        return dataList.map { mapToCard(it) }
    }
}