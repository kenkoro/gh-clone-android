package com.kenkoro.practice.githubClone.core.data.networking

import com.kenkoro.practice.githubClone.core.domain.util.NetworkError
import com.kenkoro.practice.githubClone.core.domain.util.Result
import retrofit2.Response

inline fun <reified T> responseToResult(response: Response<T>): Result<T, NetworkError> {
  val body = response.body()
  return if (body == null) {
    Result.Error(NetworkError.Serialization)
  } else {
    Result.Success(body)
  }
}