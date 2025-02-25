package uz.gita.otabek.ebooklib.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import uz.gita.otabek.ebooklib.screens.book.BookContract
import uz.gita.otabek.ebooklib.screens.book.BookDirections
import uz.gita.otabek.ebooklib.screens.splash.SplashContract
import uz.gita.otabek.ebooklib.screens.splash.SplashDirections
import uz.gita.otabek.ebooklib.screens.tabs.favourites.FavoriteContracts
import uz.gita.otabek.ebooklib.screens.tabs.favourites.FavoriteDirections
import uz.gita.otabek.ebooklib.screens.tabs.home.HomeContracts
import uz.gita.otabek.ebooklib.screens.tabs.home.HomeDirections
import uz.gita.otabek.ebooklib.screens.tabs.info.InfoContracts
import uz.gita.otabek.ebooklib.screens.tabs.info.InfoDirections

@Module
@InstallIn(ViewModelComponent::class)
interface DirectionModule {

    @[Binds ViewModelScoped]
    fun bindHomeDirection(impl: HomeDirections): HomeContracts.Directions

    @[Binds ViewModelScoped]
    fun bindFavoriteDirection(impl: FavoriteDirections): FavoriteContracts.Directions

    @[Binds ViewModelScoped]
    fun bindBookDirection(impl: BookDirections): BookContract.Direction

    @[Binds ViewModelScoped]
    fun bindSplashDirection(impl: SplashDirections): SplashContract.Direction

    @[Binds ViewModelScoped]
    fun bindInfoDirection(impl: InfoDirections): InfoContracts.Directions
}