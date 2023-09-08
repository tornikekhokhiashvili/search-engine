package teacher.com.epam.repository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import teacher.com.epam.api.Asset
import teacher.com.epam.api.Asset.Type

@ExperimentalCoroutinesApi
class ContentDataSource: SearchRepository {
    override  fun searchContentAsync(query: teacher.com.epam.engine.Query): Flow<Asset> {
        val assetList = listOf(
            Asset.Cast("Adriana Ferdynand", 1),
            Asset.Cast("Walenty Kuba", 2) ,
            Asset.Movie("Harry Potter and the Sorcerer's Stone", 1005861600000L),
            Asset.Movie("28 Weeks Later", 1178830800000L)
        )
        val filteredAssets = assetList.filter { asset ->
            when (Query.typeVOD) {
                Type.VOD -> asset is Asset.Movie
                else -> true
            }
            when(Query.typeCREW){
                Type.CREW -> asset is Asset.Cast
                else->true
            }
            when(Query.typeLIVE){
                Type.LIVE -> asset is Asset.TvChannel
                else->true
            }
        }
        return filteredAssets.asFlow()
    }

}