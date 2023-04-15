package jp.yuoku.github_reader.shared.domain.model.service.user_search

import jp.yuoku.github_reader.shared.domain.model.entity.GitHubUser
import kotlinx.serialization.Serializable

@Serializable
data class GitHubUserSearchResult(
    val items: List<GitHubUser>
)