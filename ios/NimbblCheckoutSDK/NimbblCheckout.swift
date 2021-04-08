//
//  NimbblCheckout.swift
//  NimbblPaymentSDK
//
//  Created by Stany on 24/03/21.
//

import Foundation
import UIKit

@objc open class NimbblCheckout: NSObject {
  
  fileprivate var accessKey: String
  fileprivate var delegate: NimbblCheckoutDelegate
  
  @objc public init(accessKey: String, delegate: NimbblCheckoutDelegate){
    self.accessKey = accessKey
    self.delegate = delegate
  }
  
  @objc public func show(options: [String:Any], displayController: UIViewController){
    var props = options
    props["accessKey"] = accessKey
    let vc = NimbblCheckoutViewController(options: props, delegate: self.delegate)
    vc.modalPresentationStyle = .overFullScreen
    displayController.present(vc, animated: false, completion: nil)
  }
  
}
