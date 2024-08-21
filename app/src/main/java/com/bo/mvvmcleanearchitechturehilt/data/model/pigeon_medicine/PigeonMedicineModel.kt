package com.bo.mvvmcleanearchitechturehilt.data.model.pigeon_medicine

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class PigeonMedicineModel(
    @SerializedName("isShow")
    @Expose
    val isShow: String = "",

    @SerializedName("pm_id")
    @Expose
    val pmId: String = "",

    @SerializedName("groupName")
    @Expose
    val groupName: String = "",

    @SerializedName("titleName")
    @Expose
    val titleName: String = "",

    @SerializedName("medicineType")
    @Expose
    val medicineType: String = "",

    @SerializedName("description")
    @Expose
    val description: String = "",

    @SerializedName("alterMedicineId")
    @Expose
    val alterMedicineId: String = "",

    @SerializedName("amountPerLiter")
    @Expose
    val amountPerLiter: String = "",

    @SerializedName("unit")
    @Expose
    val unit: String = "",

    @SerializedName("imgUrl")
    @Expose
    val imgUrl: String = "",

    @SerializedName("webLink")
    @Expose
    val webLink: String = "",

    @SerializedName("youtubeLink")
    @Expose
    val youtubeLink: String = ""

) : Parcelable {
    override fun toString(): String {
        return "PigeonMedicineModel(isShow='$isShow', pmId='$pmId', groupName='$groupName', titleName='$titleName', medicineType='$medicineType', description='$description', alterMedicineId='$alterMedicineId', amountPerLiter='$amountPerLiter', unit='$unit', imgUrl='$imgUrl', webLink='$webLink', youtubeLink='$youtubeLink')"
    }
}