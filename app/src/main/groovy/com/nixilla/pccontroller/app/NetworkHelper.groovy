package com.nixilla.pccontroller.app

import groovy.transform.CompileStatic

@CompileStatic
class NetworkHelper {

    List<String> getClassC(String ipAddress) {

        List<String> result = []

        InetAddress subject = InetAddress.getByName(ipAddress)

        byte[] bytes = subject.address

        (1..<255).each {

            bytes[3] = it

            result.add(InetAddress.getByAddress(bytes).getHostAddress())
        }

        result
    }
}
