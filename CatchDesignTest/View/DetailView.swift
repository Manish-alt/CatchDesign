//
//  DetailView.swift
//  CatchDesignTest
//
//  Created by Manish Maharjan on 13/01/2026.
//
import SwiftUI

struct DetailView: View {
    // Environment property to handle the back action
    @Environment(\.dismiss) var dismiss
    
    // Properties to be passed from the ListView
    let title: String
    let content: String

    var body: some View {
        VStack(spacing: 0) {
            
            customNavigationBar
            
            Divider()
            
            ScrollView {
                VStack(alignment: .leading, spacing: 16) {
                    Text(content)
                        .font(.body)
                        .lineSpacing(4)
                        .foregroundColor(.primary)
                }
                .padding(20)
            }
            
            Spacer()
        }
        // Hides the system navigation bar to use our custom one
        .toolbar(.hidden, for: .navigationBar)
    }
    
    // MARK: - Custom Navigation Bar Component
    private var customNavigationBar: some View {
        ZStack {
            Text(title)
                .font(.headline)
            
            HStack {
                Button(action: {
                    dismiss()
                }) {
                    HStack(spacing: 5) {
                        Image(systemName: "chevron.left")
                            .fontWeight(.semibold)
                        Text("Back")
                    }
                    .font(.body)
                    .foregroundColor(Color(hex: "#070932"))
                }
                .padding(.leading, 16)
                
                Spacer()
            }
        }
        .frame(height: 44) // Standard iOS Navigation Bar height
        .background(Color(.systemBackground))
    }
}
