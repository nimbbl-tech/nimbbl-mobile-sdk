//
//  ViewController.swift
//  PaymentApp
//
//  Created by Stany on 12/03/21.
//

import UIKit
import PaymentSDK

class ViewController: UIViewController, PaymentDelegate {

    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
    }

    @IBAction func onBtnPaymentAction(_ sender: Any) {
        let vc = PaymentScreen()
        vc.delegate = self
        vc.orderID = "BmO74dQ8xzmbW7qx"
        vc.accessKey = "access_key_1MwvMkKkweorz0ry"
        vc.modalPresentationStyle = .overFullScreen
        present(vc, animated: true, completion: nil)
    }
    
  func getResponse(_ response: String?) {
      print("Response",response)
  }
    
}

