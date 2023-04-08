package jp.yuoku.github_reader.di

import jp.yuoku.github_reader.application.di.applicationModule
import jp.yuoku.github_reader.data.di.dataModule

val sharedModule = applicationModule() + dataModule()
