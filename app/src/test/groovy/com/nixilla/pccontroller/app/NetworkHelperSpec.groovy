package com.nixilla.pccontroller.app

import spock.lang.Specification

class NetworkHelperSpec extends Specification {

    void "it can be initialized"() {

        when:
        def subject = new NetworkHelper()

        then:
        subject instanceof NetworkHelper
    }

    void "it can workout class c from given ip address"() {

        given:
        def subject = new NetworkHelper()

        when:
        List result = subject.getClassC("10.0.1.9")

        then:
        result.size() == 254
        result.get(0) == "10.0.1.1"
    }
}
