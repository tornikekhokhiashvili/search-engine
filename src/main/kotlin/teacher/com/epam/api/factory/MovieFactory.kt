package teacher.com.epam.api.factory

import kotlinx.coroutines.flow.*
import teacher.com.epam.api.Asset

class MovieFactory: ContentFactory<Asset.Movie>() {
    override val dataList: Array<*> = arrayOf(
        Asset.Movie("Harry Potter and the Sorcerer's Stone", 1005861600000L),
        Asset.Movie("28 Weeks Later", 1178830800000L),
        Asset.Movie("Beowulf", 1195596000000L),
        Asset.Movie("The Seven Deadly Sins", 1416088800000L),
        Asset.Movie("Die Hard", 585345600000L),
        Asset.Movie("Rocky", 217371600000L),
        Asset.Movie("Doctor Strange", 1477342800000L),
        Asset.Movie("Braveheart", 801262800000L),
        Asset.Movie("Beauty and the Beast", 1487800800000L),
        Asset.Movie("Seven", 811717200000L)
    )

    override fun provideContent(): Flow<Asset.Movie> {
        val movieFlows = dataList.mapNotNull { movie ->
            if (movie is Asset.Movie) {
                flowOf(movie)
            } else {
                null
            }
        }
        return movieFlows.asFlow().flatMapConcat { it }
    }
}