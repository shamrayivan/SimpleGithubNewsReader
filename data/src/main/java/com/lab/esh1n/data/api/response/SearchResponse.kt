package com.lab.esh1n.data.api.response

import com.google.gson.annotations.SerializedName
import retrofit2.Response
import java.util.regex.Pattern

/**
 * Created by esh1n on 3/8/18.
 */

class SearchResponse<T> {


    @SerializedName("total_count")
    val totalCount: Long = 0
    @SerializedName("items")
    val items: List<T>? = null

    class PaginationInfo constructor(val nextPage: Int, val lastPage: Int)

    companion object {
        private val LINK_PATTERN = Pattern
                .compile("<([^>]*)>[\\s]*;[\\s]*rel=\"([a-zA-Z0-9]+)\"")
        private val PAGE_PATTERN = Pattern.compile("\\bpage=(\\d+)")
        private val PAGINATION_HEADER = "link"
        private val NEXT_LINK = "next"
        private val LAST_LINK = "next"

        fun getPaginationInfo(response: Response<*>): PaginationInfo {
            val header = response.headers().get(PAGINATION_HEADER)
            var nextPage = -1
            var lastPage = -1

            if (header != null) {
                val matcher = LINK_PATTERN.matcher(header)
                while (matcher.find()) {
                    val pageLink = matcher.group(1)
                    val pageMatcher = PAGE_PATTERN.matcher(pageLink)
                    if (pageMatcher.find()) {
                        val page = Integer.parseInt(pageMatcher.group(1))
                        val relLink = matcher.group(2)
                        if (relLink == NEXT_LINK) {
                            nextPage = page
                        } else if (relLink == LAST_LINK) {
                            lastPage = page
                        }
                    }

                }

                return PaginationInfo(nextPage, lastPage)
            }

            return PaginationInfo(1, 1)
        }
    }
}