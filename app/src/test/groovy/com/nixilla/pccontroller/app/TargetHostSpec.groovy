package com.nixilla.pccontroller.app

import spock.lang.Specification

class TargetHostSpec extends Specification {

    void "it can be initialized"() {

        when:
        def subject = new TargetHost()

        then:
        subject instanceof TargetHost
    }

    void "it has IP address property"() {

        given:
        def subject = new TargetHost()
        Integer address = 1

        when:
        subject.ipAddress = address

        then:
        subject.ipAddress == address
    }

    void "it has some string properties"() {

        given:
        def subject = new TargetHost()
        String someString = "value"

        when:
        for(str in ["hostname", "token", "boottime", "status"]) {
            subject[str] = someString
        }


        then:
        for(str in ["hostname", "token", "boottime", "status"]) {
            subject[str] == someString
        }
    }

    void "it has a cookie property"() {

        given:
        def subject = new TargetHost()

        when:
        subject.cookieName = "PHPSSID"
        subject.cookieValue = "skjdfhskjdfhskjdf"

        then:
        subject.cookieName == "PHPSSID"
        subject.cookieValue == "skjdfhskjdfhskjdf"
    }
}
