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
        rUB = parcel.readParcelable(RUB::class.java.classLoader)
        uSD = parcel.readParcelable(USD::class.java.classLoader)
        aED = parcel.readParcelable(AED::class.java.classLoader)
        aMD = parcel.readParcelable(AMD::class.java.classLoader)
        aUD = parcel.readParcelable(AUD::class.java.classLoader)
        aZN = parcel.readParcelable(AZN::class.java.classLoader)
        bGN = parcel.readParcelable(BGN::class.java.classLoader)
        bRL = parcel.readParcelable(BRL::class.java.classLoader)
        bYN = parcel.readParcelable(BYN::class.java.classLoader)
        cAD = parcel.readParcelable(CAD::class.java.classLoader)
        cHF = parcel.readParcelable(CHF::class.java.classLoader)
        cLP = parcel.readParcelable(CLP::class.java.classLoader)
        cNY = parcel.readParcelable(CNY::class.java.classLoader)
        cZK = parcel.readParcelable(CZK::class.java.classLoader)
        dKK = parcel.readParcelable(DKK::class.java.classLoader)
        eGP = parcel.readParcelable(EGP::class.java.classLoader)
        gBP = parcel.readParcelable(GBP::class.java.classLoader)
        gEL = parcel.readParcelable(GEL::class.java.classLoader)
        hKD = parcel.readParcelable(HKD::class.java.classLoader)
        hRK = parcel.readParcelable(HRK::class.java.classLoader)
        hUF = parcel.readParcelable(HUF::class.java.classLoader)
        iLS = parcel.readParcelable(ILS::class.java.classLoader)
        iNR = parcel.readParcelable(INR::class.java.classLoader)
        jPY = parcel.readParcelable(JPY::class.java.classLoader)
        kRW = parcel.readParcelable(KRW::class.java.classLoader)
        kWD = parcel.readParcelable(KWD::class.java.classLoader)
        kZT = parcel.readParcelable(KZT::class.java.classLoader)
        lBP = parcel.readParcelable(LBP::class.java.classLoader)
        mDL = parcel.readParcelable(MDL::class.java.classLoader)
        mXN = parcel.readParcelable(MXN::class.java.classLoader)
        nOK = parcel.readParcelable(NOK::class.java.classLoader)
        nZD = parcel.readParcelable(NZD::class.java.classLoader)
        pLN = parcel.readParcelable(PLN::class.java.classLoader)
        rON = parcel.readParcelable(RON::class.java.classLoader)
        sAR = parcel.readParcelable(SAR::class.java.classLoader)
        sEK = parcel.readParcelable(SEK::class.java.classLoader)
        sGD = parcel.readParcelable(SGD::class.java.classLoader)
        tHB = parcel.readParcelable(THB::class.java.classLoader)
        tRY = parcel.readParcelable(TRY::class.java.classLoader)
        tWD = parcel.readParcelable(TWD::class.java.classLoader)
        vND = parcel.readParcelable(VND::class.java.classLoader)
        dZD = parcel.readParcelable(DZD::class.java.classLoader)
        iQD = parcel.readParcelable(IQD::class.java.classLoader)
        kGS = parcel.readParcelable(KGS::class.java.classLoader)
        pKR = parcel.readParcelable(PKR::class.java.classLoader)
        tJS = parcel.readParcelable(TJS::class.java.classLoader)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(eUR, flags)
        parcel.writeParcelable(rUB, flags)
        parcel.writeParcelable(uSD, flags)
        parcel.writeParcelable(aED, flags)
        parcel.writeParcelable(aMD, flags)
        parcel.writeParcelable(aUD, flags)
        parcel.writeParcelable(aZN, flags)
        parcel.writeParcelable(bGN, flags)
        parcel.writeParcelable(bRL, flags)
        parcel.writeParcelable(bYN, flags)
        parcel.writeParcelable(cAD, flags)
        parcel.writeParcelable(cHF, flags)
        parcel.writeParcelable(cLP, flags)
        parcel.writeParcelable(cNY, flags)
        parcel.writeParcelable(cZK, flags)
        parcel.writeParcelable(dKK, flags)
        parcel.writeParcelable(eGP, flags)
        parcel.writeParcelable(gBP, flags)
        parcel.writeParcelable(gEL, flags)
        parcel.writeParcelable(hKD, flags)
        parcel.writeParcelable(hRK, flags)
        parcel.writeParcelable(hUF, flags)
        parcel.writeParcelable(iLS, flags)
        parcel.writeParcelable(iNR, flags)
        parcel.writeParcelable(jPY, flags)
        parcel.writeParcelable(kRW, flags)
        parcel.writeParcelable(kWD, flags)
        parcel.writeParcelable(kZT, flags)
        parcel.writeParcelable(lBP, flags)
        parcel.writeParcelable(mDL, flags)
        parcel.writeParcelable(mXN, flags)
        parcel.writeParcelable(nOK, flags)
        parcel.writeParcelable(nZD, flags)
        parcel.writeParcelable(pLN, flags)
        parcel.writeParcelable(rON, flags)
        parcel.writeParcelable(sAR, flags)
        parcel.writeParcelable(sEK, flags)
        parcel.writeParcelable(sGD, flags)
        parcel.writeParcelable(tHB, flags)
        parcel.writeParcelable(tRY, flags)
        parcel.writeParcelable(tWD, flags)
        parcel.writeParcelable(vND, flags)
        parcel.writeParcelable(dZD, flags)
        parcel.writeParcelable(iQD, flags)
        parcel.writeParcelable(kGS, flags)
        parcel.writeParcelable(pKR, flags)
        parcel.writeParcelable(tJS, flags)
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