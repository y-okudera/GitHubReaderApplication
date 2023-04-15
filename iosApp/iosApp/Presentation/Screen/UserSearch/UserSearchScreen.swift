//
//  UserSearchScreen.swift
//  iosApp
//
//  Created by Yuki Okudera on 2023/04/10.
//  Copyright © 2023 orgName. All rights reserved.
//

import shared
import SwiftUI

struct UserSearchScreen: View {

    @ObservedObject private var state = KoinApplication.userSearchViewModel().asObserveableObject()

    @State private var query = ""

    var body: some View {
        NavigationView {
            VStack {
                SearchBar(searchText: $query, placeholder: "ユーザ検索", onSearchButtonClicked: {
                    state.viewModel.onIntent(intent: UserSearchActionSearchUsers(query: query))
                })
                switch state.state {
                case is UserSearchStateError:
                    Text((state.state as! UserSearchStateError).errorMessage)
                case is UserSearchStateSuccess:
                    List{
                        ForEach((state.state as! UserSearchStateSuccess).users.items , id: \.self) { item in
                            UserRow(user: item)
                        }
                        .buttonStyle(PlainButtonStyle())
                    }
                    .frame( maxWidth: .infinity)
                    .listStyle(PlainListStyle())

                case is UserSearchStateLoading:
                    ProgressView()
                    Spacer()

                default:
                    Spacer()
                }
            }
            .navigationTitle("UserSearch")
        }
    }
}

struct UserSearchScreen_Previews: PreviewProvider {
    static var previews: some View {
        UserSearchScreen()
    }
}
