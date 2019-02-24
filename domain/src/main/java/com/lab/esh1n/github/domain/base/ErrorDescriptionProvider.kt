package com.lab.esh1n.github.domain.base

interface ErrorDescriptionProvider {
    fun getHumanReadableError(error: ErrorModel): String
}