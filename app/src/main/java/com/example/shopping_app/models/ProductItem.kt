package com.example.shopping_app.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.math.BigDecimal
import java.math.RoundingMode

@Entity(tableName = "cart_table")
data class ProductItem(

	@PrimaryKey()
	@ColumnInfo(name = "itemID")
	@field:SerializedName("itemID")
	val itemID: String,

	@ColumnInfo(name = "itemName")
	@field:SerializedName("itemName")
	val itemName: String? = null,

	@ColumnInfo(name = "sellingPrice")
	@field:SerializedName("sellingPrice")
	val sellingPrice: Double? = null,

	@ColumnInfo(name = "taxPercentage")
	@field:SerializedName("taxPercentage")
	val taxPercentage: Double? = null,

	@ColumnInfo(name = "itemQty")
	var itemQty: Int = 1
)
{

	// calculate sub total
	fun getSubTotal() : BigDecimal{
		return (sellingPrice?.let {
			it * itemQty
		} ?: 0.0).toBigDecimal().setScale(2, RoundingMode.HALF_UP)
	}

	// calculate tax amount
	fun getTaxAmount() : BigDecimal{
		return (sellingPrice?.let {
			it * (taxPercentage ?: 0.0 ) / 100
		} ?: 0.0).toBigDecimal().setScale(2,RoundingMode.HALF_UP)
	}

	// calculate total amount
	fun getTotalAmount() : BigDecimal{
		return getSubTotal() + getTaxAmount()
	}
}
