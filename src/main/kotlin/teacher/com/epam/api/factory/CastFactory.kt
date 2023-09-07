package teacher.com.epam.api.factory

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import teacher.com.epam.api.Asset.Cast


class CastFactory:ContentFactory<Cast>() {
    override val dataList: Array<*> = arrayOf(
        Cast("Adriana Ferdynand", 1),
        Cast("Walenty Kuba", 2),
        Cast("Jarek Franciszka", 3),
        Cast("Quintella Hayley", 4),
        Cast("Fraser Starr", 5),
        Cast("Wallis Chuck", 6),
        Cast("Nino Avksenti", 7),
        Cast("Daviti Ketevan", 8),
        Cast("Ioane Korneli", 9),
        Cast("Mariami Nika", 10)
    )


    override fun provideContent(): Flow<Cast> {
        val castFlows = dataList.mapNotNull { castMember ->
            if (castMember is Cast) {
                flowOf(castMember)
            } else {
                null
            }
        }
        return castFlows.asFlow().flatMapConcat { it }
    }
}