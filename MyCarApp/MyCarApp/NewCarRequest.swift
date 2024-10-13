//
//  NewCarRequest.swift
//  MyCarApp
//
//  Created by VladOs on 13.10.24.
//


import Foundation

struct NewCarRequest: Codable {
    let make: String
    let model: String
    let bidCarsMake: String
    let bidCarsModel: String
    let avByMake: String
    let avByModel: String
}
