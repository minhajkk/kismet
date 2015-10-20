package org.kismet

class User {

    Integer id
    String name
    String geoLocation
    String prefrences

    static hasMany = [kroins: Kroin]

    static constraints = {
    }

    static mapping = {
        autoTimestamp true
    }
}
