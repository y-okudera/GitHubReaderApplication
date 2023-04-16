//
//  SearchBar.swift
//  iosApp
//
//  Created by Yuki Okudera on 2023/04/15.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct SearchBar: UIViewRepresentable {

    @Binding var searchText: String
    private let placeholder: String?
    private var textDidChange: ((String) -> Void)?
    private var onSearchButtonClicked: (() -> Void)?

    init(
        searchText: Binding<String>,
        placeholder: String?,
        textDidChange: ((String) -> Void)?,
        onSearchButtonClicked: (() -> Void)?
    ) {
        self._searchText = searchText
        self.placeholder = placeholder
        self.textDidChange = textDidChange
        self.onSearchButtonClicked = onSearchButtonClicked
    }

    func makeCoordinator() -> Coordinator {
        Coordinator(self)
    }

    func makeUIView(context: Context) -> UISearchBar {
        let searchBar = UISearchBar()
        searchBar.delegate = context.coordinator
        searchBar.placeholder = placeholder

        // remove lines.
        searchBar.backgroundImage = .init()

        return searchBar
    }

    func updateUIView(_ uiView: UISearchBar, context: Context) {
        uiView.text = searchText
    }

    class Coordinator: NSObject, UISearchBarDelegate {
        var searchBar: SearchBar

        init(_ searchBar: SearchBar) {
            self.searchBar = searchBar
        }

        func searchBar(_ searchBar: UISearchBar, textDidChange searchText: String) {
            self.searchBar.searchText = searchText
            self.searchBar.textDidChange?(searchText)
        }

        func searchBarSearchButtonClicked(_ searchBar: UISearchBar) {
            searchBar.resignFirstResponder()
            self.searchBar.onSearchButtonClicked?()
        }
    }
}

struct SearchBar_Previews: PreviewProvider {
    static var previews: some View {
        SearchBar(searchText: .constant("preview"), placeholder: "placeholder", textDidChange: nil, onSearchButtonClicked: nil)
    }
}
