//
//  CatchDesignTestTests.swift
//  CatchDesignTestTests
//
//  Created by Manish Maharjan on 13/01/2026.
//

import Testing
@testable import CatchDesignTest
import Combine
import Foundation

struct CatchDesignTestTests {

        // Helper to store subscriptions during tests
        private var cancellables = Set<AnyCancellable>()

        @Test("fetchData sets loading state and handles successful data fetch")
        func testFetchDataSuccess() async throws {
            // 1. Arrange
            let mockService = MockAPIService()
            let viewModel = ListViewModel(apiService: mockService)
            let expectedData = [ResponseModel]()
            mockService.result = .success(expectedData)

            // 2. Act
            viewModel.fetchData()

            // 3. Assert
            #expect(viewModel.data == expectedData)
            #expect(viewModel.isLoading == false)
            #expect(viewModel.errorMessage == nil)
        }
    
    

        @Test("fetchData handles network failure and sets error message")
        func testFetchDataFailure() async throws {
            // 1. Arrange
            let mockService = MockAPIService()
            let viewModel = ListViewModel(apiService: mockService)
            let error = URLError(.notConnectedToInternet)
            mockService.result = .failure(error)

            // 2. Act
            viewModel.fetchData()

            // 3. Assert
            #expect(viewModel.data.isEmpty) // Or whatever your default is
            #expect(viewModel.isLoading == false)
            #expect(viewModel.errorMessage == error.localizedDescription)
        }

}
