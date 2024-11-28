package com.kenkoro.practice.githubClone.core.domain.util

enum class NetworkError : Error {
  NoInternet,
  Serialization,
  ServerError,
  Unknown,
}