//
//  CarService.swift
//  MyCarApp
//
//  Created by VladOs on 13.10.24.
//


import Foundation

class CarService {
    
    let baseURL = "http://localhost:8080/cars"
        
    func getCarDetails(carId: UUID, completion: @escaping (Result<Car, Error>) -> Void) {
            guard let url = URL(string: "http://localhost:8080/cars/\(carId.uuidString)") else {
                return
            }

            URLSession.shared.dataTask(with: url) { data, response, error in
                if let error = error {
                    completion(.failure(error))
                    return
                }

                guard let data = data else {
                    completion(.failure(NSError(domain: "", code: 0, userInfo: [NSLocalizedDescriptionKey: "No data"])))
                    return
                }

                do {
                    let car = try JSONDecoder().decode(Car.self, from: data)
                    completion(.success(car))
                } catch {
                    completion(.failure(error))
                }
            }.resume()
        }

        func addToFavorites(carId: UUID, followRequest: FollowRequest, completion: @escaping (Result<Void, Error>) -> Void) {
            guard let url = URL(string: "http://localhost:8080/cars/\(carId.uuidString)/follow") else {
                return
            }

            var request = URLRequest(url: url)
            request.httpMethod = "POST"
            request.addValue("application/json", forHTTPHeaderField: "Content-Type")

            do {
                let requestBody = try JSONEncoder().encode(followRequest)
                request.httpBody = requestBody
            } catch {
                completion(.failure(error))
                return
            }

            URLSession.shared.dataTask(with: request) { data, response, error in
                if let error = error {
                    completion(.failure(error))
                    return
                }

                if let httpResponse = response as? HTTPURLResponse, httpResponse.statusCode == 204 {
                    completion(.success(()))
                } else {
                    completion(.failure(NSError(domain: "", code: 0, userInfo: [NSLocalizedDescriptionKey: "Failed to add to favorites"])))
                }
            }.resume()
        }
    
    func fetchAllCars(completion: @escaping (Result<[Car], Error>) -> Void) {
        guard let url = URL(string: "http://localhost:8080/cars") else {
            completion(.failure(NSError(domain: "Invalid URL", code: 400, userInfo: nil)))
            return
        }
        
        URLSession.shared.dataTask(with: url) { data, response, error in
            if let error = error {
                completion(.failure(error))
                return
            }
            
            guard let data = data else {
                completion(.failure(NSError(domain: "No data", code: 404, userInfo: nil)))
                return
            }
            
            do {
                let cars = try JSONDecoder().decode([Car].self, from: data)
                completion(.success(cars))
            } catch {
                completion(.failure(error))
            }
        }.resume()
    }
    
    // Метод для создания новой машины
    func createCar(_ newCar: NewCarRequest, completion: @escaping (Result<Void, Error>) -> Void) {
        guard let url = URL(string: "http://localhost:8080/cars") else {
            completion(.failure(NSError(domain: "Invalid URL", code: 400, userInfo: nil)))
            return
        }
        
        var request = URLRequest(url: url)
        request.httpMethod = "POST"
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")
        
        do {
            let jsonData = try JSONEncoder().encode(newCar)
            request.httpBody = jsonData
        } catch {
            completion(.failure(error))
            return
        }
        
        URLSession.shared.dataTask(with: request) { data, response, error in
            if let error = error {
                completion(.failure(error))
                return
            }
            
            if let httpResponse = response as? HTTPURLResponse, httpResponse.statusCode == 201 {
                completion(.success(()))
            } else {
                let responseError = NSError(domain: "com.example.error", code: 500, userInfo: [NSLocalizedDescriptionKey: "Failed to create car"])
                completion(.failure(responseError))
            }
        }.resume()
    }
}
