//
//  KoinApplication+.swift
//  iosApp
//
//  Created by Yuki Okudera on 2023/04/11.
//  Copyright © 2023 orgName. All rights reserved.
//

import shared
import feature

typealias KoinApplication = Koin_coreKoinApplication
typealias Koin = shared.Koin_coreKoin

extension KoinApplication {

    private static let keyPaths: [PartialKeyPath<Koin>] = [
        \.apiClient,
         \.gitHubUserSearchRepository,
         \.gitHubUserSearchUseCase,
         \.userSearchViewModel,
    ]

    static let shared = KoinHelperKt.doInitKoin()

    @discardableResult
    static func start() -> KoinApplication {
        Self.shared
    }

    static func inject<T>() -> T {
        self.shared.inject()
    }

    func inject<T>() -> T {
        for partialKeyPath in Self.keyPaths {
            guard let keyPath = partialKeyPath as? KeyPath<Koin, T> else {
                continue
            }
            return koin[keyPath: keyPath]
        }
        fatalError("\(T.self) is not registered with KoinApplication.")
    }
}

extension KoinApplication {
    static func userSearchViewModel() -> UserSearchViewModel {
        self.shared.inject()
    }
}
