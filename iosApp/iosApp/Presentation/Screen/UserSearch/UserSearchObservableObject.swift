//
//  UserSearchObservableObject.swift
//  iosApp
//
//  Created by Yuki Okudera on 2023/04/10.
//  Copyright © 2023 orgName. All rights reserved.
//

import Combine
import shared

final class UserSearchObservableObject: ObservableObject {

    var viewModel: UserSearchViewModel

    /// state flow acts as a state for swift ui here
    @Published private(set) var state: UserSearchUiState

    /// fusing the .asObserveable extension funstion we get the wrapped viewmodel and the stateflow
    init(wrapped: UserSearchViewModel) {

        viewModel = wrapped
        state = wrapped.state.value as! UserSearchUiState
        (wrapped.state.asPublisher() as AnyPublisher<UserSearchUiState, Never>)
            .receive(on: RunLoop.main)
            .assign(to: &$state)
    }
}

extension UserSearchViewModel {

    func asObserveableObject() -> UserSearchObservableObject{
        return UserSearchObservableObject(wrapped: self)
    }
}
