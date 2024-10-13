//
//  Utils.swift
//  MyCarApp
//
//  Created by VladOs on 13.10.24.
//

import Foundation

// Сохраняем userId в UserDefaults
func saveUserId(_ userId: UUID) {
    UserDefaults.standard.set(userId.uuidString, forKey: "userId")
}

// Получаем сохраненный userId
func getUserId() -> UUID? {
    if let userIdString = UserDefaults.standard.string(forKey: "userId") {
        return UUID(uuidString: userIdString)
    }
    return nil
}
