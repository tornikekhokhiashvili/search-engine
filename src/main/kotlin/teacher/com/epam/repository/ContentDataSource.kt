package teacher.com.epam.repository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import teacher.com.epam.api.Asset
import teacher.com.epam.api.Asset.Type

@ExperimentalCoroutinesApi
class ContentDataSource: SearchRepository {
    override suspend fun searchContentAsync(query: teacher.com.epam.engine.Query): Flow<Asset> {
        // Simulate a list of assets (replace with actual data source)
        val assetList = listOf(
            Asset.Cast("Adriana Ferdynand", 1),
            Asset.Cast("Walenty Kuba", 2) ,
            Asset.Movie("Harry Potter and the Sorcerer's Stone", 1005861600000L),
            Asset.Movie("28 Weeks Later", 1178830800000L)
        )

        // Filter assets based on the query type
        val filteredAssets = assetList.filter { asset ->
            when (Query.typeVOD) {
                Type.VOD -> asset is Asset.Movie
                else -> true // Include all assets when type is null
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

        // Convert the filtered assets to a Flow and return
        return filteredAssets.asFlow()
    }

}