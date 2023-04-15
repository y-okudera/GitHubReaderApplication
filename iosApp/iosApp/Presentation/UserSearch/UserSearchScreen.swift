//
//  UserSearchScreen.swift
//  iosApp
//
//  Created by Yuki Okudera on 2023/04/10.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared
//import feature

struct UserSearchScreen: View {

    @ObservedObject var state = KoinApplication.userSearchViewModel().asObserveableObject()

    var body: some View {
        NavigationView {
            VStack{
                switch state.state {
                case is UserSearchStateError:
                    Text((state.state as! UserSearchStateError).errorMessage)
                case is UserSearchStateSuccess:
                    List{
                        ForEach((state.state as! UserSearchStateSuccess).users.items , id: \.self) { item in
                            Text(item.login)
                        }
                        .buttonStyle(PlainButtonStyle())
                    }
                    .frame( maxWidth: .infinity)
                    .listStyle(PlainListStyle())

                case is UserSearchStateLoading:

                    ProgressView()

                default:
                    Text("default")
                }
                Text("default")
            }.onAppear(perform: {
                print("onAppear")
                state.viewModel.onIntent(intent: UserSearchSideEffect.SearchUsers())
            })
//            .navigationBarTitleDisplayMode(.large)
//                .toolbar {
//                    ToolbarItem(placement: .principal) {
//                        VStack {
//                            Text("Headlines").font(.headline)
//
//                        }
//                    }

//                    ToolbarItem(placement: .primaryAction) {
////                     NavigationLink(destination: ReadLaterScreen()) {
////                            Image(systemName: "bookmark.circle.fill")
////                        }.buttonStyle(PlainButtonStyle())
//
//                    }
//                }
        }
    }
}

struct UserSearchScreen_Previews: PreviewProvider {
    static var previews: some View {
        UserSearchScreen()
    }
}
