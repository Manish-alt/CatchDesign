//
//  ApiServiceProtocol.swift
//  CatchDesignTest
//
//  Created by Manish Maharjan on 13/01/2026.
//

import Combine
import Foundation

protocol APIServiceProtocol {
    func fetchData() -> AnyPublisher<[ResponseModel], Error>
}

final class APIService: APIServiceProtocol {

    func fetchData() -> AnyPublisher<[ResponseModel], Error> {
        guard let url = URL(string: Constants.baseUrl) else {
            return Fail(error: URLError(.badURL))
                .eraseToAnyPublisher()
        }

        return URLSession.shared.dataTaskPublisher(for: url)
            .map(\.data)
            .decode(type: [ResponseModel].self, decoder: JSONDecoder())
            .receive(on: DispatchQueue.main)
            .eraseToAnyPublisher()
    }
}

class MockAPIService: APIServiceProtocol {
    // 1. Ensure the Result matches the expected [ResponseModel]
    var result: Result<[ResponseModel], Error>?
    
    var autoComplete: Bool = true

    func fetchData() -> AnyPublisher<[ResponseModel], Error> {
        guard let result = result else {
            // Return an empty publisher if no result is set to prevent crashes
            return Empty().eraseToAnyPublisher()
        }

        if autoComplete {
            // 2. We can use CurrentValueSubject to emit the result immediately
            return result.publisher.eraseToAnyPublisher()
        } else {
            // 3. Use a PassthroughSubject that stays open to simulate "waiting"
            return PassthroughSubject<[ResponseModel], Error>().eraseToAnyPublisher()
        }
    }
}

