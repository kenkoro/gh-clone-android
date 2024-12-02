package com.kenkoro.practice.githubClone.core.data.networking

import com.kenkoro.practice.githubClone.core.domain.util.NetworkError
import com.kenkoro.practice.githubClone.core.domain.util.Result
import retrofit2.Response

suspend inline fun <reified T> responseToResult(response: Response<T>): Result<T, NetworkError> {
  return when (response.code()) {
    in 200..299 -> {
      val body = response.body()
      if (body == null) {
        Result.Error(NetworkError.Serialization)
      } else {
        Result.Success(body)
      }
    }

    else -> Result.Error(NetworkError.Unknown)
  }
}