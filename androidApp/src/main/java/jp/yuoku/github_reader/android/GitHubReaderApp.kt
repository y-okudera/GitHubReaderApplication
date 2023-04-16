package jp.yuoku.github_reader.android

import android.app.Application
import jp.yuoku.github_reader.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

class GitHubReaderApp : Application(){

    override fun onCreate() {
        super.onCreate()

        initKoin() {
            androidContext(this@GitHubReaderApp)
            modules(
                listOf(module {
                    /**
                     * android specific modules
                     */
                })
            )
        }
    }
}