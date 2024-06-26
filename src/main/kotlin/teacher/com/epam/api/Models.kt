package teacher.com.epam.api

import java.text.SimpleDateFormat
import java.util.*

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

    }

    abstract val type: Type

    /** Title of the asset, which holds all necessary information about the asset*/
    //TODO: should be used in [SearchApi] to match search query.
    abstract fun getPoster(): String

    data class Movie(
        val label: String,
        val releaseYear: Date,
        override val type: Type = Type.VOD
    ) : Asset() {
        override fun getPoster(): String = label
        override fun toString(): String = "$label (${SimpleDateFormat("dd.MM.yyyy").format(releaseYear)})"
    }
    data class TvChannel(
        val label: String,
        val number: Int,
        override val type: Type = Type.LIVE
    ) : Asset() {
        override fun getPoster(): String = label
        override fun toString(): String = "№$number. $label"
    }
    data class Cast(
        val name: String,
        val surname: String,
        val filmCount: Int,
        override val type: Type = Type.CREW
    ) : Asset() {
        override fun getPoster(): String = "$name $surname"
        override fun toString(): String = "${getPoster()} ($filmCount films)"
    }
}