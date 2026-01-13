//
//  ViewController.swift
//  CatchDesign
//
//  Created by Manish Maharjan on 12/01/2026.
//

import UIKit

class ViewController: UIViewController {

    @IBOutlet weak var tbListView: UITableView!
   
    var data: [ResponseModel] = []
    
    var dataCount = 0
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
        
        
        setUpTableView()
        
        
        callApi()
    }
    

    
    func setUpTableView() {
        tbListView.delegate = self
        tbListView.dataSource = self
        
        tbListView.refreshControl = UIRefreshControl()
        tbListView.refreshControl?.tintColor = .white
        tbListView.refreshControl?.backgroundColor = UIColor(named: "ProgressBackgroundColor")
        tbListView.refreshControl?.addTarget(self, action: #selector(didPullToRefresh), for: .valueChanged)
    }
    
    @objc func didPullToRefresh(){
        callApi()
        
        DispatchQueue.main.asyncAfter(deadline: .now()+2) {
            self.tbListView.refreshControl?.endRefreshing()
        }
    }
    
    func callApi(){
        GenericApiCall<[ResponseModel]>().getMethod(url: Constants.baseUrl){ response in
            print("Hello: \(response)")
            
            self.data = response
            self.dataCount = response.count
            
            self.tbListView.reloadData()
            
        } fail: { msg in
            print(msg)
        }
    }


}

extension ViewController: UITableViewDelegate, UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if dataCount == 0 {
            return 1
        } else {
            return dataCount
        }
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        if dataCount == 0 {
            guard let cell = tbListView.dequeueReusableCell(withIdentifier: "EmptyCell") as? EmptyCell else {
                return UITableViewCell()
            }
            return cell
        } else {
            guard let cell = tbListView.dequeueReusableCell(withIdentifier: "ListViewCell") as? ListViewCell else {
                return UITableViewCell()
            }
            
            cell.setContent(data: data[indexPath.row])
            return cell
        }
        
        
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        tableView.deselectRow(at: indexPath, animated: true)
        
        
        let vc = DetailViewController.instantiate(fromAppStoryboard: .DetailScreen)
        
        vc.title = data[indexPath.row].title
        vc.content = data[indexPath.row].content
        
        self.navigationController?.pushViewController(vc, animated: true)

    }
    
    
}

