package com.mhp.coding.challenges.mapping.services

import com.mhp.coding.challenges.mapping.repositories.ArticleRepository
import com.mhp.coding.challenges.mapping.mappers.ArticleMapper
import com.mhp.coding.challenges.mapping.models.dto.ArticleDto
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class ArticleService(
    private val mapper: ArticleMapper,
) {
    fun list(): List<ArticleDto> {
        val articles = ArticleRepository.all()
        return articles.map { mapper.map(it) }
    }

    fun articleForId(id: Long): ArticleDto {
        val article = ArticleRepository.findBy(id)

        // Since the ArticleRepository.kt always returns valid articles, I am not sure, how an unknown article would be
        // represented. Assuming null here, however I am aware that it can never be null with the current
        // implementation of ArticleRepository.kt.
        // I'd much rather like to return 404 in the ArticleController.kt. However, I guess that I am not supposed to alter it.
        if (article == null) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find article")
        } else {
            return article.let { mapper.map(it) }
        }
    }

    fun create(articleDto: ArticleDto): ArticleDto {
        val article = mapper.map(articleDto)
        ArticleRepository.create(article)
        return mapper.map(article)
    }
}
