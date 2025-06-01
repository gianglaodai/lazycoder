package io.lazycoder.infras.templating

import java.nio.file.Paths
import kotlin.io.path.exists
import kotlin.io.path.useLines
import kotlin.random.Random
import kotlinx.html.HTML
import kotlinx.html.TBODY
import kotlinx.html.body
import kotlinx.html.button
import kotlinx.html.h1
import kotlinx.html.h2
import kotlinx.html.head
import kotlinx.html.id
import kotlinx.html.link
import kotlinx.html.script
import kotlinx.html.style
import kotlinx.html.table
import kotlinx.html.tbody
import kotlinx.html.td
import kotlinx.html.th
import kotlinx.html.thead
import kotlinx.html.title
import kotlinx.html.tr

fun HTML.leaderboardPage(random: Random) {
    head {
        title("HTMX Example")
        script(src = "/leaderboard.js") {}
        link(rel = "stylesheet", href = "/leaderboard.css")
    }
    body {
        h1 {
            +"Leaderboard"
        }
        table {
            id = "leaderboard"
            thead {
                tr {
                    th { +"Alias" }
                    th { +"Score" }
                }
            }
            tbody {
                randomRows(random)
            }
        }
        h2 {
            id = "total-count"
            +"Total: 10"
        }
    }
}

fun TBODY.randomRows(random: Random) {
    for (contestant in generateSequence { random.nextContestant() }.take(10)) {
        tr {
            td { +contestant.alias }
            td { +contestant.score.toString() }
        }
    }
    loadMoreRows()
}

fun TBODY.loadMoreRows() {
    tr {
        id = "replaceMe"
        td {
            colSpan = "3"
            style = "text-align: center;"

            button {
                attributes["hx-get"] = "/more-rows"
                attributes["hx-target"] = "#replaceMe"
                attributes["hx-swap"] = "outerHTML"
                attributes["hx-trigger"] = "click"
                attributes["hx-select"] = "tr"

                +"Load More..."
            }
        }
    }
}

private val dictionary: List<String> by lazy {
    listOf("/usr/share/dict/words", "/usr/dict/words").map(Paths::get).firstOrNull {
        it.exists()
    }?.let { dictionaryFile ->
        dictionaryFile.useLines(Charsets.UTF_8) { lines ->
            lines.filter { it.length >= 4 }.toList()
        }
    } ?: listOf("red", "blue", "green", "yellow", "pink", "violet", "black")
}

fun Random.nextContestant(): Contestant =
    Contestant(
        nextAlias(),
        nextInt(1_000_000)
    )

fun Random.nextAlias(): String =
    dictionary[nextInt(dictionary.size - 1)]

data class Contestant(
    val alias: String,
    val score: Int,
)