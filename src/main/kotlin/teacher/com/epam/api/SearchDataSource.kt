package teacher.com.epam.api

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import teacher.com.epam.api.factory.ContentFactory

@ExperimentalCoroutinesApi
class SearchDataSource(
    private val movieFactory: ContentFactory<Asset.Movie>,
    private val tvChannelFactory: ContentFactory<Asset.TvChannel>,
    private val castFactory: ContentFactory<Asset.Cast>
):SearchApi {
    override fun searchByContains(query: String): Flow<Asset> {
        return searchAllContentFactories(query) { it.contains(query, ignoreCase = true) }
    }

    override fun searchByContains(query: String, type: Asset.Type): Flow<Asset> {
        return when (type) {
            Asset.Type.VOD -> flow {
                val assets = searchContentFactory(movieFactory) { it.contains(query, ignoreCase = true) }
                emitAll(assets)
            }
            Asset.Type.LIVE -> flow {
                val assets = searchContentFactory(tvChannelFactory) { it.contains(query, ignoreCase = true) }
                emitAll(assets)
            }
            Asset.Type.CREW -> flow {
                val assets = searchContentFactory(castFactory) { it.contains(query, ignoreCase = true) }
                emitAll(assets)
            }
        }
    }

    override  fun searchByStartWith(query: String): Flow<Asset> {
        return searchAllContentFactories(query) { it.startsWith(query, ignoreCase = true) }
    }

    override  fun searchByStartWith(query: String, type: Asset.Type): Flow<Asset> {
        return when (type) {
            Asset.Type.VOD -> flow {
                val assets = searchContentFactory(movieFactory) { it.startsWith(query, ignoreCase = true) }
                emitAll(assets)
            }
            Asset.Type.LIVE -> flow {
                val assets = searchContentFactory(tvChannelFactory) { it.startsWith(query, ignoreCase = true) }
                emitAll(assets)
            }
            Asset.Type.CREW -> flow {
                val assets = searchContentFactory(castFactory) { it.startsWith(query, ignoreCase = true) }
                emitAll(assets)
            }
        }
    }
    private  fun searchAllContentFactories(query: String, predicate: (String) -> Boolean): Flow<Asset> {
        val movieFlow = movieFactory.provideContent().filter { predicate(it.getPoster()) }
        val tvChannelFlow = tvChannelFactory.provideContent().filter { predicate(it.getPoster()) }
        val castFlow = castFactory.provideContent().filter { predicate(it.getPoster()) }

        return flowOf(movieFlow, tvChannelFlow, castFlow).flatMapConcat { it }
    }
    private fun <T : Asset> searchContentFactory(
        contentFactory: ContentFactory<T>,
        predicate: (String) -> Boolean
    ): Flow<Asset> {
        return contentFactory.provideContent().filter { predicate(it.getPoster()) }
    }
}