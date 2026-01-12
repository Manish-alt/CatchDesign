//
//  Extension.swift
//  CatchDesignTest
//
//  Created by Manish Maharjan on 13/01/2026.
//

import SwiftUI

extension Color {
    init(hex: String, opacity: Double = 1.0) {
        let hex = hex.trimmingCharacters(in: .whitespacesAndNewlines)
            .replacingOccurrences(of: "#", with: "")

        var int: UInt64 = 0
        Scanner(string: hex).scanHexInt64(&int)

        let r, g, b: UInt64

        switch hex.count {
        case 6: // RRGGBB
            (r, g, b) = (
                (int >> 16) & 0xFF,
                (int >> 8) & 0xFF,
                int & 0xFF
            )
        default:
            (r, g, b) = (0, 0, 0)
        }

        self.init(
            .sRGB,
            red: Double(r) / 255,
            green: Double(g) / 255,
            blue: Double(b) / 255,
            opacity: opacity
        )
    }
}


extension UIColor {
    convenience init?(hex: String) {
        let r, g, b, a: CGFloat

        if hex.hasPrefix("#") {
            let start = hex.index(hex.startIndex, offsetBy: 1)
            let hexColor = String(hex[start...])

            // Use 6 characters for RGB or 8 for RGBA
            if hexColor.count == 6 || hexColor.count == 8 {
                let scanner = Scanner(string: hexColor)
                var hexNumber: UInt64 = 0

                if scanner.scanHexInt64(&hexNumber) {
                    if hexColor.count == 6 {
                        r = CGFloat((hexNumber & 0xFF0000) >> 16) / 255.0
                        g = CGFloat((hexNumber & 0x00FF00) >> 8) / 255.0
                        b = CGFloat(hexNumber & 0x0000FF) / 255.0
                        a = 1.0 // Default to opaque
                    } else { // hexColor.count == 8
                        r = CGFloat((hexNumber & 0xff000000) >> 24) / 255.0
                        g = CGFloat((hexNumber & 0x00ff0000) >> 16) / 255.0
                        b = CGFloat((hexNumber & 0x0000ff00) >> 8) / 255.0
                        a = CGFloat(hexNumber & 0x000000ff) / 255.0
                    }
                    self.init(red: r, green: g, blue: b, alpha: a)
                    return
                }
            }
        }
        return nil // Return nil for invalid formats
    }
}
