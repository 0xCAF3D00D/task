package org.example.task.service.provider.upgate.mapper

import org.example.task.client.dto.UpGatePostbackTransactionStatus
import org.example.task.client.dto.UpGateProductSale
import org.example.task.dao.entity.Product
import org.example.task.event.ExternalTransactionStatus

object UpGateMapper {

    fun Product.toProductSale(): UpGateProductSale {
        return UpGateProductSale(
            productPrice = this.price,
            productName = this.name,
            productDescription = this.description,
        )
    }

    fun UpGatePostbackTransactionStatus.toExternalTransactionStatus(): ExternalTransactionStatus {
        return when (this) {
            UpGatePostbackTransactionStatus.SUCCESS -> ExternalTransactionStatus.SUCCESS
            UpGatePostbackTransactionStatus.DECLINE -> ExternalTransactionStatus.DECLINE
            UpGatePostbackTransactionStatus.ERROR -> ExternalTransactionStatus.ERROR
        }
    }

}