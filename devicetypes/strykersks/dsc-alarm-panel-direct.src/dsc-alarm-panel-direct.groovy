/*
 *  DSC Alarm Panel Direct
 *
 *  Author: Sean Kendall Schneyer <sean@linuxbox.org>
 *  Date: 2015-11-14
 *  
 *  Inspired by and combines ideas from obycode, Kent Holloway <drizit@gmail.com> and oehokie
 *  Also inspired by Radio Thermostat for direct communication examples:
 *    https://github.com/statusbits/smartthings/blob/master/devicetypes/statusbits/radio-thermostat.src/radio-thermostat.groovy
 *
 */
 
 preferences {
  input("confIpAddr", "string", title:"Envisalink (Proxy) IP Addr",
   required:true, displayDuringSetup: true)
  input("confTcpPort", "number", title:"Envisalink Port",
   required:true, displayDuringSetup:true)
}

metadata {
 definition (name:"DSC Alarm Panel Direct", namespace:"StrykerSKS", author:"Sean Kendall Schneyer <sean@linuxbox.org>") {
  // Define Capabilities Here
  capability "Alarm"
  capability "Refresh"
  capability "Polling"
  capability "Switch"
 }

// Add commands here as needed

 simulator {
  // Place Simulator data here
 }
 
 // UI Tile Definitions
 tiles {
  standardTile("statusButton", "device.mainState", width: 2, height: 2, canChangeIcon: true) {
   state "default", label: 'Ready', action: "arm", icon: "st.Home.home2", backgroundColor: "#79b821", nextState: "arming"
   state "disarmed", label: 'Disarmed', action: "arm", icon: "st.Home.home2", backgroundColor: "#79b821", nextState: "arming"
   state "armed", label: 'Armed', action: "disarm", icon: "st.Home.home3", backgroundColor: "#b82078", nextState: "disarming"
   state "alarming", label: 'ALARMING', action: "disarm", icon: "st.Home.home3", backgroundColor: "#b82078", nextState: "disarming"
   state "disarming", label: 'Disarming', action: "arm", icon: "st.Home.home2", backgroundColor: "#b8ab20", nextState: "disarmed"
   state "arming", label: 'Arming', action: "disarm", icon: "st.Home.home3", backgroundColor: "#b8ab20", nextState: "armed"
   state "night", label: 'Armed Night', action: "disarm", icon: "st.Home.home3", backgroundColor: "#2078b8", nextState: "disarming"
  }
  
  standardTile("nightButton", "device.button", width: 1, height: 1, canChangeIcon: true) {
   state "default", label: 'Night Arm', action: "nightarm", icon: "st.Weather.weather4", backgroundColor: "#2078b8", nextState: "default"
  }
  
  standardTile("alarmStatus","device.alarmStatus", width: 1, height: 1, canChangeIcon: true) {
   state "ready", label: 'Ready', action: "", icon: "", backgroundColor: "#ffffff", nextState: ""
   state "readyf", label: 'Ready - F', action: "", icon: "", backgroundColor: "#ffffff", nextState: ""
   state "notready", label: 'Not Ready', action: "", icon: "", backgroundColor: "#ffffff", nextState: ""
   state "alarming", label: 'ALARMING', action: "", icon: "", backgroundColor: "#ffffff", nextState: ""
   state "entry", label: 'Entry Delay', action: "", icon: "", backgroundColor: "#ffffff", nextState: ""
   state "exit", label: 'Exit Delay', action: "", icon: "", backgroundColor: "#ffffff", nextState: ""
   state "lockout", label: 'Keypad Lockout', action: "f", icon: "", backgroundColor: "#ffffff", nextState: ""
   state "fail", label: 'Failed to Arm', action: "", icon: "", backgroundColor: "#ffffff", nextState: ""
   state "away", label: 'Armed - Away', action: "", icon: "", backgroundColor: "#ffffff", nextState: ""
   state "stay", label: 'Armed - Stay', action: "", icon: "", backgroundColor: "#ffffff", nextState: ""
   state "zeroaway", label: 'Zero Entry Away', action: "", icon: "", backgroundColor: "#ffffff", nextState: ""
   state "zerostay", label: 'Zero Entry Stay', action: "", icon: "", backgroundColor: "#ffffff", nextState: ""
   state "disarmed", label: 'Disarmed', action: "", icon: "", backgroundColor: "#ffffff", nextState: ""
  }
  
  standardTile("update", "default", inactiveLabel: false) {
   state "default", action:"updatestatus", icon:"st.secondary.refresh"
  }
  
  standardTile("disarmButton", "device.button", inactiveLabel:false) {
   state "default", label:"Disarm", icon:"st.Home.home2", action:"disarm"
  }
  
  standardTile("chimeToggle", "device.switch", canChangeIcon: true) {
   state "off", label: "Chime", icon:"st.Home.home2", action:"chime.on", backgroundColor: "#ffffff"
   state "on", label: "Chime", icon:"st.Home.home2", action: "chime.off", backgroundColor: "#E60000"
  }

  main(["statusButton","nightButton","alarmStatus","update","disarmButton","chimeToggle"])
  details(["statusButton","nightButton"])
 }
}

def parse(String description) {
 // Parse the messages from the panel here...
 LOG(description)
}

private String convertPortToHex(port) {
    String hexport = port.toString().format( '%04x', port.toInteger() )
    log.debug hexport
    return hexport
}

private Integer convertHexToInt(hex) {
    Integer.parseInt(hex,16)
}


private String convertHexToIP(hex) {
    log.debug("Convert hex to ip: $hex") 
    [convertHexToInt(hex[0..1]),convertHexToInt(hex[2..3]),convertHexToInt(hex[4..5]),convertHexToInt(hex[6..7])].join(".")
}

private getHostAddress() {
    def parts = device.deviceNetworkId.split(":")
    log.debug device.deviceNetworkId
    def ip = convertHexToIP(parts[0])
    def port = convertHexToInt(parts[1])
    return ip + ":" + port
}

private def LOG(message) {
    log.trace message
}

