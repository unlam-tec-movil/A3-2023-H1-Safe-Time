package ar.edu.unlam.mobile2.kitties.domain

import ar.edu.unlam.mobile2.kitties.data.KittiesRepo
import javax.inject.Inject

class GetNewKitty {

    var repo: KittiesRepo

    @Inject
    constructor(repo: KittiesRepo) {
        this.repo = repo
    }

    suspend fun getKitty(): String {
        return repo.getNewKitty()
    }
}
