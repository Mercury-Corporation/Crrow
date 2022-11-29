package message

import react.VFC
import react.dom.html.ReactHTML.h2
import react.router.useParams

val Message = VFC {
    val userId = useParams()["userId"]

    h2 {
        +"Message $userId"
    }
}