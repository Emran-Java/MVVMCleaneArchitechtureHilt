package com.bo.networkoperationdemo.data.model.pigeon_medicine

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
open class PigeonMonthlyMedicineCourseApiModel(
    @SerializedName("records")
    @Expose
    val pgnMnthMedicineList: List<PigeonMedicineModel> = emptyList()

) : Parcelable {
}