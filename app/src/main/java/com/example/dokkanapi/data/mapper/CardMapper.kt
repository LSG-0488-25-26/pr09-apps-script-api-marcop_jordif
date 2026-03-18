package com.example.dokkanapi.data.mapper

import com.example.dokkanapi.data.model.Card
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

object CardMapper {

    fun mapToCard(data: JsonObject): Card {
        fun str(vararg keys: String): String {
            for (key in keys) {
                val v = data[key]?.jsonPrimitive?.contentOrNull
                if (!v.isNullOrEmpty()) return v
            }
            return ""
        }

        fun strOrNull(vararg keys: String): String? {
            for (key in keys) {
                val v = data[key]?.jsonPrimitive?.contentOrNull
                if (!v.isNullOrEmpty()) return v
            }
            return null
        }

        fun int(vararg keys: String): Int {
            for (key in keys) {
                val v = data[key]?.jsonPrimitive?.intOrNull
                if (v != null) return v
            }
            return 0
        }

        fun list(vararg keys: String): List<String> {
            for (key in keys) {
                val arr = runCatching { data[key]?.jsonArray }.getOrNull()
                if (arr != null) return arr.map { it.jsonPrimitive.contentOrNull ?: "" }
            }
            return emptyList()
        }

        return Card(
            id = str("id"),
            title = str("Card's title", "title"),
            name = str("character", "name"),
            type = str("Card's Type", "type").ifEmpty { "UNKNOWN" },
            rarity = str("Card's Rarity", "rarity").ifEmpty { "UNKNOWN" },
            hp = int("hp"),
            attack = int("atk", "attack"),
            defense = int("def", "defense"),
            cost = int("cost"),
            leaderSkill = str("leader_skill", "leaderSkill"),
            superAttack = str("super_attack", "superAttack"),
            passiveSkill = str("passive_skill", "passiveSkill"),
            linkSkills = list("link_skills", "linkSkills"),
            categories = list("categories"),
            jpRelease = strOrNull("jp_release", "jpRelease"),
            globalRelease = strOrNull("global_release", "globalRelease")
        )
    }

    fun mapToCardList(dataList: List<JsonObject>): List<Card> {
        return dataList.map { mapToCard(it) }
    }
}