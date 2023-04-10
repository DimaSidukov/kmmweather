//
//  Helpers.swift
//  iosApp
//
//  Created by Android on 07.04.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import Combine
import shared

typealias Collector = Kotlinx_coroutines_coreFlowCollector

class Observer: Collector {
    
    func emit(value: Any?, completionHandler: @escaping (Error?) -> Void) {
        callback(value)
        completionHandler(nil)
    }
    
    
    let callback:(Any?) -> Void
    
    init(callback: @escaping (Any?) -> Void) {
        self.callback = callback
    }

}
