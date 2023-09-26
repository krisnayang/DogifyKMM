package di

import com.example.dogify.repository.BreedsRepository
import com.example.dogify.usecase.FetchBreedsUseCase
import com.example.dogify.usecase.GetBreedsUseCase
import com.example.dogify.usecase.ToggleFavouriteStateUseCase
import com.example.dogify.viewmodel.MainViewModel
import org.kodein.di.DI
import org.kodein.di.bindProvider

val viewModelModule = DI {
  val repository = BreedsRepository()
  val getBreedsUseCase = GetBreedsUseCase()
  val fetchBreedsUseCase = FetchBreedsUseCase()
  val toggleFavouriteStateUseCase = ToggleFavouriteStateUseCase()

  bindProvider { MainViewModel(repository, getBreedsUseCase, fetchBreedsUseCase, toggleFavouriteStateUseCase) }
}