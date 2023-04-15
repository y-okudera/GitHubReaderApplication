//
//  UserSearchObservableObject.swift
//  iosApp
//
//  Created by Yuki Okudera on 2023/04/10.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import Combine
import shared
//import feature

public class UserSearchObservableObject : ObservableObject {

    var viewModel: UserSearchViewModel

    /// state flow acts as a state for swift ui here
    @Published private(set) var state: UserSearchState

    /// fusing the .asObserveable extension funstion we get the wrapped viewmodel and the stateflow
    init(wrapped: UserSearchViewModel) {

        viewModel = wrapped
        state = wrapped.state.value as! UserSearchState
        (wrapped.state.asPublisher() as AnyPublisher<UserSearchState, Never>)
            .receive(on: RunLoop.main)
            .assign(to: &$state)
    }
}

public extension UserSearchViewModel {

    func asObserveableObject() -> UserSearchObservableObject{
        return UserSearchObservableObject(wrapped: self)
    }
}
