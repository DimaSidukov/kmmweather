//
//  SearchView.swift
//  iosApp
//
//  Created by Android on 05.04.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct SearchView: View {
    
    @ObservedObject private var viewModel = SearchViewModel()
    
    var bgColor = Color(
        red: 67 / 255,
        green: 16 / 255,
        blue: 152 / 255
    )
    
    @State private var text = ""
    
    @Binding var tabSelection: Int
    @Binding var latitude: Double
    @Binding var longitude: Double
    @Binding var forceRemote: Bool
    
    var body: some View {
        
        NavigationView {
            ZStack {
                bgColor
                VStack(alignment: HorizontalAlignment.center) {
                    VStack(alignment: HorizontalAlignment.leading) {
                        Text("Enter the city name:")
                            .padding(.leading, 35)
                            .foregroundColor(Color.white)
                        TextField("Yoshkar-Ola", text: $text)
                            .padding()
                            .foregroundColor(.white)
                            .background(Color.white.opacity(0.5))
                            .cornerRadius(20)
                            .shadow(radius: 5)
                            .padding(.horizontal, 30)
                    }
                        Button(action: {
                            viewModel.getCoordinates(address: self.text) { lat, lon in
                                longitude = lon
                                latitude = lat
                                forceRemote = true
                                tabSelection = 1
                            }
                        }) {
                            Text("Apply")
                                .foregroundColor(.white)
                                .padding()
                                .border(.gray)
                                .background(Color.gray)
                        }.foregroundColor(.white)
                            .cornerRadius(10)
                            .padding(.top, 20)
                }
            }.edgesIgnoringSafeArea(.all)
        }
    }
}

//struct SearchView_Previews: PreviewProvider {
//    static var previews: some View {
//        SearchView()
//    }
//}
