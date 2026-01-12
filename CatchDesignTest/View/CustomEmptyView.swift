//
//  CustomEmptyView.swift
//  CatchDesignTest
//
//  Created by Manish Maharjan on 13/01/2026.
//

import SwiftUI

struct CustomEmptyView: View {
    var action: () -> Void
    
    var body: some View {
        ScrollView {
            ZStack {
                Color(hex: "#EB1851")
                    .frame(minHeight: UIScreen.main.bounds.height)
                
                Image("logo")
            }
        }
        .ignoresSafeArea()
        .refreshable {
            try? await Task.sleep(nanoseconds: 2 * 1_000_000_000)
            action()
        }
    }
}


