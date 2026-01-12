# iOS Developer Test


### Guidelines

1. To run the project you need the latest Xcode.
2. After you clone or unzip the folder, open the folder.
3. Please double click on 'CatchDesignTest.xcodeproj' and Xcode will open.
4. Please wait while Xcode builds then select the simulator and you can click play button on the top left of Xcode.
5. This app is written in Swift, including using SwiftUI and Combine.

### Project Structure and Working
1. I seperated every file to their respective folder as their fuctions.

CatchDesignTest
    - Model
        - ResponseModel.swift
    - Service
        - ApiService.swift
    - Utils
        - Constants.swift
        - Extension.swift
    - View
        - CustomEmptyView.swift
        - DetailView.swift
        - ListView.swift
        - SplashView.swift
    - ViewModel
        - ListViewModel.swift
CatchDesignTestApp.swift

- I have followed SOLID principle.
- Since the project was too small so no arhitecture is used but in large project I prefer Clean architecture.

2. 'CatchDesignTestApp' is the main file which contains the root view.
    - here i have setup the background color and tint for refresh control.
    - directs to the SplashView
   
3. 'SplashView' as name suggests, it is a 3 second screen as an intro to app.
    - there is a 3 second delay to make app logo visible.
    - used NavigationStack to direct to 'ListView' screen.
    
4. 'ListView' screen loads the data.
    - here ListViewModel is used for api call.
    - ListViewModel is used to seperate business logic from UI logic.
    - if the data is empty then it loads 'CustomEmptyView' else list is displayed.
    - when clicked on the list items, will direct to 'DetailView' where title and content are displayed 
    
5. Api Call:
    - URLSession is used for api call and '@Published' property wrapper is used to automatically announce changes when occured.
    - Handled failure and success case.
    - Datas are mapped to 'ResponseModel' struct which is Codable. It means that the data can be converted to and from JSON for data presentation.
    
6. Extension function contains Color extension to convert hex to RGB format.


