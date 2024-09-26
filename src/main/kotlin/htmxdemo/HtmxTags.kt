package htmxdemo

import kotlinx.html.HTMLTag

fun HTMLTag.hxGet(value: String) {
    attributes["hx-get"] = value
}

fun HTMLTag.hxPost(value: String) {
    attributes["hx-post"] = value
}

fun HTMLTag.hxTrigger(value: String) {
    attributes["hx-trigger"] = value
}

fun HTMLTag.hxTarget(value: String) {
    attributes["hx-target"] = value
}

fun HTMLTag.hxSwap(value: String) {
    attributes["hx-swap"] = value
}