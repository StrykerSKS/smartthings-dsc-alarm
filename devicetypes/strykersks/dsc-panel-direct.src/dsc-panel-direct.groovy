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
  
  main(["statusButton","nightButton"])
  details(["statusButton","nightButton"])
 }
}
