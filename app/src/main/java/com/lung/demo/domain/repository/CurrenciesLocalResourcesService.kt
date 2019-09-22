package com.lung.demo.domain.repository

interface CurrenciesLocalResourcesService {

    fun getDescriptionAndImage(rate: String): Pair<String, String>

}