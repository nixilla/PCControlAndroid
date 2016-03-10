package com.nixilla.pccontroller.app

import spock.lang.Specification

class TargetHostSpec extends Specification {

    void "it can be initialized"() {

        when:
        def subject = new TargetHost()

        then:
        subject instanceof TargetHost
    }

    void "it has some string properties"() {

        given:
        def subject = new TargetHost()
        String someString = "value"

        when:
        for(str in ["hostname", "token", "boottime", "status", "ipAddress"]) {
            subject[str] = someString
        }


        then:
        for(str in ["hostname", "token", "boottime", "status", "ipAddress"]) {
            subject[str] == someString
        }
    }
}
