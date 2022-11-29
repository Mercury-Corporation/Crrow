package user

import react.VFC
import react.dom.html.ReactHTML.h2
import react.router.useParams

val User = VFC {
    val userId = useParams()["userId"]

    h2 {
        +"User user ID: $userId"
    }
}