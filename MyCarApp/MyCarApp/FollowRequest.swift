//
//  FollowRequest.swift
//  MyCarApp
//
//  Created by VladOs on 13.10.24.
//


struct FollowRequest: Codable {
    let userId: String
    let runStatus: String
    let yearFrom: Int
    let yearTo: Int
    let driveType: Int
    let engineCapacity: Int
}
