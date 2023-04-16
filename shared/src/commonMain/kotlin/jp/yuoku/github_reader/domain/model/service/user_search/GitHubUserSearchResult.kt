package jp.yuoku.github_reader.domain.model.service.user_search

import jp.yuoku.github_reader.domain.model.entity.GitHubUser
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GitHubUserSearchResult(
    val items: List<GitHubUser>,
    @SerialName("total_count") val totalCount: Int,
    @SerialName("incomplete_results") val incompleteResults: Boolean,
)