package org.kismet

class Community {

    Integer id
    String name
    String tier
    String location
    Boolean isVerifiedAccount

    static hasMany = [campaigns: Campaign]

    static constraints = {
    }
}
