package com.example.moviesapp.util.mappers

interface Mapper<I, O> {
    fun map(input: I): O
}