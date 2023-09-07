package teacher.com.epam.repository

import teacher.com.epam.api.Asset
import teacher.com.epam.api.SearchApi

/**
 * Represents concrete type of search request to cover
 * all search cases in [SearchApi]
 */
/*
TODO: add all necessary subclasses to satisfy [SearchRepository] contract
      and cover all [SearchApi] cases.
 */
sealed class Query(val input: String) {

    companion object {
        enum class Type {
            VOD, LIVE, CREW
        }

        val typeVOD: Type = Type.VOD
        val typeLIVE: Type = Type.LIVE
        val typeCREW: Type = Type.CREW
        fun of(input: String): Query {
            return when {
                input.endsWith("?VOD") -> ContainsWithType(input.dropLast(4), Asset.Type.VOD)
                input.endsWith("?LIVE") -> ContainsWithType(input.dropLast(5), Asset.Type.LIVE)
                input.endsWith("?CREW") -> ContainsWithType(input.dropLast(5), Asset.Type.CREW)
                else -> GenericSearch(input)
            }
        }
    }

    // Subclass for a generic search without specifying asset type
    data class GenericSearch(val queryText: String) : Query(queryText)

    // Subclass for searching by contains without specifying asset type
    data class ContainsSearch(val queryText: String) : Query(queryText)

    // Subclass for searching by contains with a specified asset type
    data class ContainsWithType(val queryText: String, val assetType: Asset.Type) : Query(queryText)

    // Subclass for searching by starts with without specifying asset type
    data class StartsWithSearch(val queryText: String) : Query(queryText)

    // Subclass for searching by starts with a specified asset type
    data class StartsWithWithType(val queryText: String, val assetType: Asset.Type) : Query(queryText)

}
