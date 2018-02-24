package com.thebrodyaga.vkurse.net

import com.thebrodyaga.vkurse.mvp.models.gson.OwnerInfo
import com.thebrodyaga.vkurse.mvp.models.gson.VkWall
import io.reactivex.Observable

/**
 * Created by Emelyanov.N4 on 21.02.2018.
 */
class VkService(private var vkurseApi: VkurseApi) {

    fun getFirstList(ownerInfoList: List<OwnerInfo>): Observable<VkWall> {
        return vkurseApi.getWall(ownerInfoList)
    }
}