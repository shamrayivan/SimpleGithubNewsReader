package com.lab.esh1n.github.domain.base

abstract class TwoWayMapper<S, T> : Mapper<S, T>() {


    fun mapInverse(source: List<T>?): List<S> {
        return if (source == null) {
            emptyList()
        } else {
            source.filter { it != null }.map { mapInverse(it) }.toMutableList()
        }
    }

    abstract fun mapInverse(source: T): S
}
