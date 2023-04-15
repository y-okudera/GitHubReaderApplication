//
//  UserRow.swift
//  iosApp
//
//  Created by Yuki Okudera on 2023/04/15.
//  Copyright © 2023 orgName. All rights reserved.
//

import shared
import SwiftUI

struct UserRow: View {

    private let user: GitHubUser

    init(user: GitHubUser) {
        self.user = user
    }

    var body: some View {
        HStack {

            if #available(iOS 15.0, *) {
                AsyncImage(url: URL(string: user.avatarUrl), transaction: Transaction(animation:.easeIn)) { phase in
                    switch phase{
                    case .success(let image):
                        image
                        .resizable()
                        .scaledToFill()
                    case .failure, .empty:
                        Image(systemName:"ant.circle.fill")
                    @unknown default:
                        ProgressView()
                    }
                }
                .frame(width: 40, height: 40)
                .cornerRadius(8)

            } else {
                // Fallback on earlier versions
            }

            VStack {
                Text("\(user.id)")
                Text(user.login)
            }
        }
    }
}

struct UserRow_Previews: PreviewProvider {
    static var previews: some View {
        UserRow(user: GitHubUser(login: "login", id: 1, avatarUrl: "avatarUrl", htmlUrl: "htmlUrl"))
    }
}
