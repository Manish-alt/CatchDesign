//
//  ContentView.swift
//  CatchDesignTest
//
//  Created by Manish Maharjan on 13/01/2026.
//

import SwiftUI

struct SplashView: View {
    @State private var goNext = false

    var body: some View {
        NavigationStack {
            ZStack {
                Color(hex: "#EB1851")
                    .ignoresSafeArea()
                
                Image("logo")
            }
            .navigationDestination(isPresented: $goNext) {
                ListView()
                    .preferredColorScheme(.light)
                    .navigationBarBackButtonHidden(true)
            }
            .task {
                try? await Task.sleep(nanoseconds: 3_000_000_000) // 3 seconds delay
                goNext = true
            }
            
            
        }
    }
}


