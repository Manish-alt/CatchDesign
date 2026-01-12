//
//  CatchDesignTestApp.swift
//  CatchDesignTest
//
//  Created by Manish Maharjan on 13/01/2026.
//

import SwiftUI

@main
struct CatchDesignTestApp: App {
    
    // change color for pull-to-refresh background and tint
    init() {
        UIRefreshControl.appearance().tintColor = .white
        
        UIRefreshControl.appearance().backgroundColor = UIColor(hex: "#070932")
    }
    
    var body: some Scene {
        WindowGroup {
            SplashView()
                .preferredColorScheme(.light)
        }
    }
}
