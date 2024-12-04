package com.kenkoro.practice.githubClone.core.data.networking

import com.kenkoro.practice.githubClone.core.domain.util.NetworkError
import com.kenkoro.practice.githubClone.core.domain.util.Result
import retrofit2.Response

inline fun <reified T> responseToResult(response: Response<T>): Result<T, NetworkError> {
  return when (response.code()) {
    in 200..299 -> {
      val data = response.body()
      if (data == null) {
        Result.Error(NetworkError.Serialization)
      } else {
        Result.Success(data)
      }
    }

    304 -> Result.Error(NetworkError.NotModified)
    401 -> Result.Error(NetworkError.RequiresAuthentication)
    403 -> Result.Error(NetworkError.Forbidden)
    in 500..599 -> Result.Error(NetworkError.ServerError)
    else -> Result.Error(NetworkError.Unknown)
  }
}