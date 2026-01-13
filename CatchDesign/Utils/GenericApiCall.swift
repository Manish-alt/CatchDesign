//
//  GenericApiCall.swift
//  CatchDesign
//
//  Created by Manish Maharjan on 12/01/2026.
//

import ObjectMapper
import UIKit
import Alamofire


class GenericApiCall<T: Codable> {
    
    var accepts = HTTPHeader(name: "Accept", value: "application/json")
    
    func getMethod(url: String, success: @escaping (T) -> Void, fail: @escaping (T) -> Void) {
        var httpHeader = HTTPHeaders()
        httpHeader.add(accepts)
        
        AF.request(url, method: .get).responseDecodable(of: [ResponseModel].self) { [weak self] response in
            switch response.result {
            case .success(let json):

                success(json as! T)
                
            case .failure(let error):
                print("Error: \(error)")
            }
        }
        
        
    }
    
}

