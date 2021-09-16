package com.mhp.coding.challenges.mapping.mappers

import com.mhp.coding.challenges.mapping.models.db.Article
import com.mhp.coding.challenges.mapping.models.db.Image
import com.mhp.coding.challenges.mapping.models.db.ImageSize
import com.mhp.coding.challenges.mapping.models.db.blocks.GalleryBlock
import com.mhp.coding.challenges.mapping.models.db.blocks.ImageBlock
import com.mhp.coding.challenges.mapping.models.db.blocks.TextBlock
import com.mhp.coding.challenges.mapping.models.db.blocks.VideoBlock
import com.mhp.coding.challenges.mapping.models.dto.ArticleDto
import com.mhp.coding.challenges.mapping.models.dto.ImageDto
import com.mhp.coding.challenges.mapping.models.dto.blocks.GalleryBlockDto
import com.mhp.coding.challenges.mapping.models.dto.blocks.ImageBlockDto
import com.mhp.coding.challenges.mapping.models.dto.blocks.TextBlockDto
import com.mhp.coding.challenges.mapping.models.dto.blocks.VideoBlockDto
import org.springframework.stereotype.Component
import java.util.*

@Component
class ArticleMapper {
    fun map(article: Article?): ArticleDto {
        return if (article != null) {
            val articleBlockDtoList = article.blocks.map { block ->
                when (block) {
                    is GalleryBlock -> GalleryBlockDto(block.images.map { mapImage(it) }, block.sortIndex)
                    is ImageBlock -> ImageBlockDto(mapImage(block.image), block.sortIndex)
                    is TextBlock -> TextBlockDto(block.text, block.sortIndex)
                    is VideoBlock -> VideoBlockDto(block.url, block.type, block.sortIndex)
                    else -> throw IllegalStateException("ArticleBlock Type unknown.")
                }
            }.sortedBy { it.sortIndex }
            article.let { ArticleDto(it.id, it.title, it.description ?: "", it.author ?: "", articleBlockDtoList) }
        } else {
            ArticleDto(0, "", "", "", emptyList())
        }
    }

    fun mapImage(image: Image?): ImageDto {
        return if (image != null) {
            ImageDto(image.id, image.url, image.imageSize)
        } else {
            // Since the ArticleRepository.kt always returns images as null (cf. line 74), unfortunately, this is obligatory.
            ImageDto(0, "", ImageSize.SMALL)
        }
    }

    // Not part of the challenge / Nicht Teil dieser Challenge.
    fun map(articleDto: ArticleDto?): Article = Article(
        title = "An Article",
        blocks = emptySet(),
        id = 1,
        lastModified = Date()
    )
}
