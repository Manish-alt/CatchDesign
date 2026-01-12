//
//  ListView.swift
//  CatchDesignTest
//
//  Created by Manish Maharjan on 13/01/2026.
//

import SwiftUI

struct ListView: View {
    @StateObject private var viewModel = ListViewModel()

    var body: some View {
        
        NavigationStack {
            Group {
                
                // If data is empty then show EmptyView
                if viewModel.data.isEmpty && !viewModel.isLoading {
                    CustomEmptyView(action: {
                        viewModel.fetchData()
                    })
                } else {
                    
                    List(viewModel.data, id: \.id) { item in
                        
                        // Direct to Detail View when clicked on List Item
                        NavigationLink {
                            DetailView(title: item.title, content: item.content)
                                .preferredColorScheme(.light)
                                .navigationBarBackButtonHidden(false)
                        } label: {
                            ListViewRow(data: item)
                        }
                        .listRowInsets(EdgeInsets(top: 0, leading: 16, bottom: 0, trailing: -10))
                    }
                    .listStyle(.plain)
                    .toolbar(.hidden, for: .navigationBar)
                    .overlay {
                        if viewModel.isLoading {
                            ProgressView()
                        }
                    }
                    .refreshable {
                        try? await Task.sleep(nanoseconds: 2 * 1_000_000_000) // 2 seconds sleep 
                        viewModel.fetchData()
                    }
                    .onAppear {
                        viewModel.fetchData()
                    }
                }
            }
        }
    }
}

struct ListViewRow: View {
    let data: ResponseModel
    
    var body: some View {
        HStack {
            Text(data.title)
                .font(.body)
                .foregroundColor(.primary)
            
            Spacer()
            
            Text(data.subtitle)
                .font(.body)
                .foregroundColor(.secondary)
            
            Image(systemName: "chevron.right")
                .font(.system(size: 14, weight: .bold))
                .foregroundColor(.black)
                .ignoresSafeArea()
        }
        .padding(.vertical, 20)
        .tint(.black)
    }
}

struct CircularProgressRing: View {
    let progress: CGFloat
    
    var body: some View {
        ZStack {
            Circle()
                .stroke(Color.white.opacity(1), lineWidth: 4)
            Circle()
                .trim(from: 0, to: min(progress, 1.0))
                .stroke(
                    AngularGradient(
                        gradient: Gradient(colors: [.white, .red]),
                        center: .center
                    ),
                    style: StrokeStyle(lineWidth: 4, lineCap: .round)
                )
                .rotationEffect(.degrees(-90))
        }
    }
}

struct ScrollOffsetKey: PreferenceKey {
    static var defaultValue: CGFloat = 0
    static func reduce(value: inout CGFloat, nextValue: () -> CGFloat) {
        value = nextValue()
    }
}


