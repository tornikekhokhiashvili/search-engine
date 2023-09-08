package teacher.com.epam

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import teacher.com.epam.api.Asset
import teacher.com.epam.engine.SearchResult

/*
TODO: write a program, which should read user's input and shows the result.
      Main logic is described in Readme.md. There are some additional requirements:
    * your implementation should use [DependencyProvider] to obtain objects.
 */

@kotlinx.coroutines.ExperimentalCoroutinesApi
fun main() = runBlocking {
    val dispatcher = Dispatchers.IO
    val searchEngine = DependencyProvider.provideEngine(dispatcher)

    println("Welcome to the Online Cinema Search Engine!")
    println("Type 'exit' to quit the program.")

    var userInput: String? = readLine()

    while (userInput != null && !userInput.isEndProgram()) {
        if (userInput.isNotBlank()) {
            val searchResults = searchEngine.searchContentAsync(userInput)
            searchResults.collect { result ->
                displaySearchResult(result)
            }
        } else {
            println("Error: Please enter a valid search query.")
        }

        userInput = readLine()
    }

    println("Goodbye! Thanks for using the Online Cinema Search Engine.")
}

private fun String.isEndProgram(): Boolean = this.equals("exit", ignoreCase = true)
private fun displaySearchResult(result: SearchResult) {
    println("Search results for ${result.type.toGroupName()}:")
    println(result.groupName)

    if (result.assets.isEmpty()) {
        println("No results found.")
    } else {
        result.assets.forEach { asset ->
            when (asset.type) {
                Asset.Type.VOD -> displayMovie(asset)
                Asset.Type.LIVE -> displayTvChannel(asset)
                Asset.Type.CREW -> displayCast(asset)
            }
        }
    }

    println("---------------")
}

private fun displayMovie(asset: Asset) {
    if (asset is Asset.Movie) {
        println("Movie: ${asset.name} (${asset.releaseYear})")
    }
}

private fun displayTvChannel(asset: Asset) {
    if (asset is Asset.TvChannel) {
        println("TV Channel: ${asset.channelNumber}. ${asset.name}")
    }
}

private fun displayCast(asset: Asset) {
    if (asset is Asset.Cast) {
        println("Cast: ${asset.name} (${asset.filmCount} films)")
    }
}
