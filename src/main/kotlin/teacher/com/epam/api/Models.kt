package teacher.com.epam.api

/*
TODO: implement subclasses of asset as described bellow
 * Movie (represents VOD)
    - in [SearchResult] should looks like -> "The Seven Deadly Sins (16.11.2014)"
    - but searching among this class should ignore release year.
      For example: query -> `1` shouldn't find "The Seven Deadly Sins (16.11.2014)"
 * TvChannel (represents LIVE)
    - in [SearchResult] should look like -> "№2. Football 1"
    - but searching among this class should ignore channel number.
       For example: query -> `2` shouldn't find "№2. Football 1"
  * Cast (represents CREW)
    - in [SearchResult] should look like -> "Jarek Franciszka (3 films)"
    - but searching among this class should ignore film counter.
       For example: query -> `3` shouldn't find "Jarek Franciszka (3 films)"
 */
abstract class Asset {

    enum class Type {
        /**
         * Video on demand (movies, serials, trailers e.t.c). This is just a simple video file,
         * which are stored on the server and can be converted to the video stream.
         * */
        VOD,

        /**
         * Video content, which are currently streaming ont the server
         * (TV channels, podcasts, e.t.c).
         */
        LIVE,

        /**
         * All people, who are participated in video making process
         * (actors, director, operator e.t.c).
         */
        CREW;


        fun toGroupName(): String {
            return when (this) {
                Type.VOD -> "-- Movies --"
                Type.LIVE -> "-- TvChannels --"
                Type.CREW -> "-- Cast and Crew --"
                else->"null"
            }
        }
    }

    abstract val type: Type

    /** Title of the asset, which holds all necessary information about the asset*/
    //TODO: should be used in [SearchApi] to match search query.
    abstract fun getPoster(): String
    data class Movie(val name: String, val releaseYear: Long) : Asset() {
        override val type: Type = Type.VOD

        override fun getPoster(): String {
            return "$name (${releaseYearToDateString(releaseYear)})"
        }
    }
    data class TvChannel(val channelNumber: Int, val name: String) : Asset() {
        override val type: Type = Type.LIVE

        override fun getPoster(): String {
            return "№$channelNumber. $name"
        }
    }
    data class Cast(val name: String, val filmCount: Int) : Asset() {
        override val type: Type = Type.CREW

        override fun getPoster(): String {
            return "$name ($filmCount films)"
        }
    }

    fun releaseYearToDateString(releaseYear: Long): String {
        // Convert Unix timestamp to a human-readable date format
        // You can use a date formatting library or Kotlin's Date/Time API here
        // For simplicity, this example assumes a simple conversion
        val date = java.util.Date(releaseYear)
        val dateFormat = java.text.SimpleDateFormat("dd.MM.yyyy")
        return dateFormat.format(date)
    }

}