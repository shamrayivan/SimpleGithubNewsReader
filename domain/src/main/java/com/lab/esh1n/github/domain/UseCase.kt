package com.lab.esh1n.github.domain

interface UseCase<ARG, RESULT> {
    fun execute(argument: ARG) : RESULT
}