//
//  HomeView.swift
//  iosApp
//
//  Created by Android on 05.04.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct HomeView: View {
    
    var bgColor = Color(
        red: 67 / 255,
        green: 16 / 255,
        blue: 152 / 255
    )
    var itemBgColor = Color(
        red: 98 / 255,
        green: 47 / 255,
        blue: 181 / 255
    )
    
    //tmp
    let locations = [
        Forecast(
            address: "Jaipur",
            dateTemperatureRange: "",
            currentHourTemperature: 30,
            weatherDescription: "",
            dayTemperatureList: []
        ),
        Forecast(
            address: "Chennai",
            dateTemperatureRange: "",
            currentHourTemperature: 22,
            weatherDescription: "",
            dayTemperatureList: []
        )
    ]
    
    var body: some View {
        ZStack {
            VStack(spacing: 0) {
                Image("bg_night")
                    .resizable()
                    .scaledToFit()
                bgColor.padding(.top, -10)
            }.edgesIgnoringSafeArea(.all)
            VStack(alignment: .leading) {
                HStack(alignment: .center) {
                    Button { } label: {
                        Image(systemName: "person.circle.fill")
                            .resizable()
                            .frame(width: 30, height: 30)
                            .padding(.leading, 20)
                            .foregroundColor(.white)
                    }
                    Spacer()
                    Button { } label: {
                        Image(systemName: "line.3.horizontal")
                            .resizable()
                            .frame(width: 26.5, height: 22.1)
                            .padding(.trailing, 20)
                            .foregroundColor(.white)
                    }
                }.padding(.top, 30)
                HStack(alignment: .center) {
                    VStack(alignment: .leading) {
                        Text("Pekin".uppercased())
                            .font(.system(size: 28))
                            .fontWeight(.semibold)
                            .foregroundColor(.white)
                        Text("7 Nov 2022 Lun  20°C/29°C")
                            .font(.system(size: 13))
                            .fontWeight(.regular)
                            .foregroundColor(.white)
                    }.padding(.leading, 20)
                    Spacer()
                    VStack(alignment: .trailing) {
                        Text("24°C")
                            .font(.system(size: 36))
                            .fontWeight(.semibold)
                            .foregroundColor(.white)
                        Text("Ciel clair")
                            .font(.system(size: 21))
                            .fontWeight(.regular)
                            .foregroundColor(.white)
                    }.padding(.trailing, 20)
                }.padding(EdgeInsets(top: 108, leading: 0, bottom: 90, trailing: 0))
                ScrollView(.horizontal, showsIndicators: false) {
                    LazyHStack(spacing: 0) {
                        ForEach(0...locations.count - 1, id: \.self) { idx in
                            ZStack(alignment: .top) {
                                if (idx % 2 == 0) {
                                    Image("bg_beach").cornerRadius(22)
                                } else {
                                    Image("bg_mountains").cornerRadius(22)
                                }
                                let text = "\(locations[idx].address) \(locations[idx].currentHourTemperature)°C"
                                Text(text)
                                    .font(.system(size: 19))
                                    .fontWeight(.semibold)
                                    .foregroundColor(.white)
                                    .padding(.top, 24)
                            }.padding(.horizontal, 20)
                        }
                    }
                }.frame(width: .infinity, height: 220)
                Text("Today")
                    .font(.system(size: 20))
                    .fontWeight(.medium)
                    .foregroundColor(.white)
                    .padding(EdgeInsets(top: 10, leading: 20, bottom: 0, trailing: 20))
                Spacer()
                ScrollView(.horizontal, showsIndicators: false) {
                    LazyHStack(spacing: 0) {
                        ForEach(0...23, id: \.self) { idx in
                            VStack(spacing: 0) {
                                Text("30°C")
                                    .padding(5)
                                    .background(Color.white)
                                    .clipShape(Capsule())
                                    .padding(.top, 10)
                                let text = "\(idx):00"
                                Text(text)
                                    .fontWeight(.semibold)
                                    .font(.system(size: 15))
                                    .foregroundColor(.white)
                                    .padding(.vertical, 10)
                            }
                            .frame(width: 80, height: 80)
                            .background(itemBgColor)
                            .cornerRadius(16)
                            .padding(EdgeInsets(top: 0, leading: 20, bottom: 14, trailing: 14))
                        }
                    }
                }
                Spacer()
            }
        }
    }
}

struct HomeView_Previews: PreviewProvider {
    static var previews: some View {
        HomeView()
    }
}
