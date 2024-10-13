//
//  NewCarFormView.swift
//  MyCarApp
//
//  Created by VladOs on 13.10.24.
//


import SwiftUI

struct NewCarFormView: View {
    @Environment(\.presentationMode) var presentationMode
    @State private var make: String = ""
    @State private var model: String = ""
    @State private var bidCarsMake: String = ""
    @State private var bidCarsModel: String = ""
    @State private var avByMake: String = ""
    @State private var avByModel: String = ""
    
    var onSave: (NewCarRequest) -> Void
    
    var body: some View {
        NavigationView {
            Form {
                Section(header: Text("Данные машины")) {
                    TextField("Марка", text: $make)
                    TextField("Модель", text: $model)
                }
                
                Section(header: Text("BidCars данные")) {
                    TextField("Марка (BidCars)", text: $bidCarsMake)
                    TextField("Модель (BidCars)", text: $bidCarsModel)
                }
                
                Section(header: Text("av.by данные")) {
                    TextField("Марка (av.by)", text: $avByMake)
                    TextField("Модель (av.by)", text: $avByModel)
                }
            }
            .navigationBarTitle("Новая машина", displayMode: .inline)
            .navigationBarItems(trailing: Button("Создать") {
                let newCar = NewCarRequest(make: make, model: model, bidCarsMake: bidCarsMake, bidCarsModel: bidCarsModel, avByMake: avByMake, avByModel: avByModel)
                onSave(newCar)
                presentationMode.wrappedValue.dismiss()
            })
        }
    }
}
