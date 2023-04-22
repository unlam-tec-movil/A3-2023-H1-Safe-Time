package ar.edu.unlam.mobile2.kitties.data

interface KittiesRepo {

    suspend fun getNewKitty(): String
}
