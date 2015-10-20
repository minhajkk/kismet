package org.kismet

class Campaign {

    Integer id
    String title
    String decription
    Boolean isVoluteerRequired
    Boolean isTargetAchieving
    Boolean isSmartTagEnabled
    String organiser
    Double pointsReturnValue

    static belongsTo = [community: Community]

    static constraints = {
    }
}
