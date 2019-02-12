package com.lab.esh1n.github.search

/**
 * Created by esh1n on 3/16/18.
 */

data class RepositoryModel(val id: Long,
                           val name: String?,
                           val avatar: String?,
                           val starsCount: Int,
                           val watchersCount: Int,
                           val forksCount: Int,
                           val language: String?)