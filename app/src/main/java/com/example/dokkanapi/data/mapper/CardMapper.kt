// CardMapper.kt
package com.tuapp.dokkanbattle.data.mapper

import com.tuapp.dokkanbattle.data.model.Card

object CardMapper {

    fun mapToCard(data: Map<String, Any>): Card {
        return Card(
            id = data["id"]?.toString() ?: "",
            title = data["Card's title"]?.toString()
                ?: data["title"]?.toString()
                ?: "Sense títol",
            name = data["character"]?.toString()
                ?: data["name"]?.toString()
                ?: "Desconegut",
            type = data["Card's Type"]?.toString()
                ?: data["type"]?.toString()
                ?: "UNKNOWN",
            rarity = data["Card's Rarity"]?.toString()
                ?: data["rarity"]?.toString()
                ?: "UNKNOWN",
            hp = (data["hp"] as? Number)?.toInt() ?: 0,
            attack = (data["atk"] as? Number)?.toInt() ?: 0,
            defense = (data["def"] as? Number)?.toInt() ?: 0,
            cost = (data["cost"] as? Number)?.toInt() ?: 0,
            leaderSkill = data["leader_skill"]?.toString()
                ?: data["leaderSkill"]?.toString()
                ?: "",
            superAttack = data["super_attack"]?.toString()
                ?: data["superAttack"]?.toString()
                ?: "",
            passiveSkill = data["passive_skill"]?.toString()
                ?: data["passiveSkill"]?.toString()
                ?: "",
            linkSkills = (data["link_skills"] as? List<*>)?.map { it?.toString() ?: "" }
                ?: emptyList(),
            categories = (data["categories"] as? List<*>)?.map { it?.toString() ?: "" }
                ?: emptyList(),
            jpRelease = data["jp_release"]?.toString(),
            globalRelease = data["global_release"]?.toString()
        )
    }

    fun mapToCardList(dataList: List<Map<String, Any>>): List<Card> {
        return dataList.map { mapToCard(it) }
    }
}