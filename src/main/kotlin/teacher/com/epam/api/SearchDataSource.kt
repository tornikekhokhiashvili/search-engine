package teacher.com.epam.api

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import teacher.com.epam.api.factory.ContentFactory

@ExperimentalCoroutinesApi
class SearchDataSource(
    private val movieFactory: ContentFactory<Asset.Movie>,
    private val tvChannelFactory: ContentFactory<Asset.TvChannel>,
    private val castFactory: ContentFactory<Asset.Cast>
):SearchApi {
    override suspend fun searchByContains(query: String): Flow<Asset> {
        return searchAllContentFactories(query) { it.contains(query, ignoreCase = true) }
    }

    override suspend fun searchByContains(query: String, type: Asset.Type): Flow<Asset> {
        return when (type) {
            Asset.Type.VOD -> searchContentFactory(movieFactory, query) { it.contains(query, ignoreCase = true) }
            Asset.Type.LIVE -> searchContentFactory(tvChannelFactory, query) { it.contains(query, ignoreCase = true) }
            Asset.Type.CREW -> searchContentFactory(castFactory, query) { it.contains(query, ignoreCase = true) }
        }
    }

    override suspend fun searchByStartWith(query: String): Flow<Asset> {
        return searchAllContentFactories(query) { it.startsWith(query, ignoreCase = true) }
    }

    override suspend fun searchByStartWith(query: String, type: Asset.Type): Flow<Asset> {
        return when (type) {
            Asset.Type.VOD -> searchContentFactory(movieFactory, query) { it.startsWith(query, ignoreCase = true) }
            Asset.Type.LIVE -> searchContentFactory(tvChannelFactory, query) { it.startsWith(query, ignoreCase = true) }
            Asset.Type.CREW -> searchContentFactory(castFactory, query) { it.startsWith(query, ignoreCase = true) }
        }
    }
    private suspend fun searchAllContentFactories(query: String, predicate: (String) -> Boolean): Flow<Asset> {
        val movieFlow = movieFactory.provideContent().filter { predicate(it.getPoster()) }
        val tvChannelFlow = tvChannelFactory.provideContent().filter { predicate(it.getPoster()) }
        val castFlow = castFactory.provideContent().filter { predicate(it.getPoster()) }

        return flowOf(movieFlow, tvChannelFlow, castFlow).flatMapConcat { it }
    }
    private suspend fun <T : Asset> searchContentFactory(
        contentFactory: ContentFactory<T>,
        query: String,
        predicate: (String) -> Boolean
    ): Flow<Asset> {
        return contentFactory.provideContent().filter { predicate(it.getPoster()) }
    }
}