package jp.yuoku.github_reader.domain

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform