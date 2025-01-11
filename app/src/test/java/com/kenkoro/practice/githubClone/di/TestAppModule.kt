package com.kenkoro.practice.githubClone.di

import com.kenkoro.practice.githubClone.reposViewer.data.networking.GithubApi
import com.kenkoro.practice.githubClone.reposViewer.data.storage.KeyValueStorage
import com.kenkoro.practice.githubClone.reposViewer.domain.AppRepository
import com.kenkoro.practice.githubClone.reposViewer.presentation.reposList.util.ColorProvider
import com.kenkoro.practice.githubClone.reposViewer.presentation.reposList.util.NetworkErrorMessageProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import io.mockk.mockk
import javax.inject.Singleton

@Module
@TestInstallIn(
  components = [SingletonComponent::class],
  replaces = [AppModule::class],
)
object TestAppModule {
  @Provides
  @Singleton
  fun provideGithubApi(): GithubApi = mockk(relaxed = true)

  @Provides
  @Singleton
  fun provideKeyValueStorage(): KeyValueStorage = mockk(relaxed = true)

  @Provides
  @Singleton
  fun provideColorProvider(): ColorProvider = mockk(relaxed = true)

  @Provides
  @Singleton
  fun provideNetworkErrorMessageProvider(): NetworkErrorMessageProvider = mockk(relaxed = true)

  @Provides
  @Singleton
  fun provideAppRepository(): AppRepository = mockk(relaxed = true)
}