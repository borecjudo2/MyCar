import Foundation

struct Car: Identifiable, Codable {
    let id: UUID
    let make: String
    let model: String
    let bidCarsMake: String
    let bidCarsModel: String
    let avByMake: String
    let avByModel: String
    let followData: [FollowData]
}

struct FollowData: Codable {
    let userId: UUID
    let runStatus: String
    let yearFrom: Int
    let yearTo: Int
    let driveType: Int
    let engineCapacity: Int
}
