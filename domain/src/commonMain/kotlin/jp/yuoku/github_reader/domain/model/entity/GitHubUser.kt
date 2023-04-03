package jp.yuoku.github_reader.domain.model.entity

import kotlinx.serialization.Serializable

@Serializable
data class GitHubUser(
    val login: String,
    val id: Int,
    val avatarUrl: String,
    val htmlUrl: String,
)
