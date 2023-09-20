//
//  MainViewModel.swift
//  iosApp
//
//  Created by Robert Nagy on 21/07/2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation
import shared

class MainViewModel: ObservableObject {
    
    private let onToggleFavouriteState = ToggleFavouriteStateUseCase.init()
    
    @Published
    private(set) var state = State.LOADING
    
    @Published
    var shouldFilterFavourites = false
    
    @Published
    private(set) var filteredBreeds: [Breed] = []
    
    @Published
    private var breeds: [Breed] = []
    
    init() {
        fetchData()
    }
    
    func fetchData() {
        state = State.LOADING
        
        BreedsRepository().get { breeds, error in
            DispatchQueue.main.async {
                if let breeds = breeds {
                    self.state = State.NORMAL
                    self.breeds = breeds
                } else {
                    self.state = State.ERROR
                }
            }
        }
        
        $breeds.combineLatest($shouldFilterFavourites, { breeds, shouldFilterFavourites -> [Breed] in
            var result: [Breed] = []
            if(shouldFilterFavourites){
                result.append(contentsOf: breeds.filter{ $0.isFavourite })
            } else {
                result.append(contentsOf: breeds)
            }
            if(result.isEmpty){
                self.state = State.EMPTY
            } else {
                self.state = State.NORMAL
            }
            return result
        }).assign(to: &$filteredBreeds)
    }
    
    func onFavouriteTapped(breed: Breed){
        onToggleFavouriteState.invoke(breed: breed) { error in }
        fetchData()
    }
    
    enum State {
        case NORMAL
        case LOADING
        case ERROR
        case EMPTY
    }
    
}
