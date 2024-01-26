package com.testcat.catcatcher

class ChangeList(val mainActivity: MainActivity) {
    fun changingList(int : Int){
        val appPrefs = AppPrefs(mainActivity)
        val list = appPrefs.getList("scoreKey").toMutableList()
        list[4]=list[3]
        list[3]=list[2]
        list[2]=list[1]
        list[1]=list[0]
        list[0] = int
        appPrefs.saveList("scoreKey",list)
    }
}