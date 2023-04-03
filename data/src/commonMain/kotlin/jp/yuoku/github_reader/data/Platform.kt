package jp.yuoku.github_reader.data

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform