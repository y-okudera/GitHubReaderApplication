package jp.yuoku.github_reader.di

import jp.yuoku.github_reader.shared.application.di.applicationModule
import jp.yuoku.github_reader.shared.data.di.dataModule
import jp.yuoku.github_reader.shared.feature.di.featureModule

val sharedModule = applicationModule() + dataModule("https://api.github.com/") + featureModule()
