//
//  ResponseModel.swift
//  CatchDesignTest
//
//  Created by Manish Maharjan on 13/01/2026.
//

import Foundation

struct ResponseModel : Identifiable, Codable, Equatable {
    var id: Int
    var title: String
    var subtitle: String
    var content: String
}
