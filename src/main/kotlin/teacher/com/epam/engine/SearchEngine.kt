package teacher.com.epam.engine

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import teacher.com.epam.api.Asset
import teacher.com.epam.repository.SearchRepository

/**
 * In Clean Architecture this class should be named as UseCase or Interactor.
 * All necessary logic should be implemented here.
 */
/*
TODO: implement all business logic which converts input stream to [Query]
      and then used to in [SearchRepository]
 * your implementation should receive CoroutineDispatcher in constructor
   (it gives possibility to write tests)
 * flow should be executed on provided CoroutineDispatcher
 */
class SearchEngine(
    private val repository: SearchRepository,
    private val dispatcher: CoroutineDispatcher
) {
    private val regex: Regex = Regex("(\\?\\w{3,4}\$)")

     fun searchContentAsync(rawInput: String): Flow<SearchResult> {
        return flow {
            val (queryText, typeString) = parseQueryAndType(rawInput)
            val assetType = mapTypeStringToAssetType(typeString)
            val query = Query(queryText, typeString)
            val results = repository.searchContentAsync(query).toList()
            val groupName = assetType.toGroupName()
            val searchResult = SearchResult(results, assetType, groupName)

            emit(searchResult)
        }.flowOn(dispatcher)
    }
    private fun parseQueryAndType(rawInput: String): Pair<String, String?> {
        val match = regex.find(rawInput)
        val query = match?.let { rawInput.substring(0, it.range.start).trim() } ?: rawInput.trim()
        val typeString = match?.let { it.value.substring(1) }
        return Pair(query, typeString)
    }
    private fun mapTypeStringToAssetType(typeString: String?): Asset.Type {
        if (typeString == "VOD") {
            return Asset.Type.VOD
        } else if (typeString == "LIVE") {
            return Asset.Type.LIVE
        } else if (typeString == "CREW") {
            return Asset.Type.CREW
        }else return Asset.Type.VOD

    }
}
data class Query(val text: String, val type: String?)
private fun Asset.Type.toGroupName(): String {
    return when (this) {
        Asset.Type.VOD -> "-- Movies --"
        Asset.Type.LIVE -> "-- TvChannels --"
        Asset.Type.CREW -> "-- Cast and Crew --"
    }
}
