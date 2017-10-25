package services.hotels

import model.Hotel

trait HotelsService {
  def search(destination: String, radius: Double): Seq[Hotel]
}
