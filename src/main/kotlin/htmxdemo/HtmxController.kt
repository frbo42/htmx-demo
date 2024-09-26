package htmxdemo

import kotlinx.html.*
import kotlinx.html.dom.createHTMLDocument
import kotlinx.html.dom.serialize
import kotlinx.html.stream.createHTML
import org.springframework.http.MediaType
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@RestController("/")
class HtmxController {

    var searchResults: List<String> = listOf("one", "two", "three", "four", "five")

    @GetMapping("/", produces = [MediaType.TEXT_HTML_VALUE])
    fun home(): String {

        val doc = createHTMLDocument().html {
            lang = "en"
            head{
                title { +"HTMX Demo" }
                htmxScript()
                metas()
                styles()
            }
            body {
                section {
                    h1 { +"Htmx Demo" }
                    div {
                        id = "parent-div"
                    }
                    button {
                        hxPost("/clicked")
                        hxTrigger("click")
                        hxTarget("#parent-div")
                        hxSwap("outerHTML")
                        +"Click me"
                    }
                }
                section {
                    input {
                        type = InputType.text
                        name = "q"
                        hxGet("/search")
                        hxTrigger("keyup changed delay:500ms")
                        hxTarget("#search-results")
                        placeholder="Search..."
                    }
                    div {
                        id ="search-results"
                    }

                }
            }
        }

        return doc.serialize()
    }


    @GetMapping("/search")
    fun search(q: String): String {
        val filtered: List<String> = searchResults
            .stream()
            .filter { s: String -> s.startsWith(q.lowercase(Locale.getDefault())) }
            .toList()

        return createHTML().div {
            id = "search-results"
            ul {
                for (result in filtered) {
                    li {
                        +result
                    }
                }
            }
        }
    }

    @PostMapping("/clicked")
    fun clicked(model: Model): String {
        val formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd.MM.yy")
        val now = LocalDateTime.now().format(formatter)
        val div = createHTML().div {
            id = "parent-div"
            p {
                +now
            }
        }

        return div
    }

    private fun HEAD.metas() {
        meta {
            httpEquiv = "Content-Type"
            content = "text/html; charset=UTF-8"
        }
        meta {
            name="viewport"
            content="width=device-width, initial-scale=1"
        }
        meta {
            name="color-scheme"
            content="light dark"
        }
    }

    private fun HEAD.styles() {
        link{
            rel = "stylesheet"
            href="https://cdn.jsdelivr.net/npm/@picocss/pico@2/css/pico.min.css"
        }
    }

    private fun HEAD.htmxScript() {
        script {
            src="https://unpkg.com/htmx.org@2.0.1"
        }
    }
}