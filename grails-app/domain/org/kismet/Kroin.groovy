package org.kismet

class Kroin {
    Integer id
    Double total
    Double worth
    Boolean isRedeemable

    static belongsTo = [user: User]

    static constraints = {
    }
}
