//
//  ListViewModel.swift
//  CatchDesignTest
//
//  Created by Manish Maharjan on 13/01/2026.
//

import Combine

final class ListViewModel: ObservableObject {

    @Published var data: [ResponseModel] = []
    @Published var isLoading = false
    @Published var errorMessage: String?

    private let apiService: APIServiceProtocol
    private var cancellables = Set<AnyCancellable>()

    init(apiService: APIServiceProtocol = APIService()) {
        self.apiService = apiService
    }

    func fetchData() {
        isLoading = true
        errorMessage = nil

        apiService.fetchData()
            .sink { [weak self] completion in
                self?.isLoading = false

                if case let .failure(error) = completion {
                    self?.errorMessage = error.localizedDescription
                }
            } receiveValue: { [weak self] response in
                self?.data = response
            }
            .store(in: &cancellables)
    }
}
