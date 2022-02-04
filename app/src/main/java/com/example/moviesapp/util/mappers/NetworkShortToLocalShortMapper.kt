package com.example.moviesapp.util.mappers

import com.example.moviesapp.model.local.ShortMovie
import com.example.moviesapp.model.network.NetworkShortMovie

class NetworkShortToLocalShortMapper : Mapper<NetworkShortMovie, ShortMovie> {
    override fun map(input: NetworkShortMovie): ShortMovie {
        return ShortMovie(
            id = input.id,
            image = input.image,
            title = input.title,
            description = input.description
        )
    }
}