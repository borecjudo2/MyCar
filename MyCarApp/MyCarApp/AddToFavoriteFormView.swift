import SwiftUI

struct AddToFavoriteFormView: View {
    var car: Car
    @Binding var isLoading: Bool
    @State private var runStatus: String = "Run and Drive"
    @State private var yearFrom: Int = 2020
    @State private var yearTo: Int = 2022
    @State private var driveType: Int = 4
    @State private var engineCapacity: Int = 2000
    
    var onSave: (FollowRequest) -> Void
    @Environment(\.presentationMode) var presentationMode

    var body: some View {
        NavigationView {
            if isLoading {
                ProgressView("Сохранение...")
                    .progressViewStyle(CircularProgressViewStyle())
                    .scaleEffect(1.5)
            } else {
                Form {
                    Section(header: Text("Информация для избранного")) {
                        TextField("Статус", text: $runStatus)
                        
                        Stepper("Год с: \(yearFrom)", value: $yearFrom, in: 1980...2024)
                        Stepper("Год по: \(yearTo)", value: $yearTo, in: 1980...2024)
                        
                        Stepper("Тип привода: \(driveType)", value: $driveType, in: 1...4)
                        
                        Stepper("Объем двигателя: \(engineCapacity) см³", value: $engineCapacity, in: 500...5000, step: 100)
                    }
                    
                    Button(action: save) {
                        Text("Сохранить")
                            .font(.headline)
                            .padding()
                            .frame(maxWidth: .infinity)
                            .background(Color.green)
                            .foregroundColor(.white)
                            .cornerRadius(10)
                    }
                }
            }
        }
        .navigationBarTitle(Text("Добавить в избранное"), displayMode: .inline)
    }

    private func save() {
        let followRequest = FollowRequest(
            userId: getUserId()?.uuidString ?? "",
            runStatus: runStatus,
            yearFrom: yearFrom,
            yearTo: yearTo,
            driveType: driveType,
            engineCapacity: engineCapacity
        )
        onSave(followRequest)
        presentationMode.wrappedValue.dismiss() // Закрываем окно после сохранения
    }
}
