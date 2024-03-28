package com.samet.shoppapp.util

import com.samet.shoppapp.models.ProductItem
import com.samet.shoppapp.models.ProductItemFavourite

fun ProductItem.toProductItemFavourite(): ProductItemFavourite {
    return ProductItemFavourite(
        category = this.category,
        description = this.description,
        id = this.id,
        image = this.image,
        price = this.price,
        rating = this.rating,
        title = this.title
    )
}