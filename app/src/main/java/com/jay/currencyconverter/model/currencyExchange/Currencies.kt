package com.jay.currencyconverter.model.currencyExchange

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.jay.currencyconverter.model.currencyExchange.currency.*
import com.jay.currencyconverter.model.currencyExchange.currency.Currency
import java.util.*

class Currencies() : Parcelable {

    @SerializedName("EUR")
    @Expose
    var eUR: EUR? = null

    @SerializedName("RUB")
    @Expose
    var rUB: RUB? = null

    @SerializedName("USD")
    @Expose
    var uSD: USD? = null

    @SerializedName("AED")
    @Expose
    var aED: AED? = null

    @SerializedName("AMD")
    @Expose
    var aMD: AMD? = null

    @SerializedName("AUD")
    @Expose
    var aUD: AUD? = null

    @SerializedName("AZN")
    @Expose
    var aZN: AZN? = null

    @SerializedName("BGN")
    @Expose
    var bGN: BGN? = null

    @SerializedName("BRL")
    @Expose
    var bRL: BRL? = null

    @SerializedName("BYN")
    @Expose
    var bYN: BYN? = null

    @SerializedName("CAD")
    @Expose
    var cAD: CAD? = null

    @SerializedName("CHF")
    @Expose
    var cHF: CHF? = null

    @SerializedName("CLP")
    @Expose
    var cLP: CLP? = null

    @SerializedName("CNY")
    @Expose
    var cNY: CNY? = null

    @SerializedName("CZK")
    @Expose
    var cZK: CZK? = null

    @SerializedName("DKK")
    @Expose
    var dKK: DKK? = null

    @SerializedName("EGP")
    @Expose
    var eGP: EGP? = null

    @SerializedName("GBP")
    @Expose
    var gBP: GBP? = null

    @SerializedName("GEL")
    @Expose
    var gEL: GEL? = null

    @SerializedName("HKD")
    @Expose
    var hKD: HKD? = null

    @SerializedName("HRK")
    @Expose
    var hRK: HRK? = null

    @SerializedName("HUF")
    @Expose
    var hUF: HUF? = null

    @SerializedName("ILS")
    @Expose
    var iLS: ILS? = null

    @SerializedName("INR")
    @Expose
    var iNR: INR? = null

    @SerializedName("JPY")
    @Expose
    var jPY: JPY? = null

    @SerializedName("KRW")
    @Expose
    var kRW: KRW? = null

    @SerializedName("KWD")
    @Expose
    var kWD: KWD? = null

    @SerializedName("KZT")
    @Expose
    var kZT: KZT? = null

    @SerializedName("LBP")
    @Expose
    var lBP: LBP? = null

    @SerializedName("MDL")
    @Expose
    var mDL: MDL? = null

    @SerializedName("MXN")
    @Expose
    var mXN: MXN? = null

    @SerializedName("NOK")
    @Expose
    var nOK: NOK? = null

    @SerializedName("NZD")
    @Expose
    var nZD: NZD? = null

    @SerializedName("PLN")
    @Expose
    var pLN: PLN? = null

    @SerializedName("RON")
    @Expose
    var rON: RON? = null

    @SerializedName("SAR")
    @Expose
    var sAR: SAR? = null

    @SerializedName("SEK")
    @Expose
    var sEK: SEK? = null

    @SerializedName("SGD")
    @Expose
    var sGD: SGD? = null

    @SerializedName("THB")
    @Expose
    var tHB: THB? = null

    @SerializedName("TRY")
    @Expose
    var tRY: TRY? = null

    @SerializedName("TWD")
    @Expose
    var tWD: TWD? = null

    @SerializedName("VND")
    @Expose
    var vND: VND? = null

    @SerializedName("DZD")
    @Expose
    var dZD: DZD? = null

    @SerializedName("IQD")
    @Expose
    var iQD: IQD? = null

    @SerializedName("KGS")
    @Expose
    var kGS: KGS? = null

    @SerializedName("PKR")
    @Expose
    var pKR: PKR? = null

    @SerializedName("TJS")
    @Expose
    var tJS: TJS? = null

    val allAvailableCurrencies: List<Currency?>
        get() {
            val currenciesList: MutableList<Currency?> = ArrayList()
            currenciesList.add(uSD)
            currenciesList.add(eUR)
            currenciesList.add(rUB)
            currenciesList.add(aED)
            currenciesList.add(aMD)
            currenciesList.add(aUD)
            currenciesList.add(aZN)
            currenciesList.add(bGN)
            currenciesList.add(bRL)
            currenciesList.add(bYN)
            currenciesList.add(cAD)
            currenciesList.add(cHF)
            currenciesList.add(cLP)
            currenciesList.add(cNY)
            currenciesList.add(cZK)
            currenciesList.add(dKK)
            currenciesList.add(eGP)
            currenciesList.add(gBP)
            currenciesList.add(gEL)
            currenciesList.add(hKD)
            currenciesList.add(hRK)
            currenciesList.add(hUF)
            currenciesList.add(iLS)
            currenciesList.add(iNR)
            currenciesList.add(jPY)
            currenciesList.add(kRW)
            currenciesList.add(kWD)
            currenciesList.add(kZT)
            currenciesList.add(lBP)
            currenciesList.add(mDL)
            currenciesList.add(mXN)
            currenciesList.add(nOK)
            currenciesList.add(nZD)
            currenciesList.add(pLN)
            currenciesList.add(rON)
            currenciesList.add(sAR)
            currenciesList.add(sEK)
            currenciesList.add(sGD)
            currenciesList.add(tHB)
            currenciesList.add(tRY)
            currenciesList.add(tWD)
            currenciesList.add(vND)
            currenciesList.add(dZD)
            currenciesList.add(iQD)
            currenciesList.add(kGS)
            currenciesList.add(pKR)
            currenciesList.add(tJS)
            return currenciesList
        }

    constructor(parcel: Parcel) : this() {
        eUR = parcel.readParcelable(EUR::class.java.classLoader)
        aED = parcel.readParcelable(AED::class.java.classLoader)
        aMD = parcel.readParcelable(AMD::class.java.classLoader)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(eUR, flags)
        parcel.writeParcelable(aED, flags)
        parcel.writeParcelable(aMD, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Currencies> {
        override fun createFromParcel(parcel: Parcel): Currencies {
            return Currencies(parcel)
        }

        override fun newArray(size: Int): Array<Currencies?> {
            return arrayOfNulls(size)
        }
    }
}