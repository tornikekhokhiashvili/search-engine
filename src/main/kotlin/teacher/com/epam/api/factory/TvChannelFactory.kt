package teacher.com.epam.api.factory

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import teacher.com.epam.api.Asset

class TvChannelFactory:ContentFactory<Asset.TvChannel>() {

    override val dataList: Array<*> = arrayOf(
        Asset.TvChannel(1, "ABC"),
        Asset.TvChannel(2, "NBC"),
        Asset.TvChannel(3, "CBS"),
        Asset.TvChannel(4, "FOX"),
        Asset.TvChannel(5, "PBS"),
        Asset.TvChannel(6, "CW"),
        Asset.TvChannel(7, "National Geographic"),
        Asset.TvChannel(8, "Discovery"),
        Asset.TvChannel(9, "UPN"),
        Asset.TvChannel(10, "BBC")
    )

    override fun provideContent(): Flow<Asset.TvChannel> {
       val tvFlow=dataList.mapNotNull { tv->
           if (tv is Asset.TvChannel){
               flowOf(tv)
           }else{
               null
           }
       }
        return tvFlow.asFlow().flatMapConcat { it }
    }
}