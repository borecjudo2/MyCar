import SwiftUI

struct CarRowView: View {
    var car: Car
    let currentUserId: UUID?

    var body: some View {
        HStack {
            VStack(alignment: .leading) {
                Text("\(car.make) \(car.model)")
                    .font(.headline)
                if let follow = car.followData.first {
                    Text("Статус: \(follow.runStatus), Годы: \(follow.yearFrom)-\(follow.yearTo)")
                        .font(.subheadline)
                        .foregroundColor(.gray)
                }
            }
            Spacer()
            if car.followData.contains(where: { $0.userId == currentUserId }) {
                Image(systemName: "heart.fill")
                    .foregroundColor(.red)
            } else {
                Image(systemName: "heart")
                    .foregroundColor(.gray)
            }
        }
        .padding(.vertical, 8)
    }
}
