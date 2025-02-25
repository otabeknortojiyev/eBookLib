package uz.gita.otabek.ebooklib.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.otabek.ebooklib.ui.navigation.AppNavigationDispatcher
import uz.gita.otabek.ebooklib.ui.navigation.AppNavigator
import uz.gita.otabek.ebooklib.ui.navigation.NavigationHandler

@Module
@InstallIn(SingletonComponent::class)
interface NavigationModule {
    @Binds
    fun bindAppNavigator(dispatcher: AppNavigationDispatcher): AppNavigator

    @Binds
    fun bindNavigationHandler(dispatcher: AppNavigationDispatcher): NavigationHandler
}