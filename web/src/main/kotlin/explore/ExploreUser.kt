package explore

import react.VFC
import react.dom.html.ReactHTML.h2
import react.router.useParams

val ExploreUser = VFC {
    val userId = useParams()["userId"]

    h2 {
        +"Explore User $userId"
    }
}