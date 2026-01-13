//
//  ListViewCell.swift
//  CatchDesign
//
//  Created by Manish Maharjan on 12/01/2026.
//

import UIKit

class ListViewCell: UITableViewCell {

    @IBOutlet weak var lblTitle: UILabel!
    @IBOutlet weak var lblSubtitle: UILabel!
    

    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
        
    
    }
    
    func setContent(data: ResponseModel){
        lblTitle.text = data.title
        lblSubtitle.text = data.subtitle
    }

    
}
