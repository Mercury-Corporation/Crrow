package explore

import react.VFC
import react.dom.html.ReactHTML.h2
import react.router.useParams

val ExploreCategory = VFC {
    val category = useParams()["category"]

    h2 {
        +"Explore Category $category"
    }
}