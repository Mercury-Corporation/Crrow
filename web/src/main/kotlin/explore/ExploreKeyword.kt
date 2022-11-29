package explore

import react.VFC
import react.dom.html.ReactHTML.h2
import react.router.useParams

val ExploreKeyword = VFC {
    val keyword = useParams()["keyword"]

    h2 {
        +"Explore Keyword $keyword"
    }
}