package teacher.com.epam

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import teacher.com.epam.api.Asset
import teacher.com.epam.api.SearchApi
import teacher.com.epam.api.SearchDataSource
import teacher.com.epam.api.factory.CastFactory
import teacher.com.epam.api.factory.MovieFactory
import teacher.com.epam.api.factory.TvChannelFactory
import teacher.com.epam.engine.SearchEngine
import teacher.com.epam.repository.ContentDataSource
import teacher.com.epam.repository.SearchRepository

/**
 * This is a simple implementation of Service Locator pattern.
 * It uses 'fabric' pattern to provide all classes
 * you need in one place.
 * More to read : [link](https://en.wikipedia.org/wiki/Service_locator_pattern)
 */
//TODO: add your implementation of each contract in this task
@ExperimentalCoroutinesApi
object DependencyProvider {

    fun provideEngine(dispatcher: CoroutineDispatcher): SearchEngine {
        val repository = provideRepository()
        return SearchEngine(repository, dispatcher)
    }

    private fun provideRepository(): SearchRepository {
        return ContentDataSource()
    }

//    private fun provideApi(): SearchApi {
//        val castFactory: CastFactory = CastFactory()
//        val movieFactory: MovieFactory = MovieFactory()
//        val tvChannel: TvChannelFactory = TvChannelFactory()
//        return SearchDataSource(castFactory,movieFactory,tvChannel)
//    }
}