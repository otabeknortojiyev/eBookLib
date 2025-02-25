package uz.gita.otabek.ebooklib.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uz.gita.otabek.ebooklib.local.BookDataBase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @[Provides Singleton]
    fun provideBookDatabase(@ApplicationContext context: Context): BookDataBase =
        Room.databaseBuilder(context = context, BookDataBase::class.java, "Database.db")
            .allowMainThreadQueries()
            .build()
}