package com.jay.currencyconverter.model.exchangeRate

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.jay.currencyconverter.R
import com.jay.currencyconverter.model.exchangeRate.currency.*
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

    fun getAllNotNullCurrencies(): List<OrganizationCurrency> {
        val currenciesList: MutableList<OrganizationCurrency?> = ArrayList()
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
        return currenciesList.filterNotNull()
    }

    fun getAllCurrencies(): List<OrganizationCurrency> {
        val list: MutableList<OrganizationCurrency> = mutableListOf()
        list.add(USD())
        list.add(EUR())
        list.add(RUB())
        list.add(AED())
        list.add(AMD())
        list.add(AUD())
        list.add(AZN())
        list.add(BGN())
        list.add(BRL())
        list.add(BYN())
        list.add(CAD())
        list.add(CHF())
        list.add(CLP())
        list.add(CNY())
        list.add(CZK())
        list.add(DKK())
        list.add(DZD())
        list.add(EGP())
        list.add(GBP())
        list.add(GEL())
        list.add(HKD())
        list.add(HRK())
        list.add(HUF())
        list.add(ILS())
        list.add(INR())
        list.add(IQD())
        list.add(JPY())
        list.add(KGS())
        list.add(KRW())
        list.add(KWD())
        list.add(KZT())
        list.add(LBP())
        list.add(MDL())
        list.add(MXN())
        list.add(NOK())
        list.add(NZD())
        list.add(PKR())
        list.add(PLN())
        list.add(RON())
        list.add(SAR())
        list.add(SEK())
        list.add(SGD())
        list.add(THB())
        list.add(TJS())
        list.add(TRY())
        list.add(TWD())
        list.add(UAH())
        list.add(VND())
        return list
    }

    fun checkCurrencyExistingByAbr(abr: String?, context: Context): Boolean {
        if (abr == null) return false
        getAllCurrencies().forEach { currency -> if (abr == currency.getAbr(context)) return true }
        return false
    }

    fun getCurrencyImageByAbr(abr: String, context: Context): Drawable? {
        getAllCurrencies().forEach { currency ->
            if (abr == currency.getAbr(context)) {
                return currency.getImage(context)
            }
        }
        return null
    }

    fun getCurrencyNameByAbr(abbr: String?, context: Context): String? {
        return when (abbr) {
            context.resources.getString(R.string.AED) -> context.resources.getString(R.string.uae_dirhams)
            context.resources.getString(R.string.AMD) -> context.resources.getString(R.string.armenian_dramas)
            context.resources.getString(R.string.AUD) -> context.resources.getString(R.string.australian_dollars)
            context.resources.getString(R.string.AZN) -> context.resources.getString(R.string.azerbaijani_manats)
            context.resources.getString(R.string.BGN) -> context.resources.getString(R.string.bulgarian_lev)
            context.resources.getString(R.string.BRL) -> context.resources.getString(R.string.brazilian_reals)
            context.resources.getString(R.string.BYN) -> context.resources.getString(R.string.belorussian_rubles)
            context.resources.getString(R.string.CAD) -> context.resources.getString(R.string.canadian_dollars)
            context.resources.getString(R.string.CHF) -> context.resources.getString(R.string.swiss_francs)
            context.resources.getString(R.string.CLP) -> context.resources.getString(R.string.chilean_pesos)
            context.resources.getString(R.string.CNY) -> context.resources.getString(R.string.yuan)
            context.resources.getString(R.string.CZK) -> context.resources.getString(R.string.czech_crowns)
            context.resources.getString(R.string.DKK) -> context.resources.getString(R.string.danish_kroner)
            context.resources.getString(R.string.DZD) -> context.resources.getString(R.string.algerian_dinars)
            context.resources.getString(R.string.EGP) -> context.resources.getString(R.string.egyptian_pounds)
            context.resources.getString(R.string.EUR) -> context.resources.getString(R.string.euro)
            context.resources.getString(R.string.GBP) -> context.resources.getString(R.string.pounds)
            context.resources.getString(R.string.GEL) -> context.resources.getString(R.string.georgian_lari)
            context.resources.getString(R.string.HKD) -> context.resources.getString(R.string.hong_kong_dollars)
            context.resources.getString(R.string.HRK) -> context.resources.getString(R.string.croatian_coons)
            context.resources.getString(R.string.HUF) -> context.resources.getString(R.string.hungarian_forints)
            context.resources.getString(R.string.ILS) -> context.resources.getString(R.string.israeli_shekels)
            context.resources.getString(R.string.INR) -> context.resources.getString(R.string.indian_rupees)
            context.resources.getString(R.string.IQD) -> context.resources.getString(R.string.iraqi_dinars)
            context.resources.getString(R.string.JPY) -> context.resources.getString(R.string.japanese_yen)
            context.resources.getString(R.string.KGS) -> context.resources.getString(R.string.kgs_of_kyrgyzstan)
            context.resources.getString(R.string.KRW) -> context.resources.getString(R.string.south_korean_won)
            context.resources.getString(R.string.KWD) -> context.resources.getString(R.string.kuwaiti_dinars)
            context.resources.getString(R.string.KZT) -> context.resources.getString(R.string.kazakh_tenge)
            context.resources.getString(R.string.LBP) -> context.resources.getString(R.string.lebanese_pounds)
            context.resources.getString(R.string.MDL) -> context.resources.getString(R.string.moldovan_lei)
            context.resources.getString(R.string.MXN) -> context.resources.getString(R.string.mexican_new_pesos)
            context.resources.getString(R.string.NOK) -> context.resources.getString(R.string.norwegian_kroner)
            context.resources.getString(R.string.NZD) -> context.resources.getString(R.string.new_zealand_dollars)
            context.resources.getString(R.string.PKR) -> context.resources.getString(R.string.pakistani_rupees)
            context.resources.getString(R.string.PLN) -> context.resources.getString(R.string.polish_zloty)
            context.resources.getString(R.string.RON) -> context.resources.getString(R.string.new_romanian_leu)
            context.resources.getString(R.string.RUB) -> context.resources.getString(R.string.rub)
            context.resources.getString(R.string.SAR) -> context.resources.getString(R.string.rials_of_saudi_arabia)
            context.resources.getString(R.string.SEK) -> context.resources.getString(R.string.swedish_kroner)
            context.resources.getString(R.string.SGD) -> context.resources.getString(R.string.singapore_dollars)
            context.resources.getString(R.string.THB) -> context.resources.getString(R.string.thai_baht)
            context.resources.getString(R.string.TJS) -> context.resources.getString(R.string.tajik_somons)
            context.resources.getString(R.string.TRY) -> context.resources.getString(R.string.turkish_lira)
            context.resources.getString(R.string.TWD) -> context.resources.getString(R.string.taiwan_dollars)
            context.resources.getString(R.string.UAH) -> context.resources.getString(R.string.uah)
            context.resources.getString(R.string.USD) -> context.resources.getString(R.string.usd)
            context.resources.getString(R.string.VND) -> context.resources.getString(R.string.vietnamese_dongs)
            else -> null
        }
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

    override fun describeContents(): Int = 0


    companion object CREATOR : Parcelable.Creator<Currencies> {

        override fun createFromParcel(parcel: Parcel): Currencies = Currencies(parcel)

        override fun newArray(size: Int): Array<Currencies?> = arrayOfNulls(size)
    }


}