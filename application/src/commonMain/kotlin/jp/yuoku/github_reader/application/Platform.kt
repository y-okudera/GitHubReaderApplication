package jp.yuoku.github_reader.application

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform