//
//  DetailViewController.swift
//  CatchDesign
//
//  Created by Manish Maharjan on 12/01/2026.
//

import UIKit

class DetailViewController: UIViewController {

    @IBOutlet weak var lblDescription: UILabel!
    
    var content: String = ""
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        // Do any additional setup after loading the view.
        
        lblDescription.text = content
        lblDescription.sizeToFit()
    }

}
