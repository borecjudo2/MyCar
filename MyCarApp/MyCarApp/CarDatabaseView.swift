import SwiftUI

struct CarDatabaseView: View {
    @State private var cars: [Car] = []
    @State private var isLoading = true
    @State private var isPresentingNewCarForm = false
    let carService = CarService()
    
    var body: some View {
        NavigationView {
            VStack {
                if isLoading {
                    ProgressView("Загрузка...")
                } else {
                    List(cars) { car in
                        NavigationLink(destination: CarDetailView(carId: car.id)) {
                                                CarRowView(car: car, currentUserId: getUserId())
                                            }
                                        }
                }
            }
            .navigationBarTitle("База авто", displayMode: .inline)
            .navigationBarItems(trailing: Button(action: {
                isPresentingNewCarForm = true
            }) {
                Image(systemName: "plus")
            })
            .onAppear(perform: fetchCars)
            .sheet(isPresented: $isPresentingNewCarForm) {
                NewCarFormView { newCar in
                    createNewCar(newCar)
                }
            }
        }
    }
    
    private func fetchCars() {
        if let userId = UUID(uuidString: "51129080-24ad-4f62-92c6-a37018ecef21") {
            saveUserId(userId)  // Сохраняем только если UUID не nil
        } else {
            print("Ошибка: некорректный UUID")
        }
        
        isLoading = true
        carService.fetchAllCars { result in
            DispatchQueue.main.async {
                switch result {
                case .success(let fetchedCars):
                    self.cars = fetchedCars
                case .failure(let error):
                    print("Ошибка при загрузке: \(error)")
                }
                isLoading = false
            }
        }
    }
    
    private func createNewCar(_ newCar: NewCarRequest) {
        carService.createCar(newCar) { result in
            DispatchQueue.main.async {
                switch result {
                case .success:
                    fetchCars()
                case .failure(let error):
                    print("Ошибка при создании машины: \(error)")
                }
            }
        }
    }
}
