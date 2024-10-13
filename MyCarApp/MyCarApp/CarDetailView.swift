import SwiftUI

struct CarDetailView: View {
    var carId: UUID
    @State private var car: Car? = nil
    @State private var isFavorite = false
    @State private var showAddToFavoriteForm = false
    @State private var showAlert = false
    @State private var isLoading = false
    let carService = CarService()

    var body: some View {
        VStack(alignment: .leading, spacing: 20) {
            if let car = car {
                Text("Модель: \(car.make) \(car.model)")
                    .font(.title)

                if let follow = car.followData.first {
                    Text("Статус: \(follow.runStatus)")
                    Text("Годы: \(follow.yearFrom) - \(follow.yearTo)")
                    Text("Тип привода: \(follow.driveType)")
                    Text("Объем двигателя: \(follow.engineCapacity) см³")

                    Button(action: {
                        // TODO: Реализовать удаление из избранного
                    }) {
                        Text("Удалить из избранного")
                            .font(.headline)
                            .padding()
                            .frame(maxWidth: .infinity)
                            .background(Color.red)
                            .foregroundColor(.white)
                            .cornerRadius(10)
                    }
                } else {
                    Button(action: {
                        showAddToFavoriteForm = true
                    }) {
                        Text("Добавить в избранное")
                            .font(.headline)
                            .padding()
                            .frame(maxWidth: .infinity)
                            .background(Color.blue)
                            .foregroundColor(.white)
                            .cornerRadius(10)
                    }
                }
            } else {
                ProgressView("Загрузка данных...")
            }
        }
        .padding()
        .navigationBarTitle(Text("Детали авто"), displayMode: .inline)
        .onAppear {
            fetchCarDetails()
        }
        .sheet(isPresented: $showAddToFavoriteForm) {
            AddToFavoriteFormView(car: car!, isLoading: $isLoading) { followRequest in
                addCarToFavorites(followRequest: followRequest)
            }
        }
        .alert(isPresented: $showAlert) {
            Alert(title: Text("Успех"), message: Text("Машина добавлена в избранное"), dismissButton: .default(Text("OK")))
        }
    }

    private func fetchCarDetails() {
        isLoading = true
        carService.getCarDetails(carId: carId) { result in
            isLoading = false
            switch result {
            case .success(let carDetails):
                self.car = carDetails
                isFavorite = carDetails.followData.contains { $0.userId.uuidString == getUserId()?.uuidString }
            case .failure(let error):
                print("Ошибка при загрузке данных: \(error)")
            }
        }
    }

    private func addCarToFavorites(followRequest: FollowRequest) {
        isLoading = true
        carService.addToFavorites(carId: carId, followRequest: followRequest) { result in
            isLoading = false
            switch result {
            case .success:
                showAlert = true
                isFavorite = true
                fetchCarDetails()
            case .failure(let error):
                print("Ошибка при добавлении в избранное: \(error)")
            }
        }
    }
}
