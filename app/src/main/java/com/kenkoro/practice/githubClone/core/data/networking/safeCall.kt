package com.kenkoro.practice.githubClone.core.data.networking

import com.kenkoro.practice.githubClone.core.domain.util.NetworkError
import com.kenkoro.practice.githubClone.core.domain.util.Result
import kotlinx.coroutines.ensureActive
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import kotlin.coroutines.coroutineContext

suspend inline fun <reified T> safeCall(execute: () -> Response<T>): Result<T, NetworkError> {
  val response =
    try {
      execute()
    } catch (e: IOException) {
      return Result.Error(NetworkError.NoInternet)
    } catch (e: HttpException) {
      return when (e.code()) {
        304 -> Result.Error(NetworkError.NotModified)
        401 -> Result.Error(NetworkError.RequiresAuthentication)
        403 -> Result.Error(NetworkError.Forbidden)
        in 500..599 -> Result.Error(NetworkError.ServerError)
        else -> Result.Error(NetworkError.Unknown)
      }
    } catch (e: Exception) {
      coroutineContext.ensureActive()
      return Result.Error(NetworkError.Unknown)
    }

  return responseToResult(response)
}