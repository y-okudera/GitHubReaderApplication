package jp.yuoku.github_reader

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform