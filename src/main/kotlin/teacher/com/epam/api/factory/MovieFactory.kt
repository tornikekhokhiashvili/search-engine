package teacher.com.epam.api.factory

import kotlinx.coroutines.flow.*
import teacher.com.epam.api.Asset.Movie
import java.util.*

class MovieFactory: ContentFactory<Movie>() {
    override val dataList: Array<Pair<String,Long>> = arrayOf(
       "Harry Potter and the Sorcerer's Stone" to 1005861600000L,
        "28 Weeks Later" to  1178830800000L,
        "Beowulf" to  1195596000000L,
        "The Seven Deadly Sins" to  1416088800000L,
        "Die Hard" to  585345600000L,
        "Rocky" to  217371600000L,
        "Doctor Strange" to  1477342800000L,
        "Braveheart" to  801262800000L,
        "Beauty and the Beast" to  1487800800000L,
        "Seven" to  811717200000L
    )

    override fun provideContent(): Flow<Movie> {
        return List(dataList.size) { index ->
            val (label, releaseYear) = dataList[index]
            Movie(
                label = label,
                releaseYear = Date(releaseYear)
            )
        }.asFlow()
    }
}