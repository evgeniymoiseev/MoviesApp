package com.example.moviesapp.util.mappers

interface Mapper<S, D> {
    fun map(source: S): D
}